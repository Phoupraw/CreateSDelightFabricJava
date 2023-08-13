package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.kinetics.millstone.MillstoneRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import phoupraw.mcmod.createsdelight.inject.InjectMillstoneBlockEntity;
@Mixin(MillstoneRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class MixinMillstoneRenderer extends KineticBlockEntityRenderer {

    public MixinMillstoneRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(KineticBlockEntity te, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
        InjectMillstoneBlockEntity.renderSafe(te, partialTicks, ms, buffer, light, overlay);
    }
}
