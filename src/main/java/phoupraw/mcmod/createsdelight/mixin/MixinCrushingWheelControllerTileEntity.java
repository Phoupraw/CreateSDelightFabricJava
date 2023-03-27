package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.contraptions.components.crusher.CrushingWheelControllerTileEntity;
import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import phoupraw.mcmod.createsdelight.inject.InjectCrushingWheelControllerTileEntity;

import java.util.List;
import java.util.Optional;
@Mixin(CrushingWheelControllerTileEntity.class)
public abstract class MixinCrushingWheelControllerTileEntity extends SmartTileEntity implements InjectCrushingWheelControllerTileEntity, SidedStorageBlockEntity, IHaveGoggleInformation {
    private SmartFluidTankBehaviour tank;
    private double bottom;

    public MixinCrushingWheelControllerTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "addBehaviours", at = @At("RETURN"), remap = false)
    private void addBehaviours(List<TileEntityBehaviour> behaviours, CallbackInfo ci) {
        InjectCrushingWheelControllerTileEntity.addBehaviours(this, behaviours, ci);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"), cancellable = true)
    private void checkTankEmpty(CallbackInfo ci) {
        InjectCrushingWheelControllerTileEntity.checkTankEmpty(this, ci);
    }

    @Inject(method = "applyRecipe", slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getCount()I")), at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/contraptions/processing/ProcessingInventory;clear()V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void applyFluidResult(CallbackInfo ci, @SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<ProcessingRecipe<Inventory>> recipe) {
        InjectCrushingWheelControllerTileEntity.applyFluidResult(this, recipe.orElseThrow());
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/tileEntity/SmartTileEntity;tick()V"))
    private void setBottom(CallbackInfo ci) {
        InjectCrushingWheelControllerTileEntity.setBottom(this);
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        InjectCrushingWheelControllerTileEntity.lazyTick(this);
    }

    @Override
    public @Nullable Storage<FluidVariant> getFluidStorage(@Nullable Direction side) {
        return side == null ? getTank().getCapability() : null;
    }

    @Override
    public SmartFluidTankBehaviour getTank() {
        return tank;
    }

    @Override
    public void setTank(SmartFluidTankBehaviour tank) {
        this.tank = tank;
    }

    @Override
    public double getBottom() {
        return bottom;
    }

    @Override
    public void setBottom(double bottom) {
        this.bottom = bottom;
    }

    @Override
    public boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(tooltip, isPlayerSneaking, getTank().getCapability());
    }
}
