package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;

import java.util.List;
public class OvenBlockEntity extends SmartTileEntity implements SidedStorageBlockEntity {
    private final SmartInventory inventory = new SmartInventory(9, this, 64, false);
    private SmartFluidTankBehaviour tank;

    public OvenBlockEntity(BlockPos pos, BlockState state) {
        this(MyBlockEntityTypes.OVEN, pos, state);
    }

    public OvenBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
        tank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.TYPE, this, 4, FluidConstants.BUCKET * 2, true);
        behaviours.add(tank);
    }

    @Override
    public @NotNull Storage<ItemVariant> getItemStorage(@Nullable Direction side) {
        return getInventory();
    }

    @Override
    public @NotNull Storage<FluidVariant> getFluidStorage(@Nullable Direction side) {
        return getTank().getCapability();
    }

    public SmartFluidTankBehaviour getTank() {
        return tank;
    }

    public SmartInventory getInventory() {
        return inventory;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public World getWorld() {
        return super.getWorld();
    }
}
