package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import phoupraw.mcmod.common.fluid.VirtualFluid;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.registry.*;

import static phoupraw.mcmod.common.Internationals.keyOfCategory;
import static phoupraw.mcmod.common.Internationals.keyOfItemGroup;
public class MyEnglishProvider extends FabricLanguageProvider {
    public MyEnglishProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        builder.add("modmenu.descriptionTranslation." + CreateSDelight.MOD_ID, """
          Mainly add interaction between create and farmer's delight and unique food processing.
          """);
        builder.add(keyOfItemGroup(MyIdentifiers.ITEM_GROUP), "Create'S Delight");
        builder.add(MyBlocks.PAN, "Pan");
        builder.add(VirtualFluid.getTranslationKey(MyFluids.SUNFLOWER_OIL), "Sunflower Seed Oil");
        builder.add(MyItems.BUCKETED_SUNFLOWER_OIL, "Bucketed Sunflower Seed Oil");
        builder.add(MyItems.BOTTLED_SUNFLOWER_OIL, "Bottled Sunflower Seed Oil");
        builder.add(MyItems.PAN_FRIED_BEEF_PATTY, "Pan Fried Beef Patty");
        builder.add(keyOfCategory(MyRecipeTypes.PAN_FRYING.getId()), "Pan Frying");
        builder.add(MyStatusEffects.SATIATION, "Satiation");
        builder.add(MyBlocks.GRILL, "Grill");
        builder.add(keyOfCategory(MyRecipeTypes.GRILLING.getId()), "Grilling");
        builder.add(MyItems.THICK_PORK_SLICE, "Thick Pork Slice");
        builder.add(MyItems.PAN_FRIED_PORK_SLICE, "Pan Fired Pork Slice");
        builder.add(MyItems.THIN_PORK_SLICE, "Thin Pork Slice");
        builder.add(MyItems.GRILLED_PORK_SLICE, "Grilled Pork Slice");
        builder.add(MyItems.SUGAR_PORK, "Frosted Pork");
        builder.add(MyBlocks.SPRINKLER, "Flavour Sprinkler");
        builder.add("empty", "Empty");
        builder.add(keyOfCategory(MyRecipeTypes.SPRINKLING.getId()), "Sprinkling Flavour");
        builder.add(MyItems.LEAVES_RICE, "Leaves Rice");
        builder.add(MyItems.VANILLA, "Vanilla");
        builder.add(MyItems.VANILLA_SWEET_ROLL, "Vanilla Sweet Roll");
        builder.add(MyBlocks.BAMBOO_STEAMER, "Bamboo Steamer");
        builder.add(keyOfCategory(MyRecipeTypes.STEAMING.getId()), "Steaming");
        builder.add(MyItems.STEAMED_BUNS, "Steamed Buns");
        builder.add(MyBlocks.SMART_DRAIN, "Smart Item Drain");
        builder.add("burn_time", "Fuel Time: %s");
        builder.add(MyBlocks.COPPER_TUNNEL, "Copper Tunnel");
        builder.add(MyBlocks.MULTIFUNC_BASIN, "Multifunctional Basin");
        builder.add(MyBlocks.VERTICAL_CUTTER, "Vertical Cutter");
        builder.add(keyOfCategory(MyRecipeTypes.VERTICAL_CUTTING.getId()), "Vertical Cutting");
        builder.add(MyBlocks.PRESSURE_COOKER, "Pressure Cooker Controller");
        builder.add(keyOfCategory(MyRecipeTypes.PRESSURE_COOKING.getId()), "Pressure Cooking");
        builder.add(MyItems.COOKED_RICE, "Cooked Rice");
        builder.add(MyItems.VEGETABLE_BIG_STEW, "Vegetable Big Stew");
        builder.add(VirtualFluid.getTranslationKey(MyFluids.VEGETABLE_BIG_STEW), "Vegetable Big Stew");
        builder.add(MyBlocks.MINCER, "Mincer");
        builder.add(keyOfCategory(MyRecipeTypes.MINCING.getId()), "Mincing");
        builder.add("modmenu.nameTranslation." + CreateSDelight.MOD_ID, "Create's Delight");
        builder.add(VirtualFluid.getTranslationKey(MyFluids.ROSE_MILK_TEA), "Rose Milk Tea");
        builder.add(MyItems.ROSE_MILK_TEA, "Rose Milk Tea");
        builder.add(MyItems.CORAL_COLORFULS, "Coral Colorfuls");
        builder.add(VirtualFluid.getTranslationKey(MyFluids.BEETROOT_SOUP), "Beetroot Soup");
        builder.add(VirtualFluid.getTranslationKey(MyFluids.TOMATO_SAUCE), "Tomato Sauce");
        builder.add(MyItems.POPPY_RUSSIAN_SOUP, "Poppy Russian Soup");
        builder.add(VirtualFluid.getTranslationKey(MyFluids.POPPY_RUSSIAN_SOUP), "Poppy Russian Soup");
        builder.add(VirtualFluid.getTranslationKey(MyFluids.EGG_LIQUID), "Egg Liquid");
        builder.add(MyItems.EGG_SHELL, "Egg Shell");
        builder.add(MyItems.EGG_DOUGH, "Egg Dough");
        builder.add(MyItems.CRUSHED_ICE, "Crushed Ice");
        builder.add(MyItems.WHEAT_BLACK_TEA, "Wheat Seeds Black Tea");
        builder.add(VirtualFluid.getTranslationKey(MyFluids.WHEAT_BLACK_TEA), "Wheat Seeds Black Tea");
        builder.add(VirtualFluid.getTranslationKey(MyFluids.ICED_MELON_JUICE), "Iced Melon Juice");
        builder.add(MyItems.ICED_MELON_JUICE, "Iced Melon Juice");
        builder.add(VirtualFluid.getTranslationKey(MyFluids.MELON_JUICE), "Melon Juice");
        builder.add(MyItems.THICK_HOT_COCOA, "Thick Hot Cocoa");
        builder.add(VirtualFluid.getTranslationKey(MyFluids.THICK_HOT_COCOA), "Thick Hot Cocoa");
    }
}
