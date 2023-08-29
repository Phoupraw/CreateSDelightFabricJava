package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import phoupraw.mcmod.createsdelight.datagen.client.CSDChineseProvider;
import phoupraw.mcmod.createsdelight.datagen.client.CSDEnglishProvider;
import phoupraw.mcmod.createsdelight.datagen.client.CSDModelProvider;

public final class CSDDataGeneratorEntrypoint implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        FabricDataGenerator.Pack pack = generator.createPack();
        pack.addProvider(CSDRecipeProvider::new);
        pack.addProvider(CSDBlockLootTableProvider::new);
        pack.addProvider(CSDItemTagProvider::new);
        pack.addProvider(CSDBlockTagProvider::new);
        pack.addProvider(CSDFluidTagProvider::new);
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            pack.addProvider(CSDChineseProvider::new);
            pack.addProvider(CSDEnglishProvider::new);
            pack.addProvider(CSDModelProvider::new);
        }
    }

}
