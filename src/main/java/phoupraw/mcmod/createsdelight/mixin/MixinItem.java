package phoupraw.mcmod.createsdelight.mixin;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoupraw.mcmod.createsdelight.inject.InjectItem;

import java.util.List;
@Mixin(Item.class)
public class MixinItem {
    @Inject(method = "appendTooltip", at = @At("RETURN"))
    private void addFoodEffectTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context, CallbackInfo ci) {
        InjectItem.addFoodEffectTooltip(stack, world, tooltip, context);
    }
}
