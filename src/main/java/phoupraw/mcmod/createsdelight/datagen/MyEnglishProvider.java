package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import phoupraw.mcmod.common.api.Internationals;
import phoupraw.mcmod.common.api.VirtualFluids;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.item.IronBowlItem;
import phoupraw.mcmod.createsdelight.registry.*;
@Environment(EnvType.CLIENT)
public final class MyEnglishProvider extends FabricLanguageProvider {
    public MyEnglishProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateTranslations(TranslationBuilder b) {
        b.add("modmenu.descriptionTranslation." + CreateSDelight.MOD_ID, """
          Mainly add interaction between create and farmer's delight and unique food processing.
          """);
        b.add(Internationals.keyOfItemGroup(MyIdentifiers.ITEM_GROUP), "Create'S Delight");
        b.add(MyBlocks.PAN, "Pan");
        b.add(VirtualFluids.getTranslationKey(MyFluids.SUNFLOWER_OIL), "Sunflower Seed Oil");
        b.add(MyItems.BUCKETED_SUNFLOWER_OIL, "Bucketed Sunflower Seed Oil");
        b.add(MyItems.BOTTLED_SUNFLOWER_OIL, "Bottled Sunflower Seed Oil");
        b.add(MyItems.PAN_FRIED_BEEF_PATTY, "Pan Fried Beef Patty");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.PAN_FRYING.getId()), "Pan Frying");
        b.add(MyStatusEffects.SATIATION, "Satiation");
        b.add(MyBlocks.GRILL, "Grill");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.GRILLING.getId()), "Grilling");
        b.add(MyItems.THICK_PORK_SLICE, "Thick Pork Slice");
        b.add(MyItems.PAN_FRIED_PORK_SLICE, "Pan Fired Pork Slice");
        b.add(MyItems.THIN_PORK_SLICE, "Thin Pork Slice");
        b.add(MyItems.GRILLED_PORK_SLICE, "Grilled Pork Slice");
        b.add(MyItems.SUGAR_PORK, "Frosted Pork");
        b.add(MyBlocks.SPRINKLER, "Flavour Sprinkler");
        b.add("empty", "Empty");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.SPRINKLING.getId()), "Sprinkling Flavour");
        b.add(MyItems.LEAVES_RICE, "Leaves Rice");
        b.add(MyItems.VANILLA, "Vanilla");
        b.add(MyItems.VANILLA_SWEET_ROLL, "Vanilla Sweet Roll");
        b.add(MyBlocks.BAMBOO_STEAMER, "Bamboo Steamer");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.STEAMING.getId()), "Steaming");
        b.add(MyItems.STEAMED_BUNS, "Steamed Buns");
        b.add(MyBlocks.SMART_DRAIN, "Smart Item Drain");
        b.add("burn_time", "Fuel Time: %s");
        b.add(MyBlocks.COPPER_TUNNEL, "Copper Tunnel");
        b.add(MyBlocks.MULTIFUNC_BASIN, "Multifunctional Basin");
        b.add(MyBlocks.VERTICAL_CUTTER, "Vertical Cutter");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.VERTICAL_CUTTING.getId()), "Vertical Cutting");
        b.add(MyBlocks.PRESSURE_COOKER, "Pressure Cooker Controller");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.PRESSURE_COOKING.getId()), "Pressure Cooking");
        b.add(MyItems.COOKED_RICE, "Cooked Rice");
        b.add(MyItems.VEGETABLE_BIG_STEW, "Vegetable Big Stew");
        b.add(VirtualFluids.getTranslationKey(MyFluids.VEGETABLE_BIG_STEW), "Vegetable Big Stew");
        b.add(MyBlocks.MINCER, "Mincer");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.MINCING.getId()), "Mincing");
        b.add("modmenu.nameTranslation." + CreateSDelight.MOD_ID, "Create's Delight");
        b.add(VirtualFluids.getTranslationKey(MyFluids.ROSE_MILK_TEA), "Rose Milk Tea");
        b.add(MyItems.ROSE_MILK_TEA, "Rose Milk Tea");
        b.add(MyItems.CORAL_COLORFULS, "Coral Colorfuls");
        b.add(VirtualFluids.getTranslationKey(MyFluids.BEETROOT_SOUP), "Beetroot Soup");
        b.add(VirtualFluids.getTranslationKey(MyFluids.TOMATO_SAUCE), "Tomato Sauce");
        b.add(MyItems.POPPY_RUSSIAN_SOUP, "Poppy Russian Soup");
        b.add(VirtualFluids.getTranslationKey(MyFluids.POPPY_RUSSIAN_SOUP), "Poppy Russian Soup");
        b.add(VirtualFluids.getTranslationKey(MyFluids.EGG_LIQUID), "Egg Liquid");
        b.add(MyItems.EGG_SHELL, "Egg Shell");
        b.add(MyItems.EGG_DOUGH, "Egg Dough");
        b.add(MyItems.CRUSHED_ICE, "Crushed Ice");
        b.add(MyItems.WHEAT_BLACK_TEA, "Wheat Seeds Black Tea");
        b.add(VirtualFluids.getTranslationKey(MyFluids.WHEAT_BLACK_TEA), "Wheat Seeds Black Tea");
        b.add(VirtualFluids.getTranslationKey(MyFluids.ICED_MELON_JUICE), "Iced Melon Juice");
        b.add(MyItems.ICED_MELON_JUICE, "Iced Melon Juice");
        b.add(VirtualFluids.getTranslationKey(MyFluids.MELON_JUICE), "Melon Juice");
        b.add(MyItems.THICK_HOT_COCOA, "Thick Hot Cocoa");
        b.add(VirtualFluids.getTranslationKey(MyFluids.THICK_HOT_COCOA), "Thick Hot Cocoa");
        b.add(MyBlocks.SKEWER, "Skewer");
        b.add(MyBlocks.BASIN, "Basin");
        b.add(MyBlocks.SKEWER_PLATE, "Skewer Plate");
        b.add(MyItems.SALT, "Salt");
        b.add(MyItems.KELP_ASH, "Kelp Ash");
        b.add(MyBlocks.JELLY_BEANS, "Jelly Beans");
        b.add(MyBlocks.JELLY_BEANS_CAKE, "Jelly Beans Cake");
        b.add(MyItems.YEAST, "Yeast");
        b.add(VirtualFluids.getTranslationKey(MyFluids.PASTE), "Paste");
        b.add(MyItems.CAKE_BASE, "Cake Base");
        b.add(MyItems.CAKE_BASE_SLICE, "Cake Base Slice");
        b.add(MyBlocks.SWEET_BERRIES_CAKE, "Sweet Berries Cake");
        b.add(MyBlocks.BASQUE_CAKE, "Gateau Basque");
        b.add(MyItems.RAW_BASQUE_CAKE, "Raw Gateau Basque");
        b.add(MyBlocks.SWEET_BERRIES_CAKE_S, "Multi-layers Sweet Berries Cake");
        b.add(MyBlocks.BROWNIE, "Brownie");
        b.add(MyBlocks.APPLE_CREAM_CAKE, "Apple Craem Cake");
        b.add(MyItems.SUNFLOWER_KERNELS, "Sunflower Kernels");
        b.add(MyItems.BUCKETED_PUMPKIN_OIL, "Bucketed Pumpkin Seeds Oil");
        b.add(VirtualFluids.getTranslationKey(MyFluids.PUMPKIN_OIL), "Pumpkin Seeds Oil");
        b.add(MyBlocks.OVEN, "Oven");
        b.add(VirtualFluids.getTranslationKey(MyFluids.APPLE_PASTE), "Apple Paste");
        b.add(MyBlocks.APPLE_CAKE, "Apple Cake");
        b.add(MyBlocks.CARROT_CREAM_CAKE, "Carrot Cream Cake");
        b.add(MyItems.MASHED_POTATO, "Bowled Mashed Potato");
        b.add(VirtualFluids.getTranslationKey(MyFluids.MASHED_POTATO), "Mashed Potato");
        b.add(MyItems.JELLY_BEANS_CAKE_0, "Incomplete Jelly Beans Cake");
        b.add(MyItems.SWEET_BERRIES_CAKE_0, "Incomplete Sweet Berries Cake");
        b.add(MyItems.SWEET_BERRIES_CAKE_S_0, "Incomplete Multi-layers Sweet Berries Cake");
        b.add(MyItems.RAW_BASQUE_CAKE_0, "Incomplete Raw Gateau Basque");
        b.add(MyItems.BROWNIE_0, "Incomplete Brownie");
        b.add(MyItems.APPLE_CREAM_CAKE_0, "Incomplete Apple Craem Cake");
        b.add(MyItems.CARROT_CREAM_CAKE_0, "Incomplete Carrot Craem Cake");
        b.add(MyItems.IRON_BOWL, "Iron Bowl");
        b.add(IronBowlItem.getSuffixKey(), "%s (%s)");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.BAKING.getId()), "Baking");
        b.add(MyBlocks.SMALL_CHOCOLATE_CREAM_CAKE, "Small Chocolate Cream Cake");
        b.add(MyBlocks.MEDIUM_CHOCOLATE_CREAM_CAKE, "Medium Chocolate Cream Cake");
        b.add(MyBlocks.BIG_CHOCOLATE_CREAM_CAKE, "Big Chocolate Cream Cake");
        b.add(VirtualFluids.getTranslationKey(MyFluids.CHOCOLATE_PASTE), "Chocolate Paste");
        b.add(MyItems.CHOCOLATE_CAKE_BASE, "Chocolate Cake Base");
        b.add(MyBlocks.IRON_BAR_SKEWER, "Iron Bar Skewer");
        b.add(MyBlocks.CHOCOLATE_ANTHEMY_CAKE, "Chocolate Anthemy Cake");
        b.add(Internationals.SECONDS, "%ssec");
        b.add(Internationals.MST, "%smin %ssec %s ticks");
        b.add(MyItems.CAKE_BLUEPRINT, "Cake Blueprint");
        b.add(MyBlocks.PRINTED_CAKE, "Blueprint Cake");
    }
}
