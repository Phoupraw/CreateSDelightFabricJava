package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.createsdelight.datagen.*;

@ApiStatus.Internal
public final class CDDataGeneratorEntrypoint implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        var pack = generator.createPack();
        pack.addProvider(CDRecipeProvider::new);
        pack.addProvider(CDBlockLootTableProvider::new);
        pack.addProvider(CDItemTagProvider::new);
        pack.addProvider(CDRecipeProvider::new);
        pack.addProvider(CDBlockLootTableProvider::new);
        pack.addProvider(CDFluidTagProvider::new);
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            pack.addProvider(CDChineseProvider::new);
            pack.addProvider(CDEnglishProvider::new);
            pack.addProvider(CDModelProvider::new);
        }
    }

}
