package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.RenderLayer;
import phoupraw.mcmod.createsdelight.block.entity.CopperTunnelBlockEntity;
import phoupraw.mcmod.createsdelight.block.entity.renderer.*;
@Environment(EnvType.CLIENT)
public final class MyClientModInitializer implements ClientModInitializer {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void loadClasses() {
        MyInstancings.SPRINKLER.hashCode();
        MyPartialModels.SPRINKLER_LID.hashCode();
    }

    @Override
    public void onInitializeClient() {
        loadClasses();

        BlockEntityRendererRegistry.register(MyBlockEntityTypes.PAN, PanRenderer::new);
        BlockEntityRendererRegistry.register(MyBlockEntityTypes.GRILL, GrillRenderer::new);
        BlockEntityRendererRegistry.register(MyBlockEntityTypes.SPRINKLER, SprinklerRenderer::new);
        BlockEntityRendererRegistry.register(MyBlockEntityTypes.BAMBOO_STEAMER, BambooSteamerRenderer::new);
        BlockEntityRendererRegistry.register(MyBlockEntityTypes.SMART_DRAIN, SmartDrainRenderer::new);
        BlockEntityRendererRegistry.register(MyBlockEntityTypes.COPPER_TUNNEL, CopperTunnelRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), MyBlocks.GRILL, MyBlocks.SPRINKLER,MyBlocks.BAMBOO_STEAMER,MyBlocks.SMART_DRAIN,MyBlocks.COPPER_TUNNEL);
    }
}
