package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.kinetics.belt.BeltHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(BeltHelper.class)
public class MixinBeltHelper {
    @Environment(EnvType.CLIENT)
    @Inject(method = "isItemUpright", at = @At("RETURN"), cancellable = true)
    private static void blockNotUpright(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ() && MinecraftClient.getInstance().getItemRenderer().getModel(stack, null, null, 0).hasDepth()) {
            cir.setReturnValue(false);
        }
    }
}
