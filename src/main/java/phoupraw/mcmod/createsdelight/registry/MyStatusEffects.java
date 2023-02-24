package phoupraw.mcmod.createsdelight.registry;

import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.effect.SatiationStatusEffect;
public final class MyStatusEffects {
    public static final SatiationStatusEffect SATIATION = new SatiationStatusEffect(StatusEffectCategory.BENEFICIAL, StatusEffects.SATURATION.getColor());
    static {
        Registry.register(Registry.STATUS_EFFECT, MyIdentifiers.SATIATION, SATIATION);
    }
    private MyStatusEffects() {}
}
