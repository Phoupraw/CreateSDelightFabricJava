package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import phoupraw.mcmod.createsdelight.datagen.client.CDChineseProvider;
import phoupraw.mcmod.createsdelight.datagen.client.CDEnglishProvider;
import phoupraw.mcmod.createsdelight.datagen.client.CDModelProvider;

public final class CSDDataGeneratorEntrypoint implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(CDRecipeProvider::new);
        pack.addProvider(CDBlockLootTableProvider::new);
        pack.addProvider(CDItemTagProvider::new);
        pack.addProvider(CDBlockTagProvider::new);
        pack.addProvider(CDFluidTagProvider::new);
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            pack.addProvider(CDChineseProvider::new);
            pack.addProvider(CDEnglishProvider::new);
            pack.addProvider(CDModelProvider::new);
        }
    }

}
