package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import phoupraw.mcmod.createsdelight.CreateSDelight;
public class MyEnglishProvider extends FabricLanguageProvider {
	public MyEnglishProvider(FabricDataGenerator dataGenerator) {
		super(dataGenerator);
	}

	@Override
	public void generateTranslations(TranslationBuilder builder) {
		builder.add("modmenu.descriptionTranslation." + CreateSDelight.MOD_ID, """
			# Create'S Delight
			- Let belt directly output to stove, cooking pot, skillet, basket, cutting board.
			- Let mechanical arm interact with stove, cooking pot, skillet, basket, cutting board.
			- Add filling recipe for chocolate pie.
			- Let lit blaze burner become heat source of farmer's delight.
			This mod is still in very early development. Any features are unstable. If you want to play for a long time, please backup.
			""");

	}
}
