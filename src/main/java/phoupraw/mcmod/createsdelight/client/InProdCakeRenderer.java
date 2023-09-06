package phoupraw.mcmod.createsdelight.client;

import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import phoupraw.mcmod.createsdelight.block.entity.InProdCakeBlockEntity;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;

public class InProdCakeRenderer extends SafeBlockEntityRenderer<InProdCakeBlockEntity> {
    public final BlockEntityRendererFactory.Context context;
    public InProdCakeRenderer(BlockEntityRendererFactory.Context context) {
        this.context = context;
    }
    @Override
    protected void renderSafe(InProdCakeBlockEntity be, float partialTicks, MatrixStack ms, VertexConsumerProvider bufferSource, int light, int overlay) {
        BlockPos relative = be.relative;
        if (relative == null) return;
        VoxelCake voxelCake = be.getVoxelCake();
        if (voxelCake == null) return;
        BakedModel bakedModel = InProdCakeModel.CACHE.getUnchecked(voxelCake);
        Direction direction = be.direction;
        int edgeLen = be.edgeLen;
        BlockPos blockPos = be.getPos();
        ms.push();
        float progress = MathHelper.lerp(partialTicks, be.preProgress, be.getProgress());
        if (direction == Direction.UP) {
            var trans = Vec3d.of(relative).multiply(progress - 1);
            ms.translate(trans.x, trans.y, trans.z);
            float scale = 1 + (edgeLen - 1) * (1 - progress);
            ms.scale(scale, scale, scale);
        } else if (direction != null) {
            var trans = direction.getUnitVector().mul(progress);
            ms.translate(trans.x, trans.y, trans.z);
        }
        context.getRenderManager().getModelRenderer().render(be.getWorld(), bakedModel, be.getCachedState(), blockPos, ms, bufferSource.getBuffer(RenderLayer.getSolid()), false, Random.create(0), 0, overlay);
        ms.pop();
    }
}
