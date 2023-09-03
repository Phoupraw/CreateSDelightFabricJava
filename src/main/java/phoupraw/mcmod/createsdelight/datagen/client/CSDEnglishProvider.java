package phoupraw.mcmod.createsdelight.datagen.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;


public final class CSDEnglishProvider extends FabricLanguageProvider {

    public CSDEnglishProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateTranslations(TranslationBuilder b) {
        new CSDChineseProvider(dataOutput).generateTranslations(b);
    }

}
