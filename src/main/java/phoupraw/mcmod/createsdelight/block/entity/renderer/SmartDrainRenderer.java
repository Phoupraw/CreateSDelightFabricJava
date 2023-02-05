package phoupraw.mcmod.createsdelight.block.entity.renderer;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.simibubi.create.content.contraptions.fluids.actors.ItemDrainRenderer;
import com.simibubi.create.content.contraptions.relays.belt.BeltHelper;
import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.content.logistics.block.depot.DepotRenderer;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.tileEntity.renderer.SmartTileEntityRenderer;
import com.simibubi.create.foundation.utility.VecHelper;
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
import phoupraw.mcmod.createsdelight.block.entity.SmartDrainBlockEntity;

import java.util.ArrayList;
import java.util.List;
public class SmartDrainRenderer extends SmartTileEntityRenderer<SmartDrainBlockEntity> {
    public static final List<Renderer> SURFACE_RENDERERS = new ArrayList<>();
    static {
        SURFACE_RENDERERS.add((drain, partialTicks, ms, buffer, light, overlay) -> {

        });
    }
    /**
     * @see ItemDrainRenderer#renderItem
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
        ms.translate(.5f, 15 / 16f, .5f);
        msr.nudge(0);
        float offset = MathHelper.lerp(partialTicks, transp.prevBeltPosition, transp.beltPosition);
        float sideOffset = MathHelper.lerp(partialTicks, transp.prevSideOffset, transp.sideOffset);
        Direction insertedFrom = transp.insertedFrom;
        if (insertedFrom.getAxis().isHorizontal()) {
            Vec3d offsetVec = Vec3d.of(insertedFrom.getOpposite().getVector()).multiply(.5f - offset);
            ms.translate(offsetVec.x, offsetVec.y, offsetVec.z);
            boolean alongX = insertedFrom.rotateYClockwise().getAxis() == Direction.Axis.X;
            if (!alongX) sideOffset *= -1;
            ms.translate(alongX ? sideOffset : 0, 0, alongX ? 0 : sideOffset);
        }
        DepotRenderer.renderItem(ms, buffer, light, overlay, transp.stack, transp.angle, Random.create(0), Vec3d.ZERO);
        ms.pop();
    }

    public SmartDrainRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(SmartDrainBlockEntity drain, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(drain, partialTicks, ms, buffer, light, overlay);
        var surface = drain.surface;
        ItemStack itemStack = surface.stack;
        if (!itemStack.isEmpty()) {
            float beltPosition = MathHelper.lerp(partialTicks, surface.prevBeltPosition, surface.beltPosition);
            float sideOffset = MathHelper.lerp(partialTicks, surface.prevSideOffset, surface.sideOffset);
            Direction insertedFrom = surface.insertedFrom;
            if (insertedFrom.getAxis().isHorizontal()) {
                renderLikeDrain(surface, drain.getPos(), partialTicks, ms, buffer, light, overlay);
            } else {
                renderLikeDepot(surface, partialTicks, ms, buffer, light, overlay);
            }
        }
        SmartFluidTankBehaviour.TankSegment segment = drain.tank.getPrimaryTank();
        var fluidStack = segment.getRenderedFluid();
        if (!fluidStack.isEmpty()) {
            FluidRenderer.renderFluidBox(fluidStack, 2 / 16f, 2 / 16f, 2 / 16f, 14 / 16f, 2 / 16f + segment.getFluidLevel().getValue(partialTicks) * 10 / 16f, 14 / 16f, buffer, ms, light, false);
        }
        int surfaceRenderer = drain.surfaceRenderer;
        if (surfaceRenderer >= 0) SURFACE_RENDERERS.get(surfaceRenderer).render(drain, partialTicks, ms, buffer, light, overlay);
    }

    @FunctionalInterface
    public interface Renderer {
        void render(SmartDrainBlockEntity drain, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay);
    }
}
