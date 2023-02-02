package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import phoupraw.mcmod.createsdelight.block.entity.renderer.GrillRenderer;
import phoupraw.mcmod.createsdelight.block.entity.renderer.PanRenderer;
@Environment(EnvType.CLIENT)
public final class MyClientModInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockEntityRendererRegistry.register(MyBlockEntityTypes.PAN, PanRenderer::new);
        BlockEntityRendererRegistry.register(MyBlockEntityTypes.GRILL, GrillRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), MyBlocks.GRILL);
    }
}
