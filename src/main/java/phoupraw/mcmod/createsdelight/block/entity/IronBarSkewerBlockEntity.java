package phoupraw.mcmod.createsdelight.block.entity;

import com.nhoryzon.mc.farmersdelight.registry.ParticleTypesRegistry;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.foundation.utility.recipe.RecipeConditions;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleItemStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.RecipeType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.api.HeatSources;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;

import java.util.ArrayList;
import java.util.Arrays;
public class IronBarSkewerBlockEntity extends KineticTileEntity implements SidedStorageBlockEntity {
    public static final int SIZE = 2;

    public static @NotNull NbtList toNbt(double @NotNull [] array) {
        var list = new NbtList();
        for (double v : array) list.add(NbtDouble.of(v));
        return list;
    }

    public static double @NotNull [] write(double @Nullable [] target, NbtList source) {
        if (target == null) target = new double[source.size()];
        for (int i = 0; i < source.size(); i++) {
            target[i] = source.getDouble(i);
        }
        return target;
    }

    public final CombinedStorage<ItemVariant, Slot> storage = new CombinedStorage<>(new ArrayList<>(2));
    public final double[] countdowns = new double[SIZE];

    public IronBarSkewerBlockEntity(BlockPos pos, BlockState state) {
        this(MyBlockEntityTypes.IRON_BAR_SKEWER, pos, state);
    }

    public IronBarSkewerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        for (int i = 0; i < SIZE; i++) {
            storage.parts.add(new Slot(i));
        }
        Arrays.fill(countdowns, Double.NaN);
    }

    @Override
    public @Nullable Storage<ItemVariant> getItemStorage(@Nullable Direction side) {
        return side == null ? storage : null;
    }

    @Override
    public void destroy() {
        super.destroy();
        DefaultedList<ItemStack> of = DefaultedList.ofSize(2);
        for (Slot s : storage.parts) {
            of.add(s.getResource().toStack());
        }
        ItemScatterer.spawn(getWorld(), getPos(), of);
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public World getWorld() {
        return super.getWorld();
    }

    @Override
    protected void write(NbtCompound compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.put("countdowns", toNbt(countdowns));
        var list = new NbtList();
        for (int i = 0; i < SIZE; i++) {
            NbtCompound nbt = new NbtCompound();
            storage.parts.get(i).writeNbt(nbt);
            list.add(nbt);
        }
        compound.put("items", list);
    }

    @Override
    protected void read(NbtCompound compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        write(countdowns, compound.getList("countdowns", NbtElement.DOUBLE_TYPE));
        var list = compound.getList("items", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < SIZE; i++) {
            storage.parts.get(i).readNbt(list.getCompound(i));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (getSpeed() == 0) return;
        double heat = 0;
        for (Direction side : Direction.values()) {
            heat = Math.max(heat, HeatSources.find(getWorld(), getPos().offset(side), side.getOpposite()));
        }
        if (heat <= 1) return;
        for (int i = 0; i < SIZE; i++) {
            Slot slot = storage.parts.get(i);
            long amount = slot.getAmount();
            if (countdowns[i] > 0) {
                countdowns[i] -= (heat - 1) / Math.cbrt(amount);
                Random random = getWorld().getRandom();
                if (random.nextInt(20) == 0) {
                    var pos = getParticlePos(i);
                    getWorld().addParticle(ParticleTypesRegistry.STEAM.get(), pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
                }
            } else if (!Double.isNaN(countdowns[i])) {
                var recipe = getWorld().getRecipeManager().listAllOfType(RecipeType.CAMPFIRE_COOKING).stream().filter(RecipeConditions.firstIngredientMatches(slot.getResource().toStack())).findFirst().orElse(null);
                if (recipe != null) {
                    try (var transa = TransferUtil.getTransaction()) {
                        slot.extract(slot.getResource(), amount, transa);
                        slot.insert(ItemVariant.of(recipe.getOutput()), amount, transa);
                        transa.commit();
                    }
                    for (int j = 0; j < 10; j++) {
                        var pos = getParticlePos(i);
                        getWorld().addParticle(ParticleTypesRegistry.STEAM.get(), pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
                    }
                    for (int j = 0; j < 30; j++) {
                        var pos = getParticlePos(i);
                        getWorld().addParticle(ParticleTypes.SMOKE, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
                    }
                }
            }
        }
    }

    public Vec3d getParticlePos(int index) {
        Random random = getWorld().getRandom();
        var axis = getCachedState().get(Properties.AXIS);
        Vec3d pos = Vec3d.ofCenter(getPos()).add((random.nextDouble() - 0.5) / 2, (random.nextDouble() - 0.5) / 2 - 0.2, (random.nextDouble() - 0.5) / 2);
        double offset = 0.4 * (index - 0.5);
        if (axis == Direction.Axis.Y) {
            pos = pos.add(0, offset, 0);
        } else if (axis == Direction.Axis.X) {
            pos = pos.add(offset, 0, 0);
        } else {
            pos = pos.add(0, 0, offset);
        }
        return pos;
    }

    public class Slot extends SingleItemStorage {
        public final int index;

        public Slot(int index) {
            this.index = index;
        }


        @Override
        protected long getCapacity(ItemVariant variant) {
            return Math.min(variant.getItem().getMaxCount(), 4);
        }

        @Override
        protected void onFinalCommit() {
            super.onFinalCommit();
            double countdown = Double.NaN;
            if (!isResourceBlank()) {
                var recipe = getWorld().getRecipeManager().listAllOfType(RecipeType.CAMPFIRE_COOKING).stream().filter(RecipeConditions.firstIngredientMatches(getResource().toStack())).findFirst().orElse(null);
                if (recipe != null) {
                    countdown = recipe.getCookTime() / 4.0;
                }
            }
            countdowns[index] = countdown;
            notifyUpdate();
        }
    }
}
