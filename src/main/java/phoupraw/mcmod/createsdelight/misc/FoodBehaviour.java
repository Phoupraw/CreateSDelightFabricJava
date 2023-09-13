package phoupraw.mcmod.createsdelight.misc;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Map;

public interface FoodBehaviour {
    double getHunger(double cubicMeters);
    double getSaturation(double cubicMeters);
    double getHungerSurpass(double cubicMeters);
    double getDuration(double cubicMeters);
    @UnmodifiableView Map<StatusEffect, Pair<StatusEffectInstance, Double>> getStatusEffects(double cubicMeters);
    void applyExtra(PlayerEntity player);
}
