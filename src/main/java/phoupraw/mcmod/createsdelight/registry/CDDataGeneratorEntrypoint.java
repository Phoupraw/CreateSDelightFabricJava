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
        generator.addProvider(new CDRecipeProvider(generator));
        generator.addProvider(new CDBlockTagProvider(generator));
        generator.addProvider(new CDItemTagProvider(generator));
        generator.addProvider(new CDFluidTagProvider(generator));
        generator.addProvider(new CDBlockLootTableProvider(generator));
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            generator.addProvider(new CDChineseProvider(generator));
            generator.addProvider(new CDEnglishProvider(generator));
            generator.addProvider(new CDModelProvider(generator));
        }
    }
}
