package phoupraw.mcmod.createsdelight.block.entity.renderer;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.content.logistics.depot.DepotRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import phoupraw.mcmod.createsdelight.block.entity.IronBarSkewerBlockEntity;
public class IronBarSkewerRenderer extends KineticBlockEntityRenderer {
    public IronBarSkewerRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(KineticBlockEntity te, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        super.renderSafe(te, partialTicks, ms, buffer, light, overlay);
        var skewer = (IronBarSkewerBlockEntity) te;
        ms.push();
        Direction.Axis axis = te.getCachedState().get(Properties.AXIS);
        ms.translate(0.5, 0.5, 0.5);
        if (axis == Direction.Axis.X) {
            ms.multiply(new Quaternion(Direction.SOUTH.getUnitVector(), 90, true));
        } else if (axis == Direction.Axis.Z) {
            ms.multiply(new Quaternion(Direction.EAST.getUnitVector(), 90, true));
        }
        ms.translate(0, -0.2, 0);
        ms.multiply(new Quaternion(Direction.UP.getUnitVector(), getAngleForTe(te, te.getPos(), axis), false));
        for (int i = 0; i < IronBarSkewerBlockEntity.SIZE; i++) {
            var slot = skewer.storage.parts.get(i);
            if (slot.isResourceBlank()) continue;
            int count = 1;
            switch ((int) slot.getAmount()) {
                case 2 -> count = 4;
                case 3 -> count = 16;
                case 4 -> count = 64;
            }
            var itemStack = slot.getResource().toStack(count);
            ms.push();
            ms.translate(0, i * 0.4, 0);
            DepotRenderer.renderItem(ms, buffer, light, overlay, itemStack, i * 79, Random.create(0), Vec3d.ZERO);
            ms.pop();
        }
        ms.pop();
    }
}
