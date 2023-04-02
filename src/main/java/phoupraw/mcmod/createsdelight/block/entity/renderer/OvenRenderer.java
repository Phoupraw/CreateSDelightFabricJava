package phoupraw.mcmod.createsdelight.block.entity.renderer;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.simibubi.create.content.logistics.block.depot.DepotRenderer;
import com.simibubi.create.foundation.tileEntity.renderer.SmartTileEntityRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import phoupraw.mcmod.createsdelight.block.entity.OvenBlockEntity;
public class OvenRenderer extends SmartTileEntityRenderer<OvenBlockEntity> {
    public OvenRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(OvenBlockEntity oven, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(oven, partialTicks, ms, buffer, light, overlay);
        TransformStack ts = TransformStack.cast(ms);
        int size = oven.getInventory().size();
        ts.pushPose();
        ts.translate(0.5, 3 / 16.0, 0.5);
        for (int i = 0; i < size; i++) {
            ItemStack itemStack = oven.getInventory().getStack(i);
            if (itemStack.isEmpty()) continue;
            ts.pushPose();
            float angle = 360f * 7 / size * i;
            ts.rotateY(angle);
            ts.translate(3.5 / 16f, 0, 3.5 / 16f);
            DepotRenderer.renderItem(ms, buffer, light, overlay, itemStack, (i * oven.getPos().hashCode()) % 360, Random.create(0), Vec3d.ZERO);
            ts.popPose();
        }
        ts.popPose();
    }
}
