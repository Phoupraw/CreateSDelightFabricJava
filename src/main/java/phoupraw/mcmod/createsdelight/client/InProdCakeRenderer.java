package phoupraw.mcmod.createsdelight.client;

import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import phoupraw.mcmod.createsdelight.block.entity.InProdCakeBlockEntity;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;

public class InProdCakeRenderer extends SafeBlockEntityRenderer<InProdCakeBlockEntity> {
    public InProdCakeRenderer(BlockEntityRendererFactory.Context context) {}
    @Override
    protected void renderSafe(InProdCakeBlockEntity be, float partialTicks, MatrixStack ms, VertexConsumerProvider bufferSource, int light, int overlay) {
        VoxelCake voxelCake = be.getVoxelCake();
        if (voxelCake == null) return;
    }
}
