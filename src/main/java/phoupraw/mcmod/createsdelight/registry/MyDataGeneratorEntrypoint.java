package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.createsdelight.datagen.*;
@ApiStatus.Internal
public final class MyDataGeneratorEntrypoint implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        generator.addProvider(new MyRecipeProvider(generator));
        generator.addProvider(new MyBlockTagProvider(generator));
        generator.addProvider(new MyItemTagProvider(generator));
        generator.addProvider(new MyBlockLootTableProvider(generator));
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            generator.addProvider(new MyChineseProvider(generator));
            generator.addProvider(new MyEnglishProvider(generator));
            generator.addProvider(new MyModelProvider(generator));
        }
    }
}
