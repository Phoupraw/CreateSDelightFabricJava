package phoupraw.mcmod.createsdelight.block.entity.renderer;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import com.simibubi.create.foundation.tileEntity.renderer.SmartTileEntityRenderer;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import phoupraw.mcmod.createsdelight.block.CopperTunnelBlock;
import phoupraw.mcmod.createsdelight.block.entity.CopperTunnelBlockEntity;
@Environment(EnvType.CLIENT)
public class CopperTunnelRenderer extends SmartTileEntityRenderer<CopperTunnelBlockEntity> {
    public CopperTunnelRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(CopperTunnelBlockEntity te, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
//        if (Backend.canUseInstancing(te.getWorld())) return;
        SuperByteBuffer flapBuffer = CachedBufferer.partial(AllBlockPartials.BELT_TUNNEL_FLAP, te.getCachedState());
        VertexConsumer vb = buffer.getBuffer(RenderLayer.getSolid());
        Vec3d pivot = VecHelper.voxelSpace(0, 10, 1f);
        TransformStack msr = TransformStack.cast(ms);
        for (var entry : CopperTunnelBlock.Model.HORIZONTALS.entrySet()) {
            if (te.getCachedState().get(entry.getValue()) != CopperTunnelBlock.Model.CURTAIN) continue;
            Direction direction = entry.getKey();
            float horizontalAngle = AngleHelper.horizontalAngle(direction.getOpposite());
            float f = te.flappings.containsKey(direction) ? te.flappings.get(direction).getValue(partialTicks) : 0;
            ms.push();
            msr.centre().rotateY(horizontalAngle).unCentre();
            for (int segment = 0; segment <= 3; segment++) {
                ms.push();
                float intensity = segment == 3 ? 1.5f : segment + 1;
                float abs = Math.abs(f);
                float flapAngle = MathHelper.sin((float) ((1 - abs) * Math.PI * intensity)) * 30 * f * (direction.getAxis() == Direction.Axis.X ? 1 : -1);
                if (f > 0) flapAngle *= .5f;
                msr.translate(pivot).rotateX(flapAngle).translateBack(pivot);
                flapBuffer.light(light).renderInto(ms, vb);
                ms.pop();
                ms.translate(-3 / 16f, 0, 0);
            }
            ms.pop();
        }
    }
}
