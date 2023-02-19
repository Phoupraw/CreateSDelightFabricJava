package phoupraw.mcmod.createsdelight.block.entity.renderer;

import com.simibubi.create.content.logistics.block.depot.DepotRenderer;
import com.simibubi.create.foundation.tileEntity.renderer.SmartTileEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import phoupraw.mcmod.createsdelight.block.entity.BambooSteamerBlockEntity;@Environment(EnvType.CLIENT)
public class BambooSteamerRenderer extends SmartTileEntityRenderer<BambooSteamerBlockEntity> {
    public BambooSteamerRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(BambooSteamerBlockEntity steamer, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(steamer, partialTicks, ms, buffer, light, overlay);
        for (int i = 0; i < steamer.inventory.size(); i++) {
            ItemStack itemStack = steamer.inventory.getStack(i);
            if (itemStack.isEmpty()) continue;
            Vec3d offset = new Vec3d(0, 3 / 16.0, 0).add(BambooSteamerBlockEntity.getOffset(i));
            ms.push();
            ms.translate(offset.getX(),offset.getY(),offset.getZ());
            DepotRenderer.renderItem(ms, buffer, light, overlay, itemStack, steamer.getPos().hashCode() + i * 71, Random.create(0), Vec3d.ZERO);
            ms.pop();
        }
    }
}
