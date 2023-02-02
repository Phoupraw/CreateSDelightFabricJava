package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.item.TooltipedFoodItem;
public final class MyItems {
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(MyIdentifiers.ITEM_GROUP, MyItems::stupidJavaCompiler);
    public static final BlockItem PAN = new BlockItem(MyBlocks.PAN, new FabricItemSettings().group(ITEM_GROUP));
    public static final BlockItem GRILL = new BlockItem(MyBlocks.GRILL, new FabricItemSettings().group(ITEM_GROUP));
    public static final Item PAN_FRIED_BEEF_PATTY = new TooltipedFoodItem(new FabricItemSettings().group(ITEM_GROUP).food(new FoodComponent.Builder().statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 15), 1).meat().alwaysEdible().build()));
    public static final Item THICK_PORK_SLICE = new TooltipedFoodItem(new FabricItemSettings().group(ITEM_GROUP).food(new FoodComponent.Builder().meat().hunger(1).saturationModifier(0.3f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, 0), 0.05f).build()));
    public static final Item PAN_FRIED_PORK_SLICE = new TooltipedFoodItem(new FabricItemSettings().group(ITEM_GROUP).food(new FoodComponent.Builder().meat().statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 14), 1).alwaysEdible().build()));
    public static final Item THIN_PORK_SLICE = new TooltipedFoodItem(new FabricItemSettings().group(ITEM_GROUP).food(new FoodComponent.Builder().meat().hunger(1).saturationModifier(0f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, 0), 0.05f).build()));
    public static final Item GRILLED_PORK_SLICE = new TooltipedFoodItem(new FabricItemSettings().group(ITEM_GROUP).food(new FoodComponent.Builder().meat().statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 7), 1).alwaysEdible().build()));
    static {
        Registry.register(Registry.ITEM, MyIdentifiers.PAN, PAN);
        Registry.register(Registry.ITEM, MyIdentifiers.GRILL, GRILL);
        Registry.register(Registry.ITEM, MyIdentifiers.PAN_FRIED_BEEF_PATTY, PAN_FRIED_BEEF_PATTY);
        Registry.register(Registry.ITEM, MyIdentifiers.THICK_PORK_SLICE, THICK_PORK_SLICE);
        Registry.register(Registry.ITEM, MyIdentifiers.PAN_FRIED_PORK_SLICE, PAN_FRIED_PORK_SLICE);
        Registry.register(Registry.ITEM, MyIdentifiers.THIN_PORK_SLICE, THIN_PORK_SLICE);
        Registry.register(Registry.ITEM, MyIdentifiers.GRILLED_PORK_SLICE, GRILLED_PORK_SLICE);
    }

    private static ItemStack stupidJavaCompiler() {
        return PAN.getDefaultStack();
    }

    private MyItems() {}
}
