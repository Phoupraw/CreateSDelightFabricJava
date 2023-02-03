package phoupraw.mcmod.createsdelight.inject;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.ApiStatus;
@ApiStatus.Internal
public class InjectLivingEntity {
    public static boolean applyInstantEffect(LivingEntity instance, StatusEffectInstance effect) {
        StatusEffect type = effect.getEffectType();
        if (!type.isInstant()) return instance.addStatusEffect(effect);
        int duration = effect.getDuration();
        int amplifier = effect.getAmplifier();
        for (int i = 0; i < duration; i++) type.applyInstantEffect(null, null, instance, amplifier, 1);
        return true;
    }
}
