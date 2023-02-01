package phoupraw.mcmod.createsdelight;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import phoupraw.mcmod.createsdelight.block.entity.renderer.PanRenderer;
import phoupraw.mcmod.createsdelight.datagen.*;
import phoupraw.mcmod.createsdelight.registry.*;
public final class CreateSDelight implements ModInitializer, ClientModInitializer/*FIXME 这会引起专用服务端崩溃*/, DataGeneratorEntrypoint {
    public static final String MOD_ID = "createsdelight";

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void loadClasses() {
        MyPointTypes.BASKET.hashCode();
        MyBlocks.PAN.hashCode();
        MyBlockEntityTypes.PAN.hashCode();
        MyItems.ITEM_GROUP.hashCode();
        MyRecipeTypes.PAN_FRYING.hashCode();
        MySpoutingBehaviours.PAN.hashCode();
        MyFluids.SUNFLOWER_OIL.hashCode();
        MyStatusEffects.SATIATION.hashCode();
    }

    @Override
    public void onInitialize() {
        loadClasses();
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(MyBlockEntityTypes.PAN, PanRenderer::new);
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        generator.addProvider(MyRecipeProvider::new);
        generator.addProvider(MyTagProvider::new);

        generator.addProvider(MyChineseProvider::new);
        generator.addProvider(MyEnglishProvider::new);
        generator.addProvider(MyModelProvider::new);
    }
}
