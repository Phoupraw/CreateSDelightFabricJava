package phoupraw.mcmod.createsdelight.inject;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.MyItemTags;

import java.util.List;
public class InjectItem {
    public static void addFoodEffectTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (!stack.isIn(MyItemTags.FOOD_TOOLTIP)) return;
        FoodComponent component = stack.getItem().getFoodComponent();
        if (component != null) {
            for (Pair<StatusEffectInstance, Float> pair : component.getStatusEffects()) {
                StatusEffectInstance instance = pair.getFirst();
                tooltip.add(Text.literal((pair.getSecond() * 100) + "% ")
                  .formatted(Formatting.GRAY)
                  .append(Text.translatable(instance.getTranslationKey()).formatted(instance.getEffectType().getCategory().getFormatting()))
                  .append(Text.literal(" " + (instance.getAmplifier() + 1) + "L " + instance.getDuration() + "t")));
            }
        }
    }
}
