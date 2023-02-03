package phoupraw.mcmod.createsdelight.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
public class SatiationStatusEffect extends InstantStatusEffect {
    public static float apply(LivingEntity target, int amplifier) {
        float recovered = 0;
        if (target instanceof PlayerEntity player) {
            int amount = amplifier + 1;
            var manager = player.getHungerManager();
            int food0 = manager.getFoodLevel();
            int food = Math.min(20, food0 + amount);
            recovered = food - food0;
            amount -= recovered;
            manager.setFoodLevel(food);
            if (amount > 0) {
                float satu0 = manager.getSaturationLevel();
                float satu = Math.min(food, satu0 + amount);
                recovered += satu - satu0;
                manager.setSaturationLevel(satu);
            }
        }
        return recovered;
    }

    public SatiationStatusEffect(StatusEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        apply(target, amplifier);
    }

    @Override
    public void applyUpdateEffect(LivingEntity target, int amplifier) {
        apply(target, amplifier);
    }
}
