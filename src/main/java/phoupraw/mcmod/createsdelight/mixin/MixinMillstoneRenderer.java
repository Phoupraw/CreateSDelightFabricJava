package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import com.simibubi.create.content.contraptions.components.millstone.MillstoneRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import phoupraw.mcmod.createsdelight.inject.InjectMillstoneTileEntity;
@Mixin(MillstoneRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class MixinMillstoneRenderer extends KineticTileEntityRenderer {

    public MixinMillstoneRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(KineticTileEntity te, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
        InjectMillstoneTileEntity.renderSafe(te, partialTicks, ms, buffer, light, overlay);
    }
}
