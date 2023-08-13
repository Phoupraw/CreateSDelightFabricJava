package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.recipe.RecipeConditions;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiCache;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.api.HeatSources;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
public class BambooSteamerBlockEntity extends SmartBlockEntity implements SidedStorageBlockEntity {
    public static Vec3d getOffset(int index) {
        double x = index % 2 == 0 ? 4 / 16.0 : 12 / 16.0;
        double z = index / 2 % 2 == 0 ? 4 / 16.0 : 12 / 16.0;
        double y = index / 4 % 2 == 0 ? 1 / 16.0 : 8 / 16.0;
        return new Vec3d(x, y, z);
    }

    public final SmartInventory inventory = new SmartInventory(8, this, 1, false);
    private BlockApiCache<Double, Direction> heatCache;
    private boolean workable;
    public final int[] elapsed = new int[8];

    public BambooSteamerBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.BAMBOO_STEAMER, pos, state);}

    public BambooSteamerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }

    @Override
    public @Nullable Storage<ItemVariant> getItemStorage(Direction side) {
        return inventory;
    }

    public boolean isWorkable() {
        if (getWorld().isClient()) return workable;
        var previous = workable;
        var be0 = getWorld().getBlockEntity(getPos().down());
        workable = be0 instanceof BasinBlockEntity basin && StorageUtil.findExtractableResource(basin.getFluidStorage(null), Predicate.isEqual(FluidVariant.of(Fluids.WATER)), null) != null && getHeat() >= 1;
        if (previous ^ workable) sendData();
        return workable;
    }

    @NotNull
    @Override
    public World getWorld() {
        return Objects.requireNonNull(super.getWorld());
    }

    public @NotNull BlockApiCache<Double, Direction> getHeatCache() {
        if (heatCache == null) {
            if (getWorld() instanceof ServerWorld serverWorld) {
                heatCache = BlockApiCache.create(HeatSources.SIDED, serverWorld, pos.down(2));
            } else {
                throw new UnsupportedOperationException("cannot invoke this at client");
            }
        }
        return heatCache;
    }

    public double getHeat() {
        Double heat = getHeatCache().find(Direction.UP);
        return heat == null ? 0 : heat;
    }

    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.put("inventory", inventory.serializeNBT());
        if (clientPacket) {
            tag.putBoolean("workable", workable);
        }
    }

    @Override
    protected void read(NbtCompound tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        inventory.deserializeNBT(tag.getCompound("inventory"));
        if (clientPacket) {
            workable = tag.getBoolean("workable");
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!isWorkable()) return;
        if (getWorld().isClient()) return;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack itemStack = inventory.getStack(i);
            if (itemStack.isEmpty()) {
                elapsed[i] = 0;
                continue;
            }
            var recipe = getWorld().getRecipeManager().listAllOfType(MyRecipeTypes.STEAMING.getRecipeType()).parallelStream().filter(RecipeConditions.firstIngredientMatches(itemStack)).findFirst().orElse(null);
            if (recipe == null) {
                elapsed[i] = 0;
                continue;
            }
            elapsed[i]++;
            if (elapsed[i] == recipe.getProcessingDuration()) {
                elapsed[i] = 0;
                inventory.setStack(i, recipe.getOutput().copy());
                sendData();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        ItemScatterer.spawn(getWorld(), getPos(), inventory);
    }
}
