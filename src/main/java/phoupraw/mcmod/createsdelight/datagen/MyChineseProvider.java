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
        b.add(Internationals.keyOfItemGroup(MyIdentifiers.ITEM_GROUP), "机械动力乐事");
        b.add(MyBlocks.PAN, "平底锅");
        b.add(VirtualFluids.getTranslationKey(MyFluids.SUNFLOWER_OIL), "葵花籽油");
        b.add(MyItems.BUCKETED_SUNFLOWER_OIL, "桶装葵花籽油");
        b.add(MyItems.BOTTLED_SUNFLOWER_OIL, "瓶装葵花籽油");
        b.add(MyItems.PAN_FRIED_BEEF_PATTY, "香煎牛肉饼");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.PAN_FRYING.getId()), "煎");
        b.add(MyStatusEffects.SATIATION, "饱食");
        b.add(MyBlocks.GRILL, "烤架");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.GRILLING.getId()), "烤");
        b.add(MyItems.THICK_PORK_SLICE, "厚猪肉片");
        b.add(MyItems.PAN_FRIED_PORK_SLICE, "煎猪肉片");
        b.add(MyItems.THIN_PORK_SLICE, "薄猪肉片");
        b.add(MyItems.GRILLED_PORK_SLICE, "烤猪肉片");
        b.add(MyItems.SUGAR_PORK, "糖霜猪肉");
        b.add(MyBlocks.SPRINKLER, "调料喷撒机");
        b.add("empty", "空");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.SPRINKLING.getId()), "撒料");
        b.add(MyItems.LEAVES_RICE, "树叶米饭");
        b.add(MyItems.VANILLA, "香草");
        b.add(MyItems.VANILLA_SWEET_ROLL, "香草甜甜卷");
        b.add(MyBlocks.BAMBOO_STEAMER, "竹蒸笼");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.STEAMING.getId()), "蒸");
        b.add(MyItems.STEAMED_BUNS, "馒头");
        b.add(MyBlocks.SMART_DRAIN, "智能分液池");
        b.add("burn_time", "燃料时间：%s");
        b.add(MyBlocks.COPPER_TUNNEL, "铜隧道");
        b.add(MyBlocks.MULTIFUNC_BASIN, "多功能工作盆");
        b.add(MyBlocks.VERTICAL_CUTTER, "纵切机");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.VERTICAL_CUTTING.getId()), "纵切");
        b.add(MyBlocks.PRESSURE_COOKER, "压力锅控制器");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.PRESSURE_COOKING.getId()), "压煮");
        b.add(MyItems.COOKED_RICE, "大米饭");
        b.add(MyItems.VEGETABLE_BIG_STEW, "蔬菜大乱炖");
        b.add(VirtualFluids.getTranslationKey(MyFluids.VEGETABLE_BIG_STEW), "蔬菜大乱炖");
        b.add(MyBlocks.MINCER, "绞肉机");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.MINCING.getId()), "绞肉");
        b.add("modmenu.nameTranslation." + CreateSDelight.MOD_ID, "机械动力乐事");
        b.add(VirtualFluids.getTranslationKey(MyFluids.ROSE_MILK_TEA), "玫瑰奶茶");
        b.add(MyItems.ROSE_MILK_TEA, "玫瑰奶茶");
        b.add(MyItems.CORAL_COLORFULS, "珊彩");
        b.add(VirtualFluids.getTranslationKey(MyFluids.BEETROOT_SOUP), "甜菜汤");
        b.add(VirtualFluids.getTranslationKey(MyFluids.TOMATO_SAUCE), "番茄酱");
        b.add(MyItems.POPPY_RUSSIAN_SOUP, "虞美人红菜汤");
        b.add(VirtualFluids.getTranslationKey(MyFluids.POPPY_RUSSIAN_SOUP), "虞美人红菜汤");
        b.add(VirtualFluids.getTranslationKey(MyFluids.EGG_LIQUID), "鸡蛋液");
        b.add(MyItems.EGG_SHELL, "鸡蛋壳");
        b.add(MyItems.EGG_DOUGH, "鸡蛋面团");
        b.add(MyItems.CRUSHED_ICE, "碎冰");
        b.add(MyItems.WHEAT_BLACK_TEA, "麦籽黑茶");
        b.add(VirtualFluids.getTranslationKey(MyFluids.WHEAT_BLACK_TEA), "麦籽黑茶");
        b.add(VirtualFluids.getTranslationKey(MyFluids.ICED_MELON_JUICE), "冰镇西瓜汁");
        b.add(MyItems.ICED_MELON_JUICE, "冰镇西瓜汁");
        b.add(VirtualFluids.getTranslationKey(MyFluids.MELON_JUICE), "西瓜汁");
        b.add(MyItems.THICK_HOT_COCOA, "厚热可可");
        b.add(VirtualFluids.getTranslationKey(MyFluids.THICK_HOT_COCOA), "厚热可可");
        b.add(MyBlocks.SKEWER, "炙烤扦");
        b.add(MyBlocks.BASIN, "工作盆");
        b.add(MyBlocks.SKEWER_PLATE, "炙烤盘");
        b.add(MyItems.SALT, "盐");
        b.add(MyItems.KELP_ASH, "海带灰烬");
        b.add(MyBlocks.JELLY_BEANS, "糖豆");
        b.add(MyBlocks.JELLY_BEANS_CAKE, "糖豆蛋糕");
        b.add(MyItems.YEAST, "酵母");
        b.add(VirtualFluids.getTranslationKey(MyFluids.PASTE), "面糊");
        b.add(MyItems.CAKE_BASE, "蛋糕胚");
        b.add(MyItems.CAKE_BASE_SLICE, "蛋糕胚片");
        b.add(MyBlocks.SWEET_BERRIES_CAKE, "甜浆果蛋糕");
        b.add(MyBlocks.BASQUE_CAKE, "巴斯克蛋糕");
        b.add(MyItems.RAW_BASQUE_CAKE, "生巴斯克蛋糕");
        b.add(MyBlocks.SWEET_BERRIES_CAKE_S, "多层甜浆果蛋糕");
        b.add(MyBlocks.BROWNIE, "布朗尼");
        b.add(MyBlocks.APPLE_CREAM_CAKE, "苹果奶油蛋糕");
        b.add(MyItems.SUNFLOWER_KERNELS, "葵花籽仁");
        b.add(MyItems.BUCKETED_PUMPKIN_OIL, "桶装南瓜籽油");
        b.add(VirtualFluids.getTranslationKey(MyFluids.PUMPKIN_OIL), "南瓜籽油");
        b.add(MyBlocks.OVEN, "烤箱");
        b.add(VirtualFluids.getTranslationKey(MyFluids.APPLE_PASTE), "苹果面糊");
        b.add(MyBlocks.APPLE_CAKE, "苹果蛋糕");
        b.add(MyBlocks.CARROT_CREAM_CAKE, "胡萝卜奶油蛋糕");
        b.add(MyItems.MASHED_POTATO, "碗装土豆泥");
        b.add(VirtualFluids.getTranslationKey(MyFluids.MASHED_POTATO), "土豆泥");
        b.add(MyItems.JELLY_BEANS_CAKE_0, "制作中的糖豆蛋糕");
        b.add(MyItems.SWEET_BERRIES_CAKE_0, "制作中的甜浆果蛋糕");
        b.add(MyItems.SWEET_BERRIES_CAKE_S_0, "制作中的多层甜浆果蛋糕");
        b.add(MyItems.RAW_BASQUE_CAKE_0, "制作中的生巴斯克蛋糕");
        b.add(MyItems.BROWNIE_0, "制作中的布朗尼");
        b.add(MyItems.APPLE_CREAM_CAKE_0, "制作中的苹果奶油蛋糕");
        b.add(MyItems.CARROT_CREAM_CAKE_0, "制作中的胡萝卜奶油蛋糕");
        b.add(MyItems.IRON_BOWL, "铁碗");
        b.add(IronBowlItem.getSuffixKey(), "%s（%s）");
        b.add(Internationals.keyOfCategory(MyRecipeTypes.BAKING.getId()), "烘焙");
        b.add(MyBlocks.SMALL_CHOCOLATE_CREAM_CAKE, "小巧克力奶油蛋糕");
        b.add(MyBlocks.MEDIUM_CHOCOLATE_CREAM_CAKE, "中巧克力奶油蛋糕");
        b.add(MyBlocks.BIG_CHOCOLATE_CREAM_CAKE, "大巧克力奶油蛋糕");
        b.add(VirtualFluids.getTranslationKey(MyFluids.CHOCOLATE_PASTE), "巧克力面糊");
        b.add(MyItems.CHOCOLATE_CAKE_BASE, "巧克力蛋糕胚");
        b.add(MyBlocks.IRON_BAR_SKEWER, "铁栏杆炙烤扦");
        b.add(MyBlocks.CHOCOLATE_ANTHEMY_CAKE, "巧克力八仙蛋糕");
        b.add(Internationals.SECONDS, "%s秒");
        b.add(Internationals.MST, "%s分%s秒%s刻");
        b.add(MyItems.CAKE_BLUEPRINT, "蛋糕蓝图");
        b.add(MyBlocks.PRINTED_CAKE, "蓝图蛋糕");
    }
}
