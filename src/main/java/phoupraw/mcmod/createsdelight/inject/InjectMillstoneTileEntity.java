package phoupraw.mcmod.createsdelight.inject;

import com.google.common.base.Predicates;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.kinetics.millstone.MillstoneBlockEntity;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import com.simibubi.create.foundation.utility.BlockHelper;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

///**
// * @see MixinMillstoneTileEntity
// */
@ApiStatus.Internal
public interface InjectMillstoneTileEntity {

    static void addBehaviours(MillstoneBlockEntity millstone0, List<BlockEntityBehaviour> behaviours, CallbackInfo ci) {
        var millstone = (MillstoneBlockEntity & InjectMillstoneTileEntity) millstone0;
        var tank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.OUTPUT, millstone, 1, FluidConstants.BUCKET, false);
        behaviours.add(tank);
        millstone.setTank(tank);
    }
    static void fluidResult(MillstoneBlockEntity millstone, SmartFluidTankBehaviour tank, MillingRecipe lastRecipe, CallbackInfo ci) {
        try (var transa = TransferUtil.getTransaction()) {
            for (FluidStack fluidResult : lastRecipe.getFluidResults()) {
                if (tank.getCapability().insert(fluidResult.getType(), fluidResult.getAmount(), transa) != fluidResult.getAmount()) {
                    ci.cancel();
                    break;
                }
            }
            transa.commit();
        }
    }

    static void lazyTick(MillstoneBlockEntity millstone0) {
        var millstone = (MillstoneBlockEntity & InjectMillstoneTileEntity) millstone0;
        //        CreateSDelight.LOGGER.info(millstone.getOutputProgress());
        //        if (millstone.getOutputEnd() < 1) return;
        World world = millstone.getWorld();
        //noinspection ConstantConditions
        if (world.isClient() || millstone.getOutputSide() == null) return;
        var basin = (BasinBlockEntity) world.getBlockEntity(millstone.getPos().down().offset(millstone.getOutputSide()));
        //noinspection ConstantConditions
        StorageUtil.move(millstone.getTank().getCapability(), basin.inputTank.getCapability(), Predicates.alwaysTrue(), FluidConstants.INGOT, null);
    }

    static void outputFluid(MillstoneBlockEntity millstone0) {
        var millstone = (MillstoneBlockEntity & InjectMillstoneTileEntity) millstone0;
        //        for (FluidStream fluidStream : millstone.getFluidStreams()) {
        //            fluidStream.start().tickChaser();
        //            fluidStream.end().tickChaser();
        //        }
        World world = millstone.getWorld();
        //        double flowSpeed = 30.0 / FluidVariantAttributes.getViscosity(millstone.getTank().getPrimaryHandler().getResource(), world);
        //        millstone.setPrevOutputEnd(millstone.getOutputEnd());
        //        millstone.setPrevOutputStart(millstone.getOutputStart());
        //        if (millstone.getOutputStart() >= 0 && millstone.getOutputStart() < 1) {
        //            millstone.setOutputStart(millstone.getOutputStart() + flowSpeed);
        //        }
        boolean b = false;
        for (Direction direction : Direction.Type.HORIZONTAL) {
            BlockPos pos1 = millstone.getPos().offset(direction);
            BlockPos pos2 = pos1.down();
            //noinspection ConstantConditions
            if (!world.getBlockState(pos2).isOf(AllBlocks.BASIN.get()) || BlockHelper.hasBlockSolidSide(world.getBlockState(pos1), world, pos1, Direction.DOWN)) continue;
            //            if (millstone.getOutputEnd() < 1) {
            //                millstone.setOutputEnd(millstone.getOutputEnd() + flowSpeed);
            //            }
            millstone.setOutputSide(direction);
            b = true;
            break;
        }
        if (!b) {
            millstone.setOutputSide(null);
            //            millstone.setPrevOutputEnd(0);
            //            millstone.setOutputEnd(0);
        }
    }
    static void checkTankFull(MillstoneBlockEntity millstone0, CallbackInfo ci) {
        var millstone = (MillstoneBlockEntity & InjectMillstoneTileEntity) millstone0;
        SmartFluidTank primaryHandler = millstone.getTank().getPrimaryHandler();
        if (primaryHandler.getAmount() == primaryHandler.getCapacity()) {
            ci.cancel();
        }
    }
    static void renderFluidBox(FluidStack fluidStack, Box box, VertexConsumerProvider buffer, MatrixStack ms, int light, boolean renderBottom) {
        FluidRenderer.renderFluidBox(fluidStack, (float) box.minX, (float) box.minY, (float) box.minZ, (float) box.maxX, (float) box.maxY, (float) box.maxZ, buffer, ms, light, true);
    }
    @Environment(EnvType.CLIENT)
    static void renderSafe(KineticBlockEntity te, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        var millstone = (MillstoneBlockEntity & InjectMillstoneTileEntity) te;
        var outputSide = millstone.getOutputSide();
        if (outputSide == null) return;
        SmartFluidTankBehaviour tank = millstone.getTank();
        float value = tank.getPrimaryTank().getFluidLevel().getValue(partialTicks);
        if (value <= 0.001) return;
        var fluidStack = tank.getPrimaryTank().getRenderedFluid();
        float angle = outputSide.asRotation();
        TransformStack ts = TransformStack.cast(ms)
          .pushPose()
          .translate(0.5, 0, 0.5)
          .rotateY(angle);
        float fluidLevel = Math.min(value * 5, 1);
        double p = 1 / 16.0;
        double halfWidth = 1.5 * p * fluidLevel;
        double breadth = p * fluidLevel;
        double height = 6 * p;
        double depth = 14 * p;
        Vec3d v1 = new Vec3d(-halfWidth, height, 0);
        Vec3d v2 = new Vec3d(halfWidth, height + breadth, 0.5 + breadth);
        Box box = new Box(v1, v2);
        renderFluidBox(fluidStack, box, buffer, ms, light, true);
        v1 = new Vec3d(-halfWidth, height, 0.5);
        v2 = new Vec3d(halfWidth, 0, 0.5 + breadth);
        box = new Box(v1, v2);
        renderFluidBox(fluidStack, box, buffer, ms, light, true);
        v1 = new Vec3d(-halfWidth, breadth, 0.5 + 2 * p);
        v2 = new Vec3d(halfWidth, 0, 0.5);
        box = new Box(v1, v2);
        renderFluidBox(fluidStack, box, buffer, ms, light, true);
        v1 = new Vec3d(-halfWidth, breadth, 0.5 + p * 2 + breadth);
        v2 = new Vec3d(halfWidth, -depth, 0.5 + p * 2);
        box = new Box(v1, v2);
        renderFluidBox(fluidStack, box, buffer, ms, light, true);
        ts.popPose();
    }
    //    @Environment(EnvType.CLIENT)
    //    static void renderSafe1(KineticBlockEntity te, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
    //        var millstone = (MillstoneBlockEntity & InjectMillstoneBlockEntity) te;
    //        var progress = MathHelper.lerp(partialTicks, millstone.getPrevOutputEnd(), millstone.getOutputEnd());
    //        var outputSide = millstone.getOutputSide();
    //        if (progress <= 0||outputSide==null) return;
    //        var tank = millstone.getTank();
    //        var fluidStack = tank.getPrimaryTank().getRenderedFluid();
    //        float angle = outputSide.asRotation();
    //        TransformStack ts = TransformStack.cast(ms)
    //          .pushPose()
    //          .translate(0.5, 0, 0.5)
    //          .rotateY(angle);
    //        double p = 1 / 16.0;
    //        double width = 2 * p;
    //        double margin = 4 * p;
    //        double height = 6 * p;
    //        double depth = 14 * p;
    //        double valve1 = 0.2, valve2 = 0.5;
    //        Vec3d v11 = new Vec3d(-width, height, 0.5 + p - margin);
    //        Vec3d v12 = new Vec3d(width, height + p, 0.5 + p - margin + margin * Math.min(progress, valve1) / valve1);
    //        Box b1 = new Box(v11, v12);
    //        FluidRenderer.renderFluidBox(fluidStack, (float) b1.minX, (float) b1.minY, (float) b1.minZ, (float) b1.maxX, (float) b1.maxY, (float) b1.maxZ, buffer, ms, light, true);
//        if (progress >= valve1) {
//            Vec3d v21 = new Vec3d(-width, height, 0.5);
//            Vec3d v22 = new Vec3d(width, height * (1 - (Math.min(progress, valve2) - valve1) / (valve2 - valve1)), 0.5 + p);
//            Box b2 = new Box(v21, v22);
//            FluidRenderer.renderFluidBox(fluidStack, (float) b2.minX, (float) b2.minY, (float) b2.minZ, (float) b2.maxX, (float) b2.maxY, (float) b2.maxZ, buffer, ms, light, true);
//            if (progress >= valve2) {
//                Vec3d v31 = new Vec3d(-width, p, 0.5 + p);
//                Vec3d v32 = new Vec3d(width, -depth * (progress - valve2) / (1 - valve2), 0.5 + p * 3);
//                Box b3 = new Box(v31, v32);
//                FluidRenderer.renderFluidBox(fluidStack, (float) b3.minX, (float) b3.minY, (float) b3.minZ, (float) b3.maxX, (float) b3.maxY, (float) b3.maxZ, buffer, ms, light, true);
//            }
//        }
//
//        ts.popPose();
//    }
    SmartFluidTankBehaviour getTank();
    void setTank(SmartFluidTankBehaviour tank);
    //    double getOutputEnd();
//    double getPrevOutputEnd();
    @Nullable Direction getOutputSide();
    //    void setOutputEnd(double outputEnd);
//    void setPrevOutputEnd(double prevOutputEnd);
    void setOutputSide(@Nullable Direction outputSide);
    //    Collection<FluidStream> getFluidStreams();
//    double getOutputStart();
//    void setOutputStart(double outputStart);
//    double getPrevOutputStart();
//    void setPrevOutputStart(double prevOutputStart);
//
//    record FluidStream(Direction side, FluidVariant fluidVariant, LerpedFloat start, LerpedFloat end) {
//        public FluidStream(Direction side, FluidVariant fluidVariant) {
//            this(side, fluidVariant, LerpedFloat.linear().startWithValue(0), LerpedFloat.linear().startWithValue(0));
//        }
//    }
}
