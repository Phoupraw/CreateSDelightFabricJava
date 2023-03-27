package phoupraw.mcmod.createsdelight.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
public class DrinkItem extends StatusEffectsItem {
    public DrinkItem(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }
}
