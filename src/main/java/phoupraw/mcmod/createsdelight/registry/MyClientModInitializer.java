package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.block.MapColor;
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
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), MyBlocks.GRILL, MyBlocks.SPRINKLER, MyBlocks.BAMBOO_STEAMER, MyBlocks.SMART_DRAIN, MyBlocks.COPPER_TUNNEL, MyBlocks.MULTIFUNC_BASIN, MyBlocks.PRESSURE_COOKER, MyBlocks.MINCER);
        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.SUNFLOWER_OIL, SimpleFluidRenderHandler.coloredWater(MapColor.TERRACOTTA_YELLOW.color));
        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.VEGETABLE_BIG_STEW, SimpleFluidRenderHandler.coloredWater(MapColor.LICHEN_GREEN.color));
        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.ROSE_MILK_TEA, SimpleFluidRenderHandler.coloredWater(MapColor.DULL_RED.color));
        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.BEETROOT_SOUP, SimpleFluidRenderHandler.coloredWater(MapColor.RED.color));
        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.TOMATO_SAUCE, SimpleFluidRenderHandler.coloredWater(MapColor.DARK_RED.color));
        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.POPPY_RUSSIAN_SOUP, SimpleFluidRenderHandler.coloredWater(MapColor.RED.color));
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), MyFluids.SUNFLOWER_OIL, MyFluids.VEGETABLE_BIG_STEW, MyFluids.ROSE_MILK_TEA, MyFluids.BEETROOT_SOUP, MyFluids.TOMATO_SAUCE, MyFluids.POPPY_RUSSIAN_SOUP);
    }
}
