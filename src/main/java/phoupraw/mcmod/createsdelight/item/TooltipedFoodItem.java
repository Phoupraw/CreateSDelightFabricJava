package phoupraw.mcmod.createsdelight.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
public class TooltipedFoodItem extends Item {
    public TooltipedFoodItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        FoodComponent component = getFoodComponent();
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
