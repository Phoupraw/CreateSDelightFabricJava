package phoupraw.mcmod.createsdelight.block.entity.renderer;

import com.nhoryzon.mc.farmersdelight.entity.block.CookingPotBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
@Environment(EnvType.CLIENT)
public class CookingPotRenderer implements BlockEntityRenderer<CookingPotBlockEntity> {
	@Override
	public void render(CookingPotBlockEntity pot, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

	}
}
