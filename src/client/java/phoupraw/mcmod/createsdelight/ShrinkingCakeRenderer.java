package phoupraw.mcmod.createsdelight;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import phoupraw.mcmod.createsdelight.block.entity.ShrinkingCakeBlockEntity;

public class ShrinkingCakeRenderer implements BlockEntityRenderer<ShrinkingCakeBlockEntity> {

    public ShrinkingCakeRenderer(BlockEntityRendererFactory.Context context) {}

    @Override
    public void render(ShrinkingCakeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

    }

}
