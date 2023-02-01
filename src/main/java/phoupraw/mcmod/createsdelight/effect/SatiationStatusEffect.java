package phoupraw.mcmod.createsdelight.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
public class SatiationStatusEffect extends InstantStatusEffect {
    public SatiationStatusEffect(StatusEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }
    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity player) {
            int amount = amplifier + 1;
            var manager = player.getHungerManager();
            int food0 = manager.getFoodLevel();
            int food = Math.min(20, food0 + amount);
            amount -= food - food0;
            manager.setFoodLevel(food);
            if (amount > 0) {
                float saturation = Math.min(food, manager.getSaturationLevel() + amount);
                manager.setSaturationLevel(saturation);
            }
        }
    }
}
