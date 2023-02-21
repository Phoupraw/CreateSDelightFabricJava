package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import phoupraw.mcmod.createsdelight.block.entity.renderer.*;
@Environment(EnvType.CLIENT)
public final class MyClientModInitializer implements ClientModInitializer {
    @SuppressWarnings("unchecked")
    public static <T extends KineticTileEntity> BlockEntityRendererFactory<T> castKineticRendererFactory() {
        return (BlockEntityRendererFactory<T>) (BlockEntityRendererFactory<KineticTileEntity>) KineticTileEntityRenderer::new;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void loadClasses() {
        MyInstancings.SPRINKLER.hashCode();
        MyPartialModels.SPRINKLER_LID.hashCode();
    }

    @Override
    public void onInitializeClient() {
        loadClasses();

        BlockEntityRendererFactories.register(MyBlockEntityTypes.PAN, PanRenderer::new);
        BlockEntityRendererFactories.register(MyBlockEntityTypes.GRILL, GrillRenderer::new);
        BlockEntityRendererFactories.register(MyBlockEntityTypes.SPRINKLER, SprinklerRenderer::new);
        BlockEntityRendererFactories.register(MyBlockEntityTypes.BAMBOO_STEAMER, BambooSteamerRenderer::new);
        BlockEntityRendererFactories.register(MyBlockEntityTypes.SMART_DRAIN, SmartDrainRenderer::new);
        BlockEntityRendererFactories.register(MyBlockEntityTypes.COPPER_TUNNEL, CopperTunnelRenderer::new);
        BlockEntityRendererFactories.register(MyBlockEntityTypes.MULTIFUNC_BASIN, MultifuncBasinRenderer::new);
        BlockEntityRendererFactories.register(MyBlockEntityTypes.VERTICAL_CUTTER, castKineticRendererFactory());
        BlockEntityRendererFactories.register(MyBlockEntityTypes.PRESSURE_COOKER, castKineticRendererFactory());
        BlockEntityRendererFactories.register(MyBlockEntityTypes.MINCER, castKineticRendererFactory());
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), MyBlocks.GRILL, MyBlocks.SPRINKLER, MyBlocks.BAMBOO_STEAMER, MyBlocks.SMART_DRAIN, MyBlocks.COPPER_TUNNEL, MyBlocks.MULTIFUNC_BASIN, MyBlocks.PRESSURE_COOKER);
    }
}
