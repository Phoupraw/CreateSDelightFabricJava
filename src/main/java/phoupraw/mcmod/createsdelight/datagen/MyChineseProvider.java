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
public final class MyChineseProvider extends FabricLanguageProvider {
    public MyChineseProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, "zh_cn");
    }

    @Override
    public void generateTranslations(TranslationBuilder b) {
        b.add("modmenu.descriptionTranslation." + CreateSDelight.MOD_ID, """
          主要为机械动力和农夫乐事添加联动，以及独特的沉浸式食材加工。
          """);
        b.add(Internationals.keyOfItemGroup(CDIdentifiers.ITEM_GROUP), "机械动力乐事");
        b.add(CDBlocks.PAN, "平底锅");
        b.add(VirtualFluids.getTranslationKey(CDFluids.SUNFLOWER_OIL), "葵花籽油");
        b.add(CDItems.BUCKETED_SUNFLOWER_OIL, "桶装葵花籽油");
        b.add(CDItems.BOTTLED_SUNFLOWER_OIL, "瓶装葵花籽油");
        b.add(CDItems.PAN_FRIED_BEEF_PATTY, "香煎牛肉饼");
        b.add(Internationals.keyOfCategory(CDRecipeTypes.PAN_FRYING.getId()), "煎");
        b.add(CDStatusEffects.SATIATION, "饱食");
        b.add(CDBlocks.GRILL, "烤架");
        b.add(Internationals.keyOfCategory(CDRecipeTypes.GRILLING.getId()), "烤");
        b.add(CDItems.THICK_PORK_SLICE, "厚猪肉片");
        b.add(CDItems.PAN_FRIED_PORK_SLICE, "煎猪肉片");
        b.add(CDItems.THIN_PORK_SLICE, "薄猪肉片");
        b.add(CDItems.GRILLED_PORK_SLICE, "烤猪肉片");
        b.add(CDItems.SUGAR_PORK, "糖霜猪肉");
        b.add(CDBlocks.SPRINKLER, "调料喷撒机");
        b.add("empty", "空");
        b.add(Internationals.keyOfCategory(CDRecipeTypes.SPRINKLING.getId()), "撒料");
        b.add(CDItems.LEAVES_RICE, "树叶米饭");
        b.add(CDItems.VANILLA, "香草");
        b.add(CDItems.VANILLA_SWEET_ROLL, "香草甜甜卷");
        b.add(CDBlocks.BAMBOO_STEAMER, "竹蒸笼");
        b.add(Internationals.keyOfCategory(CDRecipeTypes.STEAMING.getId()), "蒸");
        b.add(CDItems.STEAMED_BUNS, "馒头");
        b.add(CDBlocks.SMART_DRAIN, "智能分液池");
        b.add("burn_time", "燃料时间：%s");
        b.add(CDBlocks.COPPER_TUNNEL, "铜隧道");
        b.add(CDBlocks.MULTIFUNC_BASIN, "多功能工作盆");
        b.add(CDBlocks.VERTICAL_CUTTER, "纵切机");
        b.add(Internationals.keyOfCategory(CDRecipeTypes.VERTICAL_CUTTING.getId()), "纵切");
        b.add(CDBlocks.PRESSURE_COOKER, "压力锅控制器");
        b.add(Internationals.keyOfCategory(CDRecipeTypes.PRESSURE_COOKING.getId()), "压煮");
        b.add(CDItems.COOKED_RICE, "大米饭");
        b.add(CDItems.VEGETABLE_BIG_STEW, "蔬菜大乱炖");
        b.add(VirtualFluids.getTranslationKey(CDFluids.VEGETABLE_BIG_STEW), "蔬菜大乱炖");
        b.add(CDBlocks.MINCER, "绞肉机");
        b.add(Internationals.keyOfCategory(CDRecipeTypes.MINCING.getId()), "绞肉");
        b.add("modmenu.nameTranslation." + CreateSDelight.MOD_ID, "机械动力乐事");
        b.add(VirtualFluids.getTranslationKey(CDFluids.ROSE_MILK_TEA), "玫瑰奶茶");
        b.add(CDItems.ROSE_MILK_TEA, "玫瑰奶茶");
        b.add(CDItems.CORAL_COLORFULS, "珊彩");
        b.add(VirtualFluids.getTranslationKey(CDFluids.BEETROOT_SOUP), "甜菜汤");
        b.add(VirtualFluids.getTranslationKey(CDFluids.TOMATO_SAUCE), "番茄酱");
        b.add(CDItems.POPPY_RUSSIAN_SOUP, "虞美人红菜汤");
        b.add(VirtualFluids.getTranslationKey(CDFluids.POPPY_RUSSIAN_SOUP), "虞美人红菜汤");
        b.add(VirtualFluids.getTranslationKey(CDFluids.EGG_LIQUID), "鸡蛋液");
        b.add(CDItems.EGG_SHELL, "鸡蛋壳");
        b.add(CDItems.EGG_DOUGH, "鸡蛋面团");
        b.add(CDItems.CRUSHED_ICE, "碎冰");
        b.add(CDItems.WHEAT_BLACK_TEA, "麦籽黑茶");
        b.add(VirtualFluids.getTranslationKey(CDFluids.WHEAT_BLACK_TEA), "麦籽黑茶");
        b.add(VirtualFluids.getTranslationKey(CDFluids.ICED_MELON_JUICE), "冰镇西瓜汁");
        b.add(CDItems.ICED_MELON_JUICE, "冰镇西瓜汁");
        b.add(VirtualFluids.getTranslationKey(CDFluids.MELON_JUICE), "西瓜汁");
        b.add(CDItems.THICK_HOT_COCOA, "厚热可可");
        b.add(VirtualFluids.getTranslationKey(CDFluids.THICK_HOT_COCOA), "厚热可可");
        b.add(CDBlocks.SKEWER, "炙烤扦");
        b.add(CDBlocks.BASIN, "工作盆");
        b.add(CDBlocks.SKEWER_PLATE, "炙烤盘");
        b.add(CDItems.SALT, "盐");
        b.add(CDItems.KELP_ASH, "海带灰烬");
        b.add(CDBlocks.JELLY_BEANS, "糖豆");
        b.add(CDBlocks.JELLY_BEANS_CAKE, "糖豆蛋糕");
        b.add(CDItems.YEAST, "酵母");
        b.add(VirtualFluids.getTranslationKey(CDFluids.PASTE), "面糊");
        b.add(CDItems.CAKE_BASE, "蛋糕胚");
        b.add(CDItems.CAKE_BASE_SLICE, "蛋糕胚片");
        b.add(CDBlocks.SWEET_BERRIES_CAKE, "甜浆果蛋糕");
        b.add(CDBlocks.BASQUE_CAKE, "巴斯克蛋糕");
        b.add(CDItems.RAW_BASQUE_CAKE, "生巴斯克蛋糕");
        b.add(CDBlocks.SWEET_BERRIES_CAKE_S, "多层甜浆果蛋糕");
        b.add(CDBlocks.BROWNIE, "布朗尼");
        b.add(CDBlocks.APPLE_CREAM_CAKE, "苹果奶油蛋糕");
        b.add(CDItems.SUNFLOWER_KERNELS, "葵花籽仁");
        b.add(CDItems.BUCKETED_PUMPKIN_OIL, "桶装南瓜籽油");
        b.add(VirtualFluids.getTranslationKey(CDFluids.PUMPKIN_OIL), "南瓜籽油");
        b.add(CDBlocks.OVEN, "烤箱");
        b.add(VirtualFluids.getTranslationKey(CDFluids.APPLE_PASTE), "苹果面糊");
        b.add(CDBlocks.APPLE_CAKE, "苹果蛋糕");
        b.add(CDBlocks.CARROT_CREAM_CAKE, "胡萝卜奶油蛋糕");
        b.add(CDItems.MASHED_POTATO, "碗装土豆泥");
        b.add(VirtualFluids.getTranslationKey(CDFluids.MASHED_POTATO), "土豆泥");
        b.add(CDItems.JELLY_BEANS_CAKE_0, "制作中的糖豆蛋糕");
        b.add(CDItems.SWEET_BERRIES_CAKE_0, "制作中的甜浆果蛋糕");
        b.add(CDItems.SWEET_BERRIES_CAKE_S_0, "制作中的多层甜浆果蛋糕");
        b.add(CDItems.RAW_BASQUE_CAKE_0, "制作中的生巴斯克蛋糕");
        b.add(CDItems.BROWNIE_0, "制作中的布朗尼");
        b.add(CDItems.APPLE_CREAM_CAKE_0, "制作中的苹果奶油蛋糕");
        b.add(CDItems.CARROT_CREAM_CAKE_0, "制作中的胡萝卜奶油蛋糕");
        b.add(CDItems.IRON_BOWL, "铁碗");
        b.add(IronBowlItem.getSuffixKey(), "%s（%s）");
        b.add(Internationals.keyOfCategory(CDRecipeTypes.BAKING.getId()), "烘焙");
        b.add(CDBlocks.SMALL_CHOCOLATE_CREAM_CAKE, "小巧克力奶油蛋糕");
        b.add(CDBlocks.MEDIUM_CHOCOLATE_CREAM_CAKE, "中巧克力奶油蛋糕");
        b.add(CDBlocks.BIG_CHOCOLATE_CREAM_CAKE, "大巧克力奶油蛋糕");
        b.add(VirtualFluids.getTranslationKey(CDFluids.CHOCOLATE_PASTE), "巧克力面糊");
        b.add(CDItems.CHOCOLATE_CAKE_BASE, "巧克力蛋糕胚");
        b.add(CDBlocks.IRON_BAR_SKEWER, "铁栏杆炙烤扦");
        b.add(CDBlocks.CHOCOLATE_ANTHEMY_CAKE, "巧克力八仙蛋糕");
        b.add(Internationals.SECONDS, "%s秒");
        b.add(Internationals.MST, "%s分%s秒%s刻");
        b.add(CDItems.CAKE_BLUEPRINT, "蛋糕蓝图");
        b.add(CDBlocks.PRINTED_CAKE, "蓝图蛋糕");
    }
}
