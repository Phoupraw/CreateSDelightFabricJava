package phoupraw.mcmod.createsdelight.block.entity.renderer;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.simibubi.create.content.logistics.block.depot.DepotRenderer;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.tileEntity.renderer.SmartTileEntityRenderer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import phoupraw.mcmod.createsdelight.block.entity.OvenBlockEntity;
public class OvenRenderer extends SmartTileEntityRenderer<OvenBlockEntity> {
    public OvenRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(OvenBlockEntity oven, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(oven, partialTicks, ms, buffer, light, overlay);
        TransformStack ts = TransformStack.cast(ms);
        float fluidLevel = 0;
        for (SmartFluidTankBehaviour.TankSegment tank : oven.getTank().getTanks()) {
            if (tank.isEmpty(partialTicks)) continue;
            fluidLevel = tank.getFluidLevel().getValue(partialTicks);
            float min = 1 / 16f;
            float max = 15 / 16f;
            FluidRenderer.renderFluidBox(tank.getRenderedFluid(), min, min, min, min + (max - min) * fluidLevel, max, max, buffer, ms, light, false);
            break;
        }
        int size = oven.getInventory().size();
        ts.pushPose();
        if (fluidLevel > 0.1) {
            ts.translateY(MathHelper.clamp(fluidLevel, 0, 0.8));
        }
        for (int i = 0; i < size; i++) {
            ItemStack itemStack = oven.getInventory().getStack(i);
            if (itemStack.isEmpty()) continue;
            ts.pushPose();
            ts.translateX(0.3);
            float angle = 360f * 7 / size * i;
            ts.rotateY(angle);
            if (fluidLevel > 0.1) {
                ts.translateY((MathHelper.sin(AnimationTickHolder.getRenderTime(oven.getWorld()) / 12f + angle) + 1.5f) * 1 / 32f);
            }
            DepotRenderer.renderItem(ms, buffer, light, overlay, itemStack, 0, oven.getWorld().getRandom(), Vec3d.ZERO);
            ts.popPose();
        }
        ts.popPose();
    }
}
