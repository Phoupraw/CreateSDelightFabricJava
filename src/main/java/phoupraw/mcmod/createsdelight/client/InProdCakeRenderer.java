package phoupraw.mcmod.createsdelight.client;

import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import phoupraw.mcmod.createsdelight.block.entity.InProdBlockEntity;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;

public class InProdCakeRenderer extends SafeBlockEntityRenderer<InProdBlockEntity> {
    public InProdCakeRenderer(BlockEntityRendererFactory.Context context) {}

    @Override
    protected void renderSafe(InProdBlockEntity be, float partialTicks, MatrixStack ms, VertexConsumerProvider bufferSource, int light, int overlay) {
        VoxelCake voxelCake = be.getVoxelCake();
        if (voxelCake == null) return;

    }
}
