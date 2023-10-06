package phoupraw.mcmod.createsdelight.misc;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Map;

public interface FoodBehaviour {
    double getHunger(VoxelRecord voxelRecord, double cubicMeters);
    double getSaturation(VoxelRecord voxelRecord, double cubicMeters);
    double getHungerSurpass(VoxelRecord voxelRecord, double cubicMeters);
    default double getDuration(double cubicMeters) {
        return 0;
    }
    @UnmodifiableView
    default Map<StatusEffect, Pair<StatusEffectInstance, Double>> getStatusEffects(double cubicMeters) {
        return Map.of();
    }
    default void applyExtra(PlayerEntity player) {

    }
}
