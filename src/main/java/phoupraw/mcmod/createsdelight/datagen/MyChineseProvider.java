package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import phoupraw.mcmod.createsdelight.CreateSDelight;
public class MyChineseProvider extends FabricLanguageProvider {
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

    }
}
