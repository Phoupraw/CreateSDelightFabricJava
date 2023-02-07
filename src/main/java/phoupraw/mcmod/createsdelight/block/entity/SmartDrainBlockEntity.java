package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.DirectBeltInputBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.behaviour.BurnerBehaviour;
import phoupraw.mcmod.createsdelight.behaviour.DepotItemBehaviour;
import phoupraw.mcmod.createsdelight.behaviour.EmptyingBehaviour;
import phoupraw.mcmod.createsdelight.behaviour.RollingItemBehaviour;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.MyFluids;

import java.util.*;
public class SmartDrainBlockEntity extends SmartTileEntity implements SidedStorageBlockEntity, IHaveGoggleInformation {
    public SmartDrainBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.SMART_DRAIN, pos, state);}

    public SmartDrainBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
        var rb = new RollingItemBehaviour(this);
        behaviours.add(rb);
        behaviours.add(new DirectBeltInputBehaviour(this).setInsertionHandler(rb).allowingBeltFunnels());
        var tb = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.TYPE, this, 1, FluidConstants.BUCKET * 2, false);
        behaviours.add(tb);
        behaviours.add(new DepotItemBehaviour(this));
        var bb = new BurnerBehaviour(this) {
            @Override
            public void onIgnite() {
                super.onIgnite();
                getWorld().setBlockState(getPos(), getCachedState().with(Properties.LIT, true));
            }

            @Override
            public void onExtinguish() {
                super.onExtinguish();
                getWorld().setBlockState(getPos(), getCachedState().with(Properties.LIT, false));
            }
        };
        behaviours.add(bb);
        behaviours.add(new EmptyingBehaviour(this));
        tb.whenFluidUpdates(() -> {
            if (bb.getFuelTicks() > 0 && !tb.getPrimaryHandler().getResource().isOf(MyFluids.SUNFLOWER_OIL)) {
                bb.setFuelTicks(0);
                getWorld().playSound(null, getPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1, 1);
            }
        });
    }

    @NotNull
    @Override
    public World getWorld() {
        return Objects.requireNonNull(super.getWorld());
    }

    @Override
    public @Nullable Storage<ItemVariant> getItemStorage(@Nullable Direction side) {
        if (side == Direction.UP) return getBehaviour(RollingItemBehaviour.TYPE).get(side);
        if (side == null) side = Direction.DOWN;
        return getBehaviour(DepotItemBehaviour.TYPE).get(side);
    }

    @Override
    public @Nullable Storage<FluidVariant> getFluidStorage(@Nullable Direction side) {
        return side == Direction.UP ? null : getBehaviour(SmartFluidTankBehaviour.TYPE).getCapability();
    }

    @Override
    public boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        return IHaveGoggleInformation.super.containedFluidTooltip(tooltip, isPlayerSneaking, getBehaviour(SmartFluidTankBehaviour.TYPE).getCapability()) | getBehaviour(BurnerBehaviour.TYPE).addToGoggleTooltip(tooltip, isPlayerSneaking);
    }

}
