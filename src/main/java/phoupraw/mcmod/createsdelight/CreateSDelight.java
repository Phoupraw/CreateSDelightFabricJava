package phoupraw.mcmod.createsdelight;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import phoupraw.mcmod.createsdelight.datagen.MyChineseProvider;
import phoupraw.mcmod.createsdelight.datagen.MyEnglishProvider;
import phoupraw.mcmod.createsdelight.datagen.MyRecipeProvider;
import phoupraw.mcmod.createsdelight.datagen.MyTagProvider;
import phoupraw.mcmod.createsdelight.registry.MyArmInteractionPointTypes;
public final class CreateSDelight implements ModInitializer, ClientModInitializer, DataGeneratorEntrypoint {
    public static final String MOD_ID = "createsdelight";
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void loadClasses() {
        MyArmInteractionPointTypes.BASKET.hashCode();
    }

    @Override
    public void onInitialize() {
        loadClasses();
    }

    @Override
    public void onInitializeClient() {

    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        generator.addProvider(MyRecipeProvider::new);
        generator.addProvider(MyTagProvider::new);

        generator.addProvider(MyChineseProvider::new);
	    generator.addProvider(MyEnglishProvider::new);
    }
}
