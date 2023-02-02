package phoupraw.mcmod.createsdelight.block.entity.renderer;

import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.content.logistics.block.depot.DepotRenderer;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.tileEntity.renderer.SmartTileEntityRenderer;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import phoupraw.mcmod.createsdelight.block.entity.PanBlockEntity;
import phoupraw.mcmod.createsdelight.block.entity.MyBlockEntity1;

public class PanRenderer extends SmartTileEntityRenderer<PanBlockEntity> {
    public PanRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(PanBlockEntity pan, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(pan, partialTicks, ms, buffer, light, overlay);
        SmartFluidTankBehaviour.TankSegment segment = pan.getTank().getPrimaryTank();
        if (!segment.isEmpty(partialTicks)) {
            FluidRenderer.renderFluidBox(segment.getRenderedFluid(), 2 / 16f, 1 / 16f, 2 / 16f, 14 / 16f, 1 / 16f + segment.getFluidLevel().getValue(partialTicks) * 0.5f / 16f, 14 / 16f, buffer, ms, light, false);
        }
        TransportedItemStack trans = pan.getItem().getStorage().getTransported();
//        trans.angle=90;
        ms.push();
        ms.translate(0.5, 1.5 / 16.0, 0.5);
        float beltPos = MathHelper.lerp(partialTicks, trans.prevBeltPosition, trans.beltPosition);
        float sideOffset = MathHelper.lerp(partialTicks, trans.prevSideOffset, trans.sideOffset);
        Direction insertedFrom = trans.insertedFrom;
        boolean alongX = insertedFrom.getAxis().isHorizontal() && insertedFrom.rotateYClockwise().getAxis() == Direction.Axis.X;
        if (!alongX) sideOffset *= -1;
        Vec3d offset = Vec3d.of(insertedFrom.getOpposite().getVector())
          .multiply(0.5 - beltPos)
          .add(alongX ? sideOffset : 0, 0, alongX ? 0 : sideOffset)
          .multiply(0.75);
        ms.translate(offset.getX(), offset.getY(), offset.getZ());
        float progress = 0;
        switch (pan.getFlippingStage()) {
            case NOT_DONE -> progress = 0;
            case DOING -> {
                progress = MathHelper.lerp(partialTicks, pan.getFlippingTicks(), pan.getFlippingTicks() + 1) / MyBlockEntity1.FLIPPING_DURATION;
                ms.translate(0, 0.5 * Math.sin(Math.PI * progress), 0);
            }
            case DONE -> progress = 1;
        }
        ms.multiply(new Quaternion(new Vec3f((float) Math.cos(Math.PI / 180 * trans.angle), 0, (float) -Math.sin(Math.PI / 180 * trans.angle)), progress * 180, true));
        ms.translate(0, 1.5 / 16.0, 0);
        DepotRenderer.renderItem(ms, buffer, light, overlay, trans.stack, trans.angle, Random.create(0), Vec3d.ofCenter(pan.getPos()));
        ms.pop();
    }
}
