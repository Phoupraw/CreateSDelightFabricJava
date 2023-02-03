package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import phoupraw.mcmod.createsdelight.datagen.*;
public class MyDataGeneratorEntrypoint implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        generator.addProvider(MyRecipeProvider::new);
        generator.addProvider(MyBlockTagProvider::new);
        generator.addProvider(MyItemTagProvider::new);
        generator.addProvider(MyBlockLootTableProvider::new);

        generator.addProvider(MyChineseProvider::new);
        generator.addProvider(MyEnglishProvider::new);
        generator.addProvider(MyModelProvider::new);
    }
}
