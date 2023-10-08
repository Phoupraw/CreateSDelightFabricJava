package phoupraw.mcmod.createsdelight.registry;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import phoupraw.mcmod.createsdelight.effect.SatiationStatusEffect;

public final class CSDStatusEffects {
    public static final StatusEffect SATIATION = new SatiationStatusEffect(StatusEffectType.BENEFICIAL, StatusEffects.SATURATION.getColor());
    static {
        Registry.register(Registries.STATUS_EFFECT, CSDIdentifiers.SATIATION, SATIATION);
    }
    private CSDStatusEffects() {
    }

}
