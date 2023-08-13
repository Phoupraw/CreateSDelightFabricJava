package phoupraw.mcmod.createsdelight.block.entity;

import com.nhoryzon.mc.farmersdelight.registry.SoundsRegistry;
import com.simibubi.create.content.kinetics.base.RotationIndicatorParticleData;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.recipe.RecipeConditions;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiCache;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import phoupraw.mcmod.createsdelight.api.HeatSources;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;
import phoupraw.mcmod.createsdelight.storage.BlockingTransportedStorage;

import java.util.*;
import java.util.stream.IntStream;
public class GrillBlockEntity extends SmartBlockEntity implements SidedStorageBlockEntity {
    public static final int SLOTS = 4;

    public static Vec3d getHorizontalOffset(int index, int size) {
        if (size == 1) return Vec3d.ZERO;
        double angle = (index + 0.5) * Math.PI * 2 / size;
        return new Vec3d(Math.cos(angle) / 3, 0, Math.sin(angle) / 3);
    }

    public final CombinedStorage<ItemVariant, BlockingTransportedStorage> storage;
    public final int[] processings = new int[SLOTS];
    public final int[] flippings = new int[SLOTS];
    private BlockApiCache<Double, Direction> heatCache;
    {
        var list = new ArrayList<BlockingTransportedStorage>(SLOTS);
        for (int i = 0; i < SLOTS; i++) {
            list.add(new BlockingTransportedStorage() {
                @Override
                protected void onFinalCommit() {
                    super.onFinalCommit();
                    sendData();
                }
            });
            resetTicks(i);
        }
        storage = new CombinedStorage<>(list);
    }
    public GrillBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.GRILL, pos, state);}

    public GrillBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(new DirectBeltInputBehaviour(this));
    }

    @Override
    public void tick() {
        super.tick();
        var nonEmpties = getNonEmpties();
        for (Pair<Integer, BlockingTransportedStorage> pair : nonEmpties) {
            int i = pair.getLeft();
            if (flippings[i] >= -10) {
                if (flippings[i] < 10) {
                    flippings[i]++;
                    if (flippings[i] < 0) {
                        Vec3d pos = Vec3d.ofCenter(getPos());
                        var particle = new RotationIndicatorParticleData(MapColor.TERRACOTTA_YELLOW.color, 24f, 0.3f, 0.3f, 20, 'Y');
                        getWorld().addParticle(particle, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
                    } else if (flippings[i] == 10) {
                        getWorld().playSound(null, getPos(), SoundsRegistry.BLOCK_SKILLET_ADD_FOOD.get(), SoundCategory.BLOCKS, 0.8F, 1.0F);
                    }
                } else {
                    getWorld().playSound(null, getPos(), SoundEvents.BLOCK_CAMPFIRE_CRACKLE, SoundCategory.BLOCKS, 1, 1);
                }
            }
        }
        if (getWorld().isClient()) return;
        if (getHeat() < 1) return;
        for (int j = 0; j < nonEmpties.size(); j++) {
            int i = nonEmpties.get(j).getLeft();
            BlockingTransportedStorage slot = nonEmpties.get(j).getRight();
            var recipe = getWorld().getRecipeManager().listAllOfType(MyRecipeTypes.GRILLING.getRecipeType()).parallelStream().filter(RecipeConditions.firstIngredientMatches(slot.getStack())).findFirst().orElse(null);
            if (recipe == null) {
                resetTicks(i);
                continue;
            }
            processings[i]++;
            if (processings[i] == 1) {
                sendData();
            }
            if (processings[i] >= recipe.getProcessingDuration() / 2 - 15 && flippings[i] == -11) {
                flippings[i] = -10;
                sendData();
            }
            if (processings[i] >= recipe.getProcessingDuration()) {
                Vec3d pos = Vec3d.of(getPos()).add(0.5, 4 / 16.0, 0.5).add(getHorizontalOffset(j, nonEmpties.size()));
                var itemEntity = new ItemEntity(getWorld(), pos.getX(), pos.getY(), pos.getZ(), recipe.getOutput().copy(), 0, 0.3, 0);
                getWorld().spawnEntity(itemEntity);
                slot.setStack(ItemStack.EMPTY);
                resetTicks(i);
                sendData();
            }
        }
    }

    @NotNull
    @Override
    public World getWorld() {
        return Objects.requireNonNull(super.getWorld());
    }

    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        var slots = new NbtList();
        for (int i = 0; i < SLOTS; i++) {
            var transported = storage.parts.get(i).getTransported();
            if (transported.stack.isEmpty()) continue;
            var compound = new NbtCompound();
            compound.put("item", transported.serializeNBT());
            compound.putInt("processing", processings[i]);
            compound.putInt("flipping", flippings[i]);
            compound.putInt("slot", i);
            slots.add(compound);
        }
        tag.put("slots", slots);
    }

    @Override
    protected void read(NbtCompound tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        Set<Integer> indexes = new HashSet<>(IntStream.range(0, SLOTS).boxed().toList());
        var list = tag.getList("slots", NbtElement.COMPOUND_TYPE);
        for (int j = 0; j < list.size(); j++) {
            var compound = list.getCompound(j);
            int i = compound.getInt("slot");
            storage.parts.get(i).setTransported(TransportedItemStack.read(compound.getCompound("item")));
            processings[i] = compound.getInt("processing");
            flippings[i] = compound.getInt("flipping");
            indexes.remove(i);
        }
        for (int i : indexes) {
            storage.parts.get(i).setStack(ItemStack.EMPTY);
        }
    }

    @Override
    public @NotNull Storage<ItemVariant> getItemStorage(@Nullable Direction side) {
        return storage;
    }

    public @NotNull BlockApiCache<Double, Direction> getHeatCache() {
        if (heatCache == null) {
            if (getWorld() instanceof ServerWorld serverWorld) {
                heatCache = BlockApiCache.create(HeatSources.SIDED, serverWorld, pos.down());
            } else {
                throw new UnsupportedOperationException("cannot invoke this at client");
            }
        }
        return heatCache;
    }

    public double getHeat() {
        var heat = getHeatCache().find(Direction.UP);
        return heat == null ? 0 : heat;
    }

    public @UnmodifiableView List<Pair<Integer, BlockingTransportedStorage>> getNonEmpties() {
        List<Pair<Integer, BlockingTransportedStorage>> list = new ArrayList<>(SLOTS);
        List<BlockingTransportedStorage> parts = storage.parts;
        for (int i = 0; i < SLOTS; i++) {
            BlockingTransportedStorage s = parts.get(i);
            if (!s.isResourceBlank()) {
                list.add(Pair.of(i, s));
            }
        }
        return list;
    }

    public void resetTicks(int index) {
        processings[index] = 0;
        flippings[index] = -11;
    }
}
