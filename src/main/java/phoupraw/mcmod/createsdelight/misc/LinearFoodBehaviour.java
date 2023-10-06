package phoupraw.mcmod.createsdelight.misc;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

/**
 没有状态效果，没有额外效果 */
public class LinearFoodBehaviour implements FoodBehaviour {
    public static final LinearFoodBehaviour CAKE;
    static {
        double cubicMeters = (double) 14 * 14 * 8 / (16 * 16 * 16);
        double hunger = 2 * 7;
        double saturation = 0.4 * 7;
        CAKE = new LinearFoodBehaviour(hunger / cubicMeters, saturation / cubicMeters, 0, 0);
    }
    /**
     只考虑饥饿值、饱食度<s>、使用时间（是否是零食）</s>
     * @param minuend 被减数
     * @param subtrahend 减数
     * @param cubicMeters 体积（立方米）
     * @return (被减数 - 减数)÷体积
     */
    public static LinearFoodBehaviour subtract(FoodComponent minuend, FoodComponent subtrahend, double cubicMeters) {
        return linear(Pair.of(minuend, 1 / cubicMeters), Pair.of(subtrahend, -1 / cubicMeters));
        //double hunger = minuend.getHunger() - subtrahend.getHunger();
        //double saturation = (minuend.getHunger() * minuend.getSaturationModifier() - subtrahend.getHunger() * subtrahend.getSaturationModifier()) * 2;
        //double duration = 0;//(32 + ((minuend.isSnack() ? 0 : 1) + (subtrahend.isSnack() ? 0 : 1)) * 16)*cubicMeters;
        //return new LinearFoodBehaviour(hunger / cubicMeters, saturation / cubicMeters, 0, duration / cubicMeters);
    }
    /**
     只考虑饥饿值、饱食度<s>、使用时间（是否是零食）</s>
     @param fc 原本食物属性
     @param cubicMeters 体积（立方米）
     @return 原本食物属性÷体积
     */
    public static LinearFoodBehaviour of(FoodComponent fc, double cubicMeters) {
        double hunger = fc.getHunger();
        double saturation = fc.getHunger() * fc.getSaturationModifier() * 2;
        double duration = fc.isSnack() ? 16 : 32;
        return new LinearFoodBehaviour(hunger / cubicMeters, saturation / cubicMeters, 0, duration / cubicMeters);
    }
    @SafeVarargs
    public static LinearFoodBehaviour linear(Pair<FoodComponent, Double>... food2factors) {
        double hunger = 0;
        double saturation = 0;
        for (Pair<FoodComponent, Double> food2factor : food2factors) {
            FoodComponent fc = food2factor.getKey();
            double factor = food2factor.getValue();
            hunger += fc.getHunger() * factor;
            saturation += fc.getHunger() * fc.getSaturationModifier() * 2 * factor;
        }
        return new LinearFoodBehaviour(hunger, saturation, 0, 0);
    }
    public final double hunger;
    public final double saturation;
    public final double hungerSurpass;
    public final double duration;
    public LinearFoodBehaviour(double hunger, double saturation, double hungerSurpass, double duration) {
        this.hunger = hunger;
        this.saturation = saturation;
        this.hungerSurpass = hungerSurpass;
        this.duration = duration;
    }
    @Override
    public double getHunger(VoxelRecord voxelRecord, double cubicMeters) {
        return hunger * cubicMeters;
    }
    @Override
    public double getSaturation(VoxelRecord voxelRecord, double cubicMeters) {
        return saturation * cubicMeters;
    }
    @Override
    public double getHungerSurpass(VoxelRecord voxelRecord, double cubicMeters) {
        return hungerSurpass * cubicMeters;
    }
    @Override
    public double getDuration(double cubicMeters) {
        return duration * cubicMeters;
    }
    @Override
    public Map<StatusEffect, Pair<StatusEffectInstance, Double>> getStatusEffects(double cubicMeters) {
        return Map.of();
    }
    @Override
    public void applyExtra(PlayerEntity player) {

    }
    public LinearFoodBehaviour mul(double factor) {
        return new LinearFoodBehaviour(hunger * factor, saturation * factor, hungerSurpass * factor, duration * factor);
    }
    @Override
    public String toString() {
        return "LinearFoodBehaviour[" +
               "hunger=" + hunger + ", " +
               "saturation=" + saturation + ", " +
               "foodTotal=" + hungerSurpass + ", " +
               "duration=" + duration + ']';
    }
}
