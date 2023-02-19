package phoupraw.mcmod.createsdelight.block.entity.renderer;

import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.content.logistics.block.depot.DepotRenderer;
import com.simibubi.create.foundation.tileEntity.renderer.SmartTileEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.random.Random;
import phoupraw.mcmod.createsdelight.block.entity.GrillBlockEntity;

import static phoupraw.mcmod.createsdelight.block.entity.GrillBlockEntity.getHorizontalOffset;
@Environment(EnvType.CLIENT)
public class GrillRenderer extends SmartTileEntityRenderer<GrillBlockEntity> {
    public GrillRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(GrillBlockEntity grill, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(grill, partialTicks, ms, buffer, light, overlay);
        var nonEmpties = grill.getNonEmpties();
        for (int j = 0; j < nonEmpties.size(); j++) {
            int i = nonEmpties.get(j).getLeft();
            TransportedItemStack trans = nonEmpties.get(j).getRight().get();
            ms.push();
            Vec3d offset = getHorizontalOffset(j, nonEmpties.size()).add(0.5, 4.5 / 16, 0.5);
            ms.translate(offset.getX(), offset.getY(), offset.getZ());
            float progress = 0;
            if (grill.flippings[i] >= 0 && grill.flippings[i] < 10) {
                progress = MathHelper.lerp(partialTicks, grill.flippings[i], grill.flippings[i] + 1) / 10;
                ms.translate(0, 0.5 * Math.sin(Math.PI * progress), 0);
            } else if (grill.flippings[i] >= 10) {
                progress = 1;
            }
            ms.multiply(new Quaternion(new Vec3f((float) Math.cos(Math.PI / 180 * trans.angle), 0, (float) -Math.sin(Math.PI / 180 * trans.angle)), progress * 180, true));
            ms.translate(0, 1.5 / 16.0, 0);
            DepotRenderer.renderItem(ms, buffer, light, overlay, trans.stack, trans.angle, Random.create(0), Vec3d.ofCenter(grill.getPos()));
            ms.pop();
        }
    }
}
