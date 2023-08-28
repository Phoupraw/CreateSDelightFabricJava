package phoupraw.mcmod.createsdelight.datagen.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.registry.CDIdentifiers;


public final class CDEnglishProvider extends FabricLanguageProvider {

    public CDEnglishProvider(FabricDataOutput dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateTranslations(TranslationBuilder b) {
        b.add("modmenu.descriptionTranslation." + CreateSDelight.MOD_ID,
          """
          Mainly add interaction between create and farmer's delight and unique food processing.
          """);
        b.add(CDIdentifiers.ITEM_GROUP.toTranslationKey("itemGroup"), "Create'S Delight");
        //b.add(VirtualFluids.getTranslationKey(CDFluids.SUNFLOWER_OIL), "Sunflower Seed Oil");
        //b.add(CDItems.BUCKETED_SUNFLOWER_OIL, "Bucketed Sunflower Seed Oil");
        //b.add(CDItems.BOTTLED_SUNFLOWER_OIL, "Bottled Sunflower Seed Oil");
        ////b.add(Internationals.keyOfCategory(CDRecipeTypes.PAN_FRYING.getId()), "Pan Frying");
        //b.add(CDStatusEffects.SATIATION, "Satiation");
        ////b.add(Internationals.keyOfCategory(CDRecipeTypes.GRILLING.getId()), "Grilling");
        //b.add("empty", "Empty");
        ////b.add(Internationals.keyOfCategory(CDRecipeTypes.SPRINKLING.getId()), "Sprinkling Flavour");
        ////b.add(Internationals.keyOfCategory(CDRecipeTypes.STEAMING.getId()), "Steaming");
        //b.add("burn_time", "Fuel Time: %s");
        ////b.add(Internationals.keyOfCategory(CDRecipeTypes.VERTICAL_CUTTING.getId()), "Vertical Cutting");
        ////b.add(Internationals.keyOfCategory(CDRecipeTypes.PRESSURE_COOKING.getId()), "Pressure Cooking");
        ////b.add(VirtualFluids.getTranslationKey(CDFluids.VEGETABLE_BIG_STEW), "Vegetable Big Stew");
        ////b.add(Internationals.keyOfCategory(CDRecipeTypes.MINCING.getId()), "Mincing");
        //b.add("modmenu.nameTranslation." + CreateSDelight.MOD_ID, "Create's Delight");
        ////b.add(VirtualFluids.getTranslationKey(CDFluids.ROSE_MILK_TEA), "Rose Milk Tea");
        //b.add(VirtualFluids.getTranslationKey(CDFluids.BEETROOT_SOUP), "Beetroot Soup");
        //b.add(VirtualFluids.getTranslationKey(CDFluids.TOMATO_SAUCE), "Tomato Sauce");
        ////b.add(VirtualFluids.getTranslationKey(CDFluids.POPPY_RUSSIAN_SOUP), "Poppy Russian Soup");
        //b.add(VirtualFluids.getTranslationKey(CDFluids.EGG_LIQUID), "Egg Liquid");
        //b.add(CDItems.EGG_SHELL, "Egg Shell");
        //b.add(CDItems.EGG_DOUGH, "Egg Dough");
        ////b.add(VirtualFluids.getTranslationKey(CDFluids.WHEAT_BLACK_TEA), "Wheat Seeds Black Tea");
        ////b.add(VirtualFluids.getTranslationKey(CDFluids.ICED_MELON_JUICE), "Iced Melon Juice");
        //b.add(VirtualFluids.getTranslationKey(CDFluids.MELON_JUICE), "Melon Juice");
        ////b.add(VirtualFluids.getTranslationKey(CDFluids.THICK_HOT_COCOA), "Thick Hot Cocoa");
        //b.add(CDItems.KELP_ASH, "Kelp Ash");
        //b.add(CDBlocks.JELLY_BEANS, "Jelly Beans");
        //b.add(CDBlocks.JELLY_BEANS_CAKE, "Jelly Beans Cake");
        //b.add(VirtualFluids.getTranslationKey(CDFluids.PASTE), "Paste");
        //b.add(CDItems.CAKE_BASE, "Cake Base");
        //b.add(CDItems.CAKE_BASE_SLICE, "Cake Base Slice");
        //b.add(CDBlocks.SWEET_BERRIES_CAKE, "Sweet Berries Cake");
        //b.add(CDBlocks.BASQUE_CAKE, "Gateau Basque");
        //b.add(CDItems.RAW_BASQUE_CAKE, "Raw Gateau Basque");
        //b.add(CDBlocks.SWEET_BERRIES_CAKE_S, "Multi-layers Sweet Berries Cake");
        //b.add(CDBlocks.BROWNIE, "Brownie");
        //b.add(CDBlocks.APPLE_CREAM_CAKE, "Apple Craem Cake");
        //b.add(CDItems.SUNFLOWER_KERNELS, "Sunflower Kernels");
        //b.add(CDItems.BUCKETED_PUMPKIN_OIL, "Bucketed Pumpkin Seeds Oil");
        //b.add(VirtualFluids.getTranslationKey(CDFluids.PUMPKIN_OIL), "Pumpkin Seeds Oil");
        //b.add(VirtualFluids.getTranslationKey(CDFluids.APPLE_PASTE), "Apple Paste");
        //b.add(CDBlocks.APPLE_CAKE, "Apple Cake");
        //b.add(CDBlocks.CARROT_CREAM_CAKE, "Carrot Cream Cake");
        ////b.add(VirtualFluids.getTranslationKey(CDFluids.MASHED_POTATO), "Mashed Potato");
        //b.add(CDItems.JELLY_BEANS_CAKE_0, "Incomplete Jelly Beans Cake");
        //b.add(CDItems.SWEET_BERRIES_CAKE_0, "Incomplete Sweet Berries Cake");
        //b.add(CDItems.SWEET_BERRIES_CAKE_S_0, "Incomplete Multi-layers Sweet Berries Cake");
        //b.add(CDItems.RAW_BASQUE_CAKE_0, "Incomplete Raw Gateau Basque");
        //b.add(CDItems.BROWNIE_0, "Incomplete Brownie");
        //b.add(CDItems.APPLE_CREAM_CAKE_0, "Incomplete Apple Craem Cake");
        //b.add(CDItems.CARROT_CREAM_CAKE_0, "Incomplete Carrot Craem Cake");
        //b.add(CDItems.IRON_BOWL, "Iron Bowl");
        //b.add(IronBowlItem.getSuffixKey(), "%s (%s)");
        ////b.add(Internationals.keyOfCategory(CDRecipeTypes.BAKING.getId()), "Baking");
        //b.add(CDBlocks.SMALL_CHOCOLATE_CREAM_CAKE, "Small Chocolate Cream Cake");
        //b.add(CDBlocks.MEDIUM_CHOCOLATE_CREAM_CAKE, "Medium Chocolate Cream Cake");
        //b.add(CDBlocks.BIG_CHOCOLATE_CREAM_CAKE, "Big Chocolate Cream Cake");
        //b.add(VirtualFluids.getTranslationKey(CDFluids.CHOCOLATE_PASTE), "Chocolate Paste");
        //b.add(CDItems.CHOCOLATE_CAKE_BASE, "Chocolate Cake Base");
        //b.add(CDBlocks.CHOCOLATE_ANTHEMY_CAKE, "Chocolate Anthemy Cake");
        //b.add(Internationals.SECONDS, "%ssec");
        //b.add(Internationals.MST, "%smin %ssec %s ticks");
        //b.add(CDItems.CAKE_BLUEPRINT, "Cake Blueprint");
        //b.add(CDBlocks.PRINTED_CAKE, "Blueprint Cake");
        //b.add(CDBlocks.MILK, "Solid Milk Block");
        //b.add(CDBlocks.CHOCOLATE, "Chocolate Block");
        //b.add(CDBlocks.CAKE_OVEN, "Cake Oven");
    }
}
