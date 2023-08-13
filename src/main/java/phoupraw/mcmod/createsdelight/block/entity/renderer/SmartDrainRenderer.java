package phoupraw.mcmod.createsdelight.block.entity.renderer;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.simibubi.create.content.fluids.drain.ItemDrainBlockEntity;
import com.simibubi.create.content.fluids.drain.ItemDrainRenderer;
import com.simibubi.create.content.kinetics.belt.BeltHelper;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.logistics.depot.DepotRenderer;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.createsdelight.behaviour.DepotItemBehaviour;
import phoupraw.mcmod.createsdelight.behaviour.EmptyingBehaviour;
import phoupraw.mcmod.createsdelight.block.entity.SmartDrainBlockEntity;
import phoupraw.mcmod.createsdelight.mixin.AccessLerpedFloat;
import phoupraw.mcmod.createsdelight.storage.BlockingTransportedStorage;
@Environment(EnvType.CLIENT)
public class SmartDrainRenderer extends SmartBlockEntityRenderer<SmartDrainBlockEntity> {
    /**
     * @see ItemDrainRenderer#renderItem(ItemDrainBlockEntity, float, MatrixStack, VertexConsumerProvider, int, int)
     */
    public static void renderLikeDrain(@NotNull TransportedItemStack transp, BlockPos blockPos, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        if (transp.stack.isEmpty()) return;
        TransformStack msr = TransformStack.cast(ms);
        Direction insertedFrom = transp.insertedFrom;
        if (!insertedFrom.getAxis().isHorizontal()) return;
        ms.push();
        ms.translate(.5f, 15 / 16f, .5f);
        msr.nudge(0);
        float offset = MathHelper.lerp(partialTicks, transp.prevBeltPosition, transp.beltPosition);
        float sideOffset = MathHelper.lerp(partialTicks, transp.prevSideOffset, transp.sideOffset);
        Vec3d offsetVec = Vec3d.of(insertedFrom.getOpposite().getVector()).multiply(.5f - offset);
        ms.translate(offsetVec.x, offsetVec.y, offsetVec.z);
        boolean alongX = insertedFrom.rotateYClockwise().getAxis() == Direction.Axis.X;
        if (!alongX) sideOffset *= -1;
        ms.translate(alongX ? sideOffset : 0, 0, alongX ? 0 : sideOffset);
        ItemStack itemStack = transp.stack;
        Random r = Random.create(0);
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        int count = MathHelper.floorLog2(itemStack.getCount()) / 2;
        boolean renderUpright = BeltHelper.isItemUpright(itemStack);
        boolean blockItem = itemRenderer.getModel(itemStack, null, null, 0).hasDepth();
        if (renderUpright) ms.translate(0, 3 / 32d, 0);
        int positive = insertedFrom.getDirection().offset();
        float verticalAngle = positive * offset * 360;
        if (insertedFrom.getAxis() != Direction.Axis.X) msr.rotateX(verticalAngle);
        if (insertedFrom.getAxis() != Direction.Axis.Z) msr.rotateZ(-verticalAngle);
        if (renderUpright) {
            Entity renderViewEntity = MinecraftClient.getInstance().cameraEntity;
            if (renderViewEntity != null) {
                Vec3d positionVec = renderViewEntity.getPos();
                Vec3d vectorForOffset = Vec3d.ofCenter(blockPos).add(offsetVec);
                Vec3d diff = vectorForOffset.subtract(positionVec);
                if (insertedFrom.getAxis() != Direction.Axis.X)
                    diff = VecHelper.rotate(diff, verticalAngle, Direction.Axis.X);
                if (insertedFrom.getAxis() != Direction.Axis.Z)
                    diff = VecHelper.rotate(diff, -verticalAngle, Direction.Axis.Z);
                float yRot = (float) MathHelper.atan2(diff.z, -diff.x);
                ms.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion((float) (yRot - Math.PI / 2)));
            }
            ms.translate(0, 0, -1 / 16f);
        }
        for (int i = 0; i <= count; i++) {
            ms.push();
            if (blockItem) ms.translate(r.nextFloat() * .0625f * i, 0, r.nextFloat() * .0625f * i);
            ms.scale(.5f, .5f, .5f);
            if (!blockItem && !renderUpright) msr.rotateX(90);
            itemRenderer.renderItem(itemStack, ModelTransformation.Mode.FIXED, light, overlay, ms, buffer, 0);
            ms.pop();
            if (!renderUpright) {
                if (!blockItem) msr.rotateY(10);
                ms.translate(0, blockItem ? 1 / 64d : 1 / 16d, 0);
            } else {
                ms.translate(0, 0, -1 / 16f);
            }
        }
        ms.pop();
    }

    /**
     * @see DepotRenderer#renderItemsOf
     */
    public static void renderLikeDepot(@NotNull TransportedItemStack transp, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        TransformStack msr = TransformStack.cast(ms);
        ms.push();
//        ms.translate(.5f, 15 / 16f, .5f);
        msr.nudge(0);
        Vec3d offset = BlockingTransportedStorage.getHorizontalOffset(transp, partialTicks);
        ms.translate(offset.getX(), 15 / 16f, offset.getZ());
        DepotRenderer.renderItem(ms, buffer, light, overlay, transp.stack, transp.angle, Random.create(0), Vec3d.ZERO);
        ms.pop();
    }

    public static void render(SmartFluidTankBehaviour tank, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        SmartFluidTankBehaviour.TankSegment segment = tank.getPrimaryTank();
        if (!tank.getPrimaryHandler().isEmpty()) {
            FluidStack fluidStack = segment.getRenderedFluid();
            LerpedFloat fluidLevel = segment.getFluidLevel();
            FluidRenderer.renderFluidBox(fluidStack, 2 / 16f, 2 / 16f, 2 / 16f, 14 / 16f, 2 / 16f + fluidLevel.getValue(partialTicks) * 10 / 16f, 14 / 16f, buffer, ms, light, false);
            float value = fluidLevel.getValue(0.5f);
            float previousValue = ((AccessLerpedFloat) fluidLevel).getPreviousValue();
            float chaseTarget = fluidLevel.getChaseTarget();
            if (chaseTarget > value) {
                float radius = (value - previousValue);
//                FluidRenderer.renderFluidBox(fluidStack, 0.5f - radius, 2 / 16f, 0.5f - radius, 0.5f + radius, 13 / 16f, 0.5f + radius, buffer, ms, light, false);
            }
        }
    }

    public SmartDrainRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(SmartDrainBlockEntity drain, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(drain, partialTicks, ms, buffer, light, overlay);
        drain.getRolling().render(partialTicks, ms, buffer, light, overlay);
        render(drain.getBehaviour(SmartFluidTankBehaviour.TYPE), partialTicks, ms, buffer, light, overlay);
        drain.getBehaviour(DepotItemBehaviour.TYPE).render(partialTicks, ms, buffer, light, overlay);
        drain.getBehaviour(EmptyingBehaviour.TYPE).render(partialTicks, ms, buffer, light, overlay);
    }

}
