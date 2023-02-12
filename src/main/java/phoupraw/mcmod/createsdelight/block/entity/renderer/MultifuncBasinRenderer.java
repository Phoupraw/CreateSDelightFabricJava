package phoupraw.mcmod.createsdelight.block.entity.renderer;

import com.simibubi.create.foundation.tileEntity.renderer.SmartTileEntityRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import phoupraw.mcmod.createsdelight.block.entity.MultifuncBasinBlockEntity;
public class MultifuncBasinRenderer extends SmartTileEntityRenderer<MultifuncBasinBlockEntity> {
    public MultifuncBasinRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(MultifuncBasinBlockEntity tileEntityIn, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(tileEntityIn, partialTicks, ms, buffer, light, overlay);
    }
}
