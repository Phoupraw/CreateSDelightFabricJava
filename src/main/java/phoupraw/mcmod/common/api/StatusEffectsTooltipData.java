package phoupraw.mcmod.common.api;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
/**
 @see StatusEffectsTooltipComponent
 @see Item#getTooltipData
 @since 1.0.0 */
@Environment(EnvType.CLIENT)
public record StatusEffectsTooltipData(List<Pair<StatusEffectInstance, Float>> statusEffects) implements net.minecraft.client.item.TooltipData {
    /**
     @param potion 药水
     @return 显示药水效果的工具提示
     @since 1.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull StatusEffectsTooltipData ofPotion(@NotNull ItemVariant potion) {
        List<StatusEffectInstance> potionEffects = PotionUtil.getPotionEffects(potion.toStack());
        List<Pair<StatusEffectInstance, Float>> chancedEffects = new ArrayList<>(potionEffects.size());
        for (StatusEffectInstance effect : potionEffects) chancedEffects.add(Pair.of(effect, 1f));
        return new StatusEffectsTooltipData(chancedEffects);
    }

    /**
     @param food 食物
     @return 显示食物给予的状态效果的工具提示
     @since 1.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull StatusEffectsTooltipData ofFood(@NotNull Item food) {
        return new StatusEffectsTooltipData(food.getFoodComponent() == null ? List.of() : food.getFoodComponent().getStatusEffects());
    }
}
