package phoupraw.mcmod.createsdelight.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.effect.SatiationStatusEffect;
public final class CDStatusEffects {
    public static final StatusEffect SATIATION = new SatiationStatusEffect(StatusEffectCategory.BENEFICIAL, StatusEffects.SATURATION.getColor());
    static {
        Registry.register(Registry.STATUS_EFFECT, CDIdentifiers.SATIATION, SATIATION);
    }
    private CDStatusEffects() {
    }
}
