package phoupraw.mcmod.createsdelight.client;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.outliner.ChasingAABBOutline;
import com.simibubi.create.foundation.outliner.Outliner;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import phoupraw.mcmod.createsdelight.block.CakeOvenBlock;
import phoupraw.mcmod.createsdelight.block.entity.CakeOvenBlockEntity;
import phoupraw.mcmod.createsdelight.mixin.AChasingAABBOutline;

public class CakeOvenRenderer extends KineticBlockEntityRenderer<CakeOvenBlockEntity> {
    public final BlockEntityRendererFactory.Context context;
    public CakeOvenRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
        this.context = context;
    }
    @Override
    protected void renderSafe(CakeOvenBlockEntity be, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        if (be.isNotWorking()) return;
        TransformStack ts = TransformStack.cast(ms)
          .pushPose()
          .translate(0, 1, 0);
        double len0 = MathHelper.lerp(partialTicks, be.prevOutline0Len, be.outline0Len);
        int edgeLen = be.getBehaviour(ScrollValueBehaviour.TYPE).getValue();
        BlockState cachedState = be.getCachedState();
        var biDirection = CakeOvenBlock.BI_DIRECTION.get(cachedState.get(CakeOvenBlock.FACING));
        for (var entry : be.len1s.entrySet()) {
            var voxelCake = entry.getValue();
            if (voxelCake == null) continue;
            int len1 = entry.getKey();
            double len2 = 2 * len1 - len0;
            double len3 = (double) len1 / edgeLen;
            len2 = Math.max(1, len2);
            double delta = (len1 - len2) / (len1 - 1);
            Outliner.OutlineEntry outlineEntry = CreateClient.OUTLINER.getOutlines().get(len1);
            if (outlineEntry != null && outlineEntry.getOutline() instanceof ChasingAABBOutline aabbOutline0) {
                var aabbOutline = (ChasingAABBOutline & AChasingAABBOutline) aabbOutline0;
                Box interpolated = AChasingAABBOutline.invokeInterpolateBBs(aabbOutline.getPrevBB(), aabbOutline.getBounds(), partialTicks);
                len2 = interpolated.getYLength();
                delta = (len1 - len2) / (len1 - len3);
            }
            float scale = (float) MathHelper.lerp(delta, edgeLen, 1);
            ts.pushPose();
            for (Direction direction : biDirection) {
                if (direction.getDirection() == Direction.AxisDirection.NEGATIVE) {
                    ts.translate(direction.getUnitVector().mul(scale - 1));
                }
            }
            ts.scale(scale);
            BakedModel model = InProdCakeModel.CACHE.getUnchecked(voxelCake);
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderLayers.getBlockLayer(cachedState));
            context.getRenderManager().getModelRenderer().render(be.getWorld(), model, Blocks.GLOWSTONE.getDefaultState(), be.getPos().up(), ms, vertexConsumer, false, Random.create(0), 0, overlay);
            ts.popPose();
        }
        ts.popPose();
    }
}
