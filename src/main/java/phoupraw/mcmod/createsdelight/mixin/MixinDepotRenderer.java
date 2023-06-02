package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.logistics.block.depot.DepotRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.json.ModelTransformation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
@Mixin(DepotRenderer.class)
@Environment(EnvType.CLIENT)
public class MixinDepotRenderer {
    @ModifyArg(method = "renderItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;IILnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"))
    private static ModelTransformation.Mode fixed2ground(ModelTransformation.Mode ignored) {
        return ModelTransformation.Mode.GROUND;
    }
}
