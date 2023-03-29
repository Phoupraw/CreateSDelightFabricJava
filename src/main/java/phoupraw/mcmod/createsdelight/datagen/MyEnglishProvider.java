package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import phoupraw.mcmod.common.api.Internationals;
import phoupraw.mcmod.common.api.VirtualFluids;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.registry.*;
@Environment(EnvType.CLIENT)
public final class MyEnglishProvider extends FabricLanguageProvider {
    public MyEnglishProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        builder.add("modmenu.descriptionTranslation." + CreateSDelight.MOD_ID, """
          Mainly add interaction between create and farmer's delight and unique food processing.
          """);
        builder.add(Internationals.keyOfItemGroup(MyIdentifiers.ITEM_GROUP), "Create'S Delight");
        builder.add(MyBlocks.PAN, "Pan");
        builder.add(VirtualFluids.getTranslationKey(MyFluids.SUNFLOWER_OIL), "Sunflower Seed Oil");
        builder.add(MyItems.BUCKETED_SUNFLOWER_OIL, "Bucketed Sunflower Seed Oil");
        builder.add(MyItems.BOTTLED_SUNFLOWER_OIL, "Bottled Sunflower Seed Oil");
        builder.add(MyItems.PAN_FRIED_BEEF_PATTY, "Pan Fried Beef Patty");
        builder.add(Internationals.keyOfCategory(MyRecipeTypes.PAN_FRYING.getId()), "Pan Frying");
        builder.add(MyStatusEffects.SATIATION, "Satiation");
        builder.add(MyBlocks.GRILL, "Grill");
        builder.add(Internationals.keyOfCategory(MyRecipeTypes.GRILLING.getId()), "Grilling");
        builder.add(MyItems.THICK_PORK_SLICE, "Thick Pork Slice");
        builder.add(MyItems.PAN_FRIED_PORK_SLICE, "Pan Fired Pork Slice");
        builder.add(MyItems.THIN_PORK_SLICE, "Thin Pork Slice");
        builder.add(MyItems.GRILLED_PORK_SLICE, "Grilled Pork Slice");
        builder.add(MyItems.SUGAR_PORK, "Frosted Pork");
        builder.add(MyBlocks.SPRINKLER, "Flavour Sprinkler");
        builder.add("empty", "Empty");
        builder.add(Internationals.keyOfCategory(MyRecipeTypes.SPRINKLING.getId()), "Sprinkling Flavour");
        builder.add(MyItems.LEAVES_RICE, "Leaves Rice");
        builder.add(MyItems.VANILLA, "Vanilla");
        builder.add(MyItems.VANILLA_SWEET_ROLL, "Vanilla Sweet Roll");
        builder.add(MyBlocks.BAMBOO_STEAMER, "Bamboo Steamer");
        builder.add(Internationals.keyOfCategory(MyRecipeTypes.STEAMING.getId()), "Steaming");
        builder.add(MyItems.STEAMED_BUNS, "Steamed Buns");
        builder.add(MyBlocks.SMART_DRAIN, "Smart Item Drain");
        builder.add("burn_time", "Fuel Time: %s");
        builder.add(MyBlocks.COPPER_TUNNEL, "Copper Tunnel");
        builder.add(MyBlocks.MULTIFUNC_BASIN, "Multifunctional Basin");
        builder.add(MyBlocks.VERTICAL_CUTTER, "Vertical Cutter");
        builder.add(Internationals.keyOfCategory(MyRecipeTypes.VERTICAL_CUTTING.getId()), "Vertical Cutting");
        builder.add(MyBlocks.PRESSURE_COOKER, "Pressure Cooker Controller");
        builder.add(Internationals.keyOfCategory(MyRecipeTypes.PRESSURE_COOKING.getId()), "Pressure Cooking");
        builder.add(MyItems.COOKED_RICE, "Cooked Rice");
        builder.add(MyItems.VEGETABLE_BIG_STEW, "Vegetable Big Stew");
        builder.add(VirtualFluids.getTranslationKey(MyFluids.VEGETABLE_BIG_STEW), "Vegetable Big Stew");
        builder.add(MyBlocks.MINCER, "Mincer");
        builder.add(Internationals.keyOfCategory(MyRecipeTypes.MINCING.getId()), "Mincing");
        builder.add("modmenu.nameTranslation." + CreateSDelight.MOD_ID, "Create's Delight");
        builder.add(VirtualFluids.getTranslationKey(MyFluids.ROSE_MILK_TEA), "Rose Milk Tea");
        builder.add(MyItems.ROSE_MILK_TEA, "Rose Milk Tea");
        builder.add(MyItems.CORAL_COLORFULS, "Coral Colorfuls");
        builder.add(VirtualFluids.getTranslationKey(MyFluids.BEETROOT_SOUP), "Beetroot Soup");
        builder.add(VirtualFluids.getTranslationKey(MyFluids.TOMATO_SAUCE), "Tomato Sauce");
        builder.add(MyItems.POPPY_RUSSIAN_SOUP, "Poppy Russian Soup");
        builder.add(VirtualFluids.getTranslationKey(MyFluids.POPPY_RUSSIAN_SOUP), "Poppy Russian Soup");
        builder.add(VirtualFluids.getTranslationKey(MyFluids.EGG_LIQUID), "Egg Liquid");
        builder.add(MyItems.EGG_SHELL, "Egg Shell");
        builder.add(MyItems.EGG_DOUGH, "Egg Dough");
        builder.add(MyItems.CRUSHED_ICE, "Crushed Ice");
        builder.add(MyItems.WHEAT_BLACK_TEA, "Wheat Seeds Black Tea");
        builder.add(VirtualFluids.getTranslationKey(MyFluids.WHEAT_BLACK_TEA), "Wheat Seeds Black Tea");
        builder.add(VirtualFluids.getTranslationKey(MyFluids.ICED_MELON_JUICE), "Iced Melon Juice");
        builder.add(MyItems.ICED_MELON_JUICE, "Iced Melon Juice");
        builder.add(VirtualFluids.getTranslationKey(MyFluids.MELON_JUICE), "Melon Juice");
        builder.add(MyItems.THICK_HOT_COCOA, "Thick Hot Cocoa");
        builder.add(VirtualFluids.getTranslationKey(MyFluids.THICK_HOT_COCOA), "Thick Hot Cocoa");
        builder.add(MyBlocks.SKEWER, "Skewer");
        builder.add(MyBlocks.BASIN, "Basin");
        builder.add(MyBlocks.SKEWER_PLATE, "Skewer Plate");
        builder.add(MyItems.SALT, "Salt");
        builder.add(MyItems.KELP_ASH, "Kelp Ash");
        builder.add(MyBlocks.JELLY_BEANS, "Jelly Beans");
        builder.add(MyBlocks.JELLY_BEANS_CAKE, "Jelly Beans Cake");
        builder.add(MyItems.YEAST, "Yeast");
        builder.add(VirtualFluids.getTranslationKey(MyFluids.PASTE), "Paste");
        builder.add(MyItems.CAKE_BASE, "Cake Base");
        builder.add(MyItems.CAKE_BASE_SLICE, "Cake Base Slice");
        builder.add(MyBlocks.SWEET_BERRIES_CAKE, "Sweet Berries Cake");
        builder.add(MyBlocks.BASQUE_CAKE, "Gateau Basque");
        builder.add(MyItems.RAW_BASQUE_CAKE, "Raw Gateau Basque");
        builder.add(MyBlocks.SWEET_BERRIES_CAKE_S, "Multi-layers Sweet Berries Cake");
        builder.add(MyBlocks.BROWNIE, "Brownie");
        builder.add(MyBlocks.APPLE_CREAM_CAKE, "Apple Craem Cake");
        builder.add(MyItems.SUNFLOWER_KERNELS, "Sunflower Kernels");
        builder.add(MyItems.BUCKETED_PUMPKIN_OIL, "Bucketed Pumpkin Seeds Oil");
        builder.add(VirtualFluids.getTranslationKey(MyFluids.PUMPKIN_OIL), "Pumpkin Seeds Oil");
        builder.add(MyBlocks.OVEN, "Sealed Basin");
        builder.add(VirtualFluids.getTranslationKey(MyFluids.APPLE_PASTE), "Apple Paste");
        builder.add(MyBlocks.APPLE_CAKE, "Apple Cake");
        builder.add(MyBlocks.CARROT_CREAM_CAKE, "Carrot Cream Cake");
        builder.add(MyItems.MASHED_POTATO, "Bowled Mashed Potato");
        builder.add(VirtualFluids.getTranslationKey(MyFluids.MASHED_POTATO), "Mashed Potato");
        builder.add(MyItems.INCOMPLETE_JELLY_BEANS_CAKE, "Incomplete Jelly Beans Cake");
        builder.add(MyItems.INCOMPLETE_SWEET_BERRIES_CAKE, "Incomplete Sweet Berries Cake");
        builder.add(MyItems.INCOMPLETE_SWEET_BERRIES_CAKE_S, "Incomplete Multi-layers Sweet Berries Cake");
        builder.add(MyItems.INCOMPLETE_RAW_BASQUE_CAKE, "Incomplete Raw Gateau Basque");
        builder.add(MyItems.INCOMPLETE_BROWNIE, "Incomplete Brownie");
        builder.add(MyItems.INCOMPLETE_APPLE_CREAM_CAKE, "Incomplete Apple Craem Cake");
        builder.add(MyItems.INCOMPLETE_CARROT_CREAM_CAKE, "Incomplete Carrot Craem Cake");
    }
}
