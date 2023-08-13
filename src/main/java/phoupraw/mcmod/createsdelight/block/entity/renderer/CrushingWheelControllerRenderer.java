package phoupraw.mcmod.createsdelight.block.entity.renderer;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.simibubi.create.content.kinetics.crusher.CrushingWheelControllerBlockEntity;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import phoupraw.mcmod.createsdelight.inject.InjectCrushingWheelControllerBlockEntity;
@Environment(EnvType.CLIENT)
public class CrushingWheelControllerRenderer extends SmartBlockEntityRenderer<CrushingWheelControllerBlockEntity> {
    public CrushingWheelControllerRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(CrushingWheelControllerBlockEntity tileEntityIn, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(tileEntityIn, partialTicks, ms, buffer, light, overlay);
        var controller = (CrushingWheelControllerBlockEntity & InjectCrushingWheelControllerBlockEntity) tileEntityIn;
        double bottom = controller.getBottom();
        SmartFluidTankBehaviour tank = controller.getTank();
        if (bottom >= 0) return;
        SmartFluidTankBehaviour.TankSegment primaryTank = tank.getPrimaryTank();
        float value = primaryTank.getFluidLevel().getValue(partialTicks);
        if (value <= 0.001) return;
        float halfBreadth = Math.min(value * 5, 1) * 1.5f / 16f;
        TransformStack ts = TransformStack.cast(ms).pushPose().centre();
        FluidRenderer.renderFluidBox(primaryTank.getRenderedFluid(), -halfBreadth, (float) bottom, -halfBreadth, halfBreadth, 0, halfBreadth, buffer, ms, light, false);
        ts.popPose();
    }
}
