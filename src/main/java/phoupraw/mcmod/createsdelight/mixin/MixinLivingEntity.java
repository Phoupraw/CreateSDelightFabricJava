package phoupraw.mcmod.createsdelight.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import phoupraw.mcmod.createsdelight.inject.InjectLivingEntity;
@Mixin(LivingEntity.class)
public class MixinLivingEntity {
    /**
     * 让吃食物也能被施加瞬间效果
     */
    @Redirect(method = "applyFoodEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z"))
    private boolean applyInstantEffect(LivingEntity instance, StatusEffectInstance effect) {
        return InjectLivingEntity.applyInstantEffect(instance, effect);
    }
}
