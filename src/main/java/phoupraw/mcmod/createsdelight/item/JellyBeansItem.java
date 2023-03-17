package phoupraw.mcmod.createsdelight.item;

import net.minecraft.block.Block;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.BlockItem;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
import phoupraw.mcmod.createsdelight.registry.MyItems;
import phoupraw.mcmod.createsdelight.registry.MyStatusEffects;
public class JellyBeansItem extends BlockItem {
    public JellyBeansItem() {
        this(MyBlocks.JELLY_BEANS, MyItems.newSettings()
          .recipeRemainder(Items.GLASS_BOTTLE)
          .food(new FoodComponent.Builder()
            .hunger(1)
            .saturationModifier(0.5f)
            .snack()
            .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 0), 1f)
            .build()
          ));
    }

    public JellyBeansItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        return StatusEffectsItem.finishUsing(this, stack, world, user);
    }
}
