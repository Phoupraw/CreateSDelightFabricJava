package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.components.millstone.MillingRecipe;
import com.simibubi.create.content.contraptions.components.millstone.MillstoneTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoupraw.mcmod.createsdelight.inject.InjectMillstoneTileEntity;

import java.util.List;
@Mixin(MillstoneTileEntity.class)
public abstract class MixinMillstoneTileEntity extends KineticTileEntity implements SidedStorageBlockEntity, InjectMillstoneTileEntity {
    @Shadow
    private MillingRecipe lastRecipe;
    private SmartFluidTankBehaviour tank;
    //    private final Collection<InjectMillstoneTileEntity.FluidStream> fluidStreams = new ArrayList<>(2);
//    private double outputStart=-1;
//    private double prevOutputStart=outputStart;
//    private double outputEnd = 0;
//    private double prevOutputEnd = 0;
    private Direction outputSide;

    public MixinMillstoneTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Inject(method = "addBehaviours", at = @At("RETURN"))
    private void addBehaviours(List<TileEntityBehaviour> behaviours, CallbackInfo ci) {
        InjectMillstoneTileEntity.addBehaviours((MillstoneTileEntity) (Object) this, behaviours, ci);
    }

    @Inject(method = "process", at = @At(value = "INVOKE", target = "Lio/github/fabricators_of_create/porting_lib/transfer/item/ItemStackHandlerContainer;getStackInSlot(I)Lnet/minecraft/item/ItemStack;"), cancellable = true)
    private void fluidResult(CallbackInfo ci) {
        InjectMillstoneTileEntity.fluidResult((MillstoneTileEntity) (Object) this, tank, lastRecipe, ci);
    }

    @Inject(method = "tick", at = @At(value = "FIELD", opcode = Opcodes.GETFIELD, target = "Lcom/simibubi/create/content/contraptions/components/millstone/MillstoneTileEntity;timer:I", shift = At.Shift.BEFORE, ordinal = 0), cancellable = true)
    private void checkTankFull(CallbackInfo ci) {
        InjectMillstoneTileEntity.checkTankFull((MillstoneTileEntity) (Object) this, ci);
    }

    @Override
    public void lazyTick() {
        super.lazyTick();
        InjectMillstoneTileEntity.lazyTick((MillstoneTileEntity) (Object) this);
    }

    @Inject(method = "tick", at = @At("HEAD"/*value = "INVOKE",target = "Lcom/simibubi/create/content/contraptions/base/KineticTileEntity;tick()V",shift = At.Shift.AFTER*/))
    private void outputFluid(CallbackInfo ci) {
        InjectMillstoneTileEntity.outputFluid((MillstoneTileEntity) (Object) this);
    }

    @Override
    public @Nullable Storage<FluidVariant> getFluidStorage(@Nullable Direction side) {
        if (side == Direction.UP || side == null) return tank.getCapability();
        return null;
    }

    @Override
    public SmartFluidTankBehaviour getTank() {
        return tank;
    }

    @Override
    public void setTank(SmartFluidTankBehaviour tank) {
        this.tank = tank;
    }

//    @Override
//    public double getOutputEnd() {
//        return outputEnd;
//    }

    @Override
    public Direction getOutputSide() {
        return outputSide;
    }

//    @Override
//    public double getPrevOutputEnd() {
//        return prevOutputEnd;
//    }

    @Override
    public boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        return super.addToGoggleTooltip(tooltip, isPlayerSneaking) | containedFluidTooltip(tooltip, isPlayerSneaking, getFluidStorage(null));
    }

//    @Override
//    public void setOutputEnd(double outputEnd) {
//        if (outputEnd > 1) outputEnd = 1;
//        this.outputEnd = outputEnd;
//    }

//    @Override
//    public void setPrevOutputEnd(double prevOutputEnd) {
//        this.prevOutputEnd = prevOutputEnd;
//    }

    @Override
    public void setOutputSide(Direction outputSide) {
        this.outputSide = outputSide;
    }

//    @Override
//    public Collection<InjectMillstoneTileEntity.FluidStream> getFluidStreams() {
//        return fluidStreams;
//    }

//    @Override
//    public double getOutputStart() {
//        return outputStart;
//    }
//
//    @Override
//    public void setOutputStart(double outputStart) {
//        this.outputStart = outputStart;
//    }
//
//    @Override
//    public double getPrevOutputStart() {
//        return prevOutputStart;
//    }
//
//    @Override
//    public void setPrevOutputStart(double prevOutputStart) {
//        this.prevOutputStart = prevOutputStart;
//    }
}
