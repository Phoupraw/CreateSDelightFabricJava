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
          # 机械动力乐事
          - 让传送带能直接输入炉灶、厨锅、煎锅、篮子、砧板。
          - 让动力臂能与炉灶、厨锅、煎锅、篮子、砧板交互。
          - 为巧克力派添加注液配方。
          - 让点燃的烈焰人燃烧室能作为农夫乐事的热源。
          本模组还处于极早期开发阶段，任何特性都不稳定，如果想要持久游戏，请注意备份存档。
          """);
        builder.add(keyOfItemGroup(MyIdentifiers.ITEM_GROUP), "机械动力乐事");
        builder.add(MyBlocks.PAN, "平底锅");
        builder.add(VirtualFluid.getTranslationKey(MyFluids.SUNFLOWER_OIL), "葵花籽油");
        builder.add(MyFluids.SUNFLOWER_OIL.getBucketItem(), "桶装葵花籽油");
        builder.add(MyFluids.SUNFLOWER_OIL.getBottle(), "瓶装葵花籽油");
        builder.add(MyItems.PAN_FRIED_BEEF_PATTY, "香煎牛肉饼");
        builder.add(keyOfCategory(MyRecipeTypes.PAN_FRYING.getId()), "煎");
        builder.add(MyStatusEffects.SATIATION, "饱食");
    }
}
