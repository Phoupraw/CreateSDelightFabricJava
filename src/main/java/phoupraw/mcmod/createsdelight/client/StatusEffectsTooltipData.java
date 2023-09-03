package phoupraw.mcmod.createsdelight.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.potion.PotionUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @see StatusEffectsTooltipComponent
 * @see Item#getTooltipData
 * @since 1.0.0
 */
@Environment(EnvType.CLIENT)
public record StatusEffectsTooltipData(List<Pair<StatusEffectInstance, Float>> statusEffects) implements TooltipData {

    /**
     * @param potion 药水
     * @return 显示药水效果的工具提示
     * @since 1.0.0
     */
    public static @Nullable StatusEffectsTooltipData ofPotion(@NotNull ItemStack potion) {
        if (potion.getItem() instanceof LingeringPotionItem) return ofLingering(potion);
        List<StatusEffectInstance> potionEffects = PotionUtil.getPotionEffects(potion);
        if (potionEffects.isEmpty()) return null;
        List<Pair<StatusEffectInstance, Float>> chancedEffects = new ArrayList<>(potionEffects.size());
        for (StatusEffectInstance effect : potionEffects) {
            chancedEffects.add(Pair.of(effect, 1f));
        }
        return new StatusEffectsTooltipData(chancedEffects);
    }

    public static @Nullable StatusEffectsTooltipData ofLingering(@NotNull ItemStack potion) {
        List<StatusEffectInstance> potionEffects = PotionUtil.getPotionEffects(potion);
        if (potionEffects.isEmpty()) return null;
        List<Pair<StatusEffectInstance, Float>> chancedEffects = new ArrayList<>(potionEffects.size());
        for (StatusEffectInstance effect : potionEffects) {
            chancedEffects.add(Pair.of(new StatusEffectInstance(effect.getEffectType(), effect.getDuration() / 4, effect.getAmplifier(), effect.isAmbient(), effect.shouldShowParticles(), effect.shouldShowIcon()), 1f));
        }
        return new StatusEffectsTooltipData(chancedEffects);
    }

    /**
     * @param food 食物
     * @return 显示食物给予的状态效果的工具提示
     * @since 1.0.0
     */
    public static @Nullable StatusEffectsTooltipData ofFood(@NotNull ItemStack food) {
        FoodComponent foodComponent = food.getItem().getFoodComponent();
        if (foodComponent == null) return null;
        var statusEffects = foodComponent.getStatusEffects();
        if (statusEffects.isEmpty()) return null;
        List<Pair<StatusEffectInstance, Float>> pairs = new ArrayList<>(statusEffects.size());
        for (var pair : statusEffects) {
            pairs.add(Pair.of(pair.getFirst(), pair.getSecond()));
        }
        return new StatusEffectsTooltipData(pairs);
    }

    public static @Nullable StatusEffectsTooltipData ofStew(@NotNull ItemStack stew) {
        NbtCompound rootNbt = stew.getNbt();
        if (rootNbt == null || !rootNbt.contains(SuspiciousStewItem.EFFECTS_KEY, NbtElement.LIST_TYPE)) {return null;}
        NbtList nbtEffects = rootNbt.getList(SuspiciousStewItem.EFFECTS_KEY, NbtElement.COMPOUND_TYPE);
        int size = nbtEffects.size();
        List<Pair<StatusEffectInstance, Float>> statusEffects = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            NbtCompound nbtEffect = nbtEffects.getCompound(i);
            int duration;
            if (nbtEffect.contains(SuspiciousStewItem.EFFECT_DURATION_KEY, NbtElement.NUMBER_TYPE)) {
                duration = nbtEffect.getInt(SuspiciousStewItem.EFFECT_DURATION_KEY);
            } else {
                duration = 160;
            }
            StatusEffect statusEffect = StatusEffect.byRawId(nbtEffect.getInt(SuspiciousStewItem.EFFECT_ID_KEY));
            if (statusEffect != null) {
                statusEffects.add(Pair.of(new StatusEffectInstance(statusEffect, duration), 1f));
            }
        }
        return statusEffects.isEmpty() ? null : new StatusEffectsTooltipData(statusEffects);
    }

}
