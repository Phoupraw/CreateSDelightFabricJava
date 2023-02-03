package phoupraw.mcmod.createsdelight.registry;

import com.nhoryzon.mc.farmersdelight.item.ConsumableItem;
import com.simibubi.create.content.contraptions.components.AssemblyOperatorBlockItem;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.registry.Registry;
public final class MyItems {
    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(MyIdentifiers.ITEM_GROUP, MyItems::stupidJavaCompiler);

    public static final BlockItem PAN = new BlockItem(MyBlocks.PAN, new FabricItemSettings().group(ITEM_GROUP));
    public static final BlockItem GRILL = new BlockItem(MyBlocks.GRILL, new FabricItemSettings().group(ITEM_GROUP));
    public static final BlockItem SPRINKLER = new AssemblyOperatorBlockItem(MyBlocks.SPRINKLER, new FabricItemSettings().group(ITEM_GROUP));

    public static final Item PAN_FRIED_BEEF_PATTY = satiationMeat(4, 0.8f, 2);
    public static final Item THICK_PORK_SLICE = new Item(new FabricItemSettings().group(ITEM_GROUP).food(new FoodComponent.Builder().meat().hunger(2).saturationModifier(0.3f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, 0), 0.05f).build()));
    public static final Item PAN_FRIED_PORK_SLICE = satiationMeat(4, 0.6f, 0);
    public static final Item THIN_PORK_SLICE = new Item(new FabricItemSettings().group(ITEM_GROUP).food(new FoodComponent.Builder().meat().hunger(1).saturationModifier(0.3f).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 40, 0), 0.05f).build()));
    public static final Item GRILLED_PORK_SLICE = satiationMeat(2, 0.6f, 0);
    public static final Item SUGAR_PORK = satiationMeat(4, 0.8f, 2);
//    public static final Item LEAVES_RICE = new ConsumableItem(new FabricItemSettings().group(ITEM_GROUP).maxCount(16).food());
    static {
        Registry.register(Registry.ITEM, MyIdentifiers.PAN, PAN);
        Registry.register(Registry.ITEM, MyIdentifiers.GRILL, GRILL);
        Registry.register(Registry.ITEM, MyIdentifiers.SPRINKLER, SPRINKLER);

        Registry.register(Registry.ITEM, MyIdentifiers.PAN_FRIED_BEEF_PATTY, PAN_FRIED_BEEF_PATTY);
        Registry.register(Registry.ITEM, MyIdentifiers.THICK_PORK_SLICE, THICK_PORK_SLICE);
        Registry.register(Registry.ITEM, MyIdentifiers.PAN_FRIED_PORK_SLICE, PAN_FRIED_PORK_SLICE);
        Registry.register(Registry.ITEM, MyIdentifiers.THIN_PORK_SLICE, THIN_PORK_SLICE);
        Registry.register(Registry.ITEM, MyIdentifiers.GRILLED_PORK_SLICE, GRILLED_PORK_SLICE);
        Registry.register(Registry.ITEM, MyIdentifiers.SUGAR_PORK, SUGAR_PORK);
    }
    public static Item satiationMeat(int hunger, float saturationModifier, int amplifier) {
        return food(new FoodComponent.Builder().meat().statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, amplifier), 1).alwaysEdible().hunger(hunger).saturationModifier(saturationModifier).build());
    }

    public static Item food(FoodComponent foodComponent) {
        return new Item(new FabricItemSettings().group(ITEM_GROUP).food(foodComponent));
    }

    private static ItemStack stupidJavaCompiler() {
        return PAN.getDefaultStack();
    }

    private MyItems() {}
}
