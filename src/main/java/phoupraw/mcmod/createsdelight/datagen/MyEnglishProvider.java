package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import phoupraw.mcmod.createsdelight.api.VirtualFluid;
import phoupraw.mcmod.createsdelight.registry.*;

import static phoupraw.mcmod.createsdelight.datagen.MyChineseProvider.keyOfCategory;
import static phoupraw.mcmod.createsdelight.datagen.MyChineseProvider.keyOfItemGroup;
public class MyEnglishProvider extends FabricLanguageProvider {
    public MyEnglishProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        builder.add("modmenu.descriptionTranslation." + MyIdentifiers.MOD_ID, """
          Create'S Delight
          Mainly add interaction between create and farmer's delight and unique food processing.
          """);
        builder.add(keyOfItemGroup(MyIdentifiers.ITEM_GROUP), "Create'S Delight");
        builder.add(MyBlocks.PAN, "Pan");
        builder.add(VirtualFluid.getTranslationKey(MyFluids.SUNFLOWER_OIL), "Sunflower Seed Oil");
        builder.add(MyFluids.SUNFLOWER_OIL.getBucketItem(), "Bucketed Sunflower Seed Oil");
        builder.add(MyFluids.SUNFLOWER_OIL.getBottle(), "Bottled Sunflower Seed Oil");
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
        builder.add(MyItems.VANILLA_SWEET_ROLL,"Vanilla Sweet Roll");
        builder.add(MyBlocks.BAMBOO_STEAMER,"Bamboo Steamer");
    }
}
