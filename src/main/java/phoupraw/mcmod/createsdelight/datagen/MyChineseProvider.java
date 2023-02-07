package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.api.VirtualFluid;
import phoupraw.mcmod.createsdelight.registry.*;
public class MyChineseProvider extends FabricLanguageProvider {
    public static String keyOfItemGroup(Identifier id) {
        return "itemGroup." + id.getNamespace() + "." + id.getPath();
    }

    public static String keyOfCategory(Identifier recipeTypeId) {
        return "category." + recipeTypeId.getNamespace() + "." + recipeTypeId.getPath();
    }

    public MyChineseProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, "zh_cn");
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        builder.add("modmenu.descriptionTranslation." + CreateSDelight.MOD_ID, """
          机械动力乐事
          主要为机械动力和农夫乐事添加联动，以及独特的沉浸式食材加工。
          """);
        builder.add(keyOfItemGroup(MyIdentifiers.ITEM_GROUP), "机械动力乐事");
        builder.add(MyBlocks.PAN, "平底锅");
        builder.add(VirtualFluid.getTranslationKey(MyFluids.SUNFLOWER_OIL), "葵花籽油");
        builder.add(MyFluids.SUNFLOWER_OIL.getBucketItem(), "桶装葵花籽油");
        builder.add(MyFluids.SUNFLOWER_OIL.getBottle(), "瓶装葵花籽油");
        builder.add(MyItems.PAN_FRIED_BEEF_PATTY, "香煎牛肉饼");
        builder.add(keyOfCategory(MyRecipeTypes.PAN_FRYING.getId()), "煎");
        builder.add(MyStatusEffects.SATIATION, "饱食");
        builder.add(MyBlocks.GRILL, "烤架");
        builder.add(keyOfCategory(MyRecipeTypes.GRILLING.getId()), "烤");
        builder.add(MyItems.THICK_PORK_SLICE, "厚猪肉片");
        builder.add(MyItems.PAN_FRIED_PORK_SLICE, "煎猪肉片");
        builder.add(MyItems.THIN_PORK_SLICE, "薄猪肉片");
        builder.add(MyItems.GRILLED_PORK_SLICE, "烤猪肉片");
        builder.add(MyItems.SUGAR_PORK, "糖霜猪肉");
        builder.add(MyBlocks.SPRINKLER, "调料喷撒机");
        builder.add("empty", "空");
        builder.add(keyOfCategory(MyRecipeTypes.SPRINKLING.getId()), "撒料");
        builder.add(MyItems.LEAVES_RICE, "树叶米饭");
        builder.add(MyItems.VANILLA, "香草");
        builder.add(MyItems.VANILLA_SWEET_ROLL, "香草甜甜卷");
        builder.add(MyBlocks.BAMBOO_STEAMER, "竹蒸笼");
        builder.add(keyOfCategory(MyRecipeTypes.STEAMING.getId()), "蒸");
        builder.add(MyItems.STEAMED_BUNS, "馒头");
        builder.add(MyBlocks.SMART_DRAIN, "智能分液池");
        builder.add("burn_time", "燃料时间：%s");
        builder.add(MyBlocks.COPPER_TUNNEL, "铜隧道");
    }
}
