package phoupraw.mcmod.createsdelight.block.entity.renderer;

import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.tileEntity.renderer.SmartTileEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import phoupraw.mcmod.createsdelight.block.entity.SprinklerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyPartialModels;
@Environment(EnvType.CLIENT)
public class SprinklerRenderer extends SmartTileEntityRenderer<SprinklerBlockEntity> {

    public SprinklerRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(SprinklerBlockEntity spr, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(spr, partialTicks, ms, buffer, light, overlay);
        double angle = MathHelper.lerp(partialTicks, spr.prevAngle, spr.nowAngle);
        ms.push();
        ms.translate(0.5, 0, 0.5);
        ms.multiply(new Quaternion(Direction.UP.getUnitVector(), (float) angle, false));
        ms.translate(-0.5, 0, -0.5);
        CachedBufferer.partial(MyPartialModels.SPRINKLER_LID, spr.getCachedState())
          .light(light)
          .renderInto(ms, buffer.getBuffer(RenderLayer.getCutout()));
        ms.pop();
    }
}
