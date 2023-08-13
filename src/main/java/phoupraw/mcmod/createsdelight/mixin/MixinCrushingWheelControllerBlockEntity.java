package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
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
import phoupraw.mcmod.createsdelight.inject.InjectCrushingWheelControllerBlockEntity;

import java.util.List;
import java.util.Optional;
@Mixin(CrushingWheelControllerBlockEntity.class)
public abstract class MixinCrushingWheelControllerBlockEntity extends SmartBlockEntity implements InjectCrushingWheelControllerBlockEntity, SidedStorageBlockEntity, IHaveGoggleInformation {
    private SmartFluidTankBehaviour tank;
    private double bottom;

    public MixinCrushingWheelControllerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "addBehaviours", at = @At("RETURN"), remap = false)
    private void addBehaviours(List<BlockEntityBehaviour> behaviours, CallbackInfo ci) {
        InjectCrushingWheelControllerBlockEntity.addBehaviours(this, behaviours, ci);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;clamp(FFF)F"), cancellable = true)
    private void checkTankEmpty(CallbackInfo ci) {
        InjectCrushingWheelControllerBlockEntity.checkTankEmpty(this, ci);
    }

    @Inject(method = "applyRecipe", slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getCount()I")), at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/processing/recipe/ProcessingInventory;clear()V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
    private void applyFluidResult(CallbackInfo ci, @SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<ProcessingRecipe<Inventory>> recipe/*, List<ItemStack> list, int rolls*/) {
        InjectCrushingWheelControllerBlockEntity.applyFluidResult(this, recipe.orElseThrow());
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/blockEntity/SmartBlockEntity;tick()V"), remap = false)
    private void setBottom(CallbackInfo ci) {
        InjectCrushingWheelControllerBlockEntity.setBottom(this);
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        InjectCrushingWheelControllerBlockEntity.lazyTick(this);
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
