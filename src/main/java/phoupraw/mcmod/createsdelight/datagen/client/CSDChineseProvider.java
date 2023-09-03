package phoupraw.mcmod.createsdelight.datagen.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;
import phoupraw.mcmod.createsdelight.registry.CSDIdentifiers;
import phoupraw.mcmod.createsdelight.registry.CSDItems;
import phoupraw.mcmod.createsdelight.registry.CSDStatusEffects;

public final class CSDChineseProvider extends FabricLanguageProvider {

    public CSDChineseProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "zh_cn");
    }

    @Override
    public void generateTranslations(TranslationBuilder b) {
        b.add("modmenu.nameTranslation." + CreateSDelight.MOD_ID, "机械动力乐事");
        b.add("modmenu.descriptionTranslation." + CreateSDelight.MOD_ID,
          """
          添加了各式蛋糕和自定义蛋糕。
          """);
        b.add(CSDIdentifiers.ITEM_GROUP.toTranslationKey("itemGroup"), "机械动力乐事");
        b.add(CSDBlocks.CAKE_OVEN, "蛋糕烤箱");
        b.add(CSDBlocks.PRINTED_CAKE, "蓝图蛋糕");
        b.add(CSDBlocks.CHOCOLATE_BLOCK, "巧克力块");
        b.add(CSDBlocks.CREAM_BLOCK, "奶油块");
        b.add(CSDIdentifiers.EGG_LIQUID.toTranslationKey("block"), "鸡蛋液");
        b.add(CSDItems.BUCKETED_EGG_LIQUID, "桶装鸡蛋液");
        b.add(CSDItems.EGG_SHELL, "鸡蛋壳");
        b.add(CSDItems.KELP_ASH, "海带灰烬");
        b.add(CSDStatusEffects.SATIATION, "饱食");
    }

}
