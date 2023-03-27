package phoupraw.mcmod.createsdelight.inject;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.createsdelight.item.StatusEffectsBlockItem;
@ApiStatus.Internal
public class InjectLivingEntity {
    public static boolean applyInstantEffect(LivingEntity instance, StatusEffectInstance effect) {
        return StatusEffectsBlockItem.apply(instance, effect);
    }
}
