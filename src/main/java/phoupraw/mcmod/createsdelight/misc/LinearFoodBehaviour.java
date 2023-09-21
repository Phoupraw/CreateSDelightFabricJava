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
     只考虑饥饿值、饱食度、使用时间（是否是零食）
     * @param minuend 被减数
     * @param subtrahend 减数
     * @param cubicMeters 体积（立方米）
     * @return (被减数 - 减数)÷体积
     */
    public static LinearFoodBehaviour subtract(FoodComponent minuend, FoodComponent subtrahend, double cubicMeters) {
        double hunger = minuend.getHunger() - subtrahend.getHunger();
        double saturation = (minuend.getHunger() * minuend.getSaturationModifier() - subtrahend.getHunger() * subtrahend.getSaturationModifier()) * 2;
        double duration = 32 + ((minuend.isSnack() ? 0 : 1) + (subtrahend.isSnack() ? 0 : 1)) * 16;
        return new LinearFoodBehaviour(hunger / cubicMeters, saturation / cubicMeters, 0, duration / cubicMeters);
    }
    /**
     只考虑饥饿值、饱食度、使用时间（是否是零食）
     * @param foodComponent 原本食物属性
     * @param cubicMeters 体积（立方米）
     * @return 原本食物属性÷体积
     */
    public static LinearFoodBehaviour of(FoodComponent foodComponent, double cubicMeters) {
        double hunger = foodComponent.getHunger();
        double saturation = foodComponent.getHunger() * foodComponent.getSaturationModifier() * 2;
        double duration = foodComponent.isSnack() ? 16 : 32;
        return new LinearFoodBehaviour(hunger / cubicMeters, saturation / cubicMeters, 0, duration / cubicMeters);
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
    public double getHunger(double cubicMeters) {
        return hunger * cubicMeters;
    }
    @Override
    public double getSaturation(double cubicMeters) {
        return saturation * cubicMeters;
    }
    @Override
    public double getHungerSurpass(double cubicMeters) {
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
    //@Override
    //public boolean equals(Object obj) {
    //    if (obj == this) return true;
    //    if (obj == null || obj.getClass() != this.getClass()) return false;
    //    var that = (LinearFoodBehaviour) obj;
    //    return Double.doubleToLongBits(this.hunger) == Double.doubleToLongBits(that.hunger) &&
    //           Double.doubleToLongBits(this.saturation) == Double.doubleToLongBits(that.saturation) &&
    //           Double.doubleToLongBits(this.foodTotal) == Double.doubleToLongBits(that.foodTotal) &&
    //           Double.doubleToLongBits(this.duration) == Double.doubleToLongBits(that.duration);
    //}
    //@Override
    //public int hashCode() {
    //    return Objects.hash(hunger, saturation, foodTotal, duration);
    //}
    @Override
    public String toString() {
        return "LinearFoodBehaviour[" +
               "hunger=" + hunger + ", " +
               "saturation=" + saturation + ", " +
               "foodTotal=" + hungerSurpass + ", " +
               "duration=" + duration + ']';
    }
}
