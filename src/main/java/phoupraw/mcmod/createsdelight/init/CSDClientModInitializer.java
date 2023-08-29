package phoupraw.mcmod.createsdelight.init;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.createsdelight.misc.StatusEffectsTooltipComponent;
import phoupraw.mcmod.createsdelight.misc.StatusEffectsTooltipData;
import phoupraw.mcmod.createsdelight.model.PrintedCakeModel;
import phoupraw.mcmod.createsdelight.registry.CDIdentifiers;

@Environment(EnvType.CLIENT)
public final class CSDClientModInitializer implements ClientModInitializer {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void loadClasses() {
        //CDInstancings.SPRINKLER.hashCode();
        //CDPartialModels.SPRINKLER_LID.hashCode();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onInitializeClient() {
        loadClasses();

        //BlockEntityRendererFactories.register(CDBETypes.PAN, PanRenderer::new);
        //BlockEntityRendererFactories.register(CDBETypes.GRILL, GrillRenderer::new);
        //BlockEntityRendererFactories.register(CDBETypes.SPRINKLER, SprinklerRenderer::new);
        //BlockEntityRendererFactories.register(CDBETypes.BAMBOO_STEAMER, BambooSteamerRenderer::new);
        //BlockEntityRendererFactories.register(CDBETypes.SMART_DRAIN, SmartDrainRenderer::new);
        //BlockEntityRendererFactories.register(CDBETypes.COPPER_TUNNEL, CopperTunnelRenderer::new);
        //BlockEntityRendererFactories.register(CDBETypes.MULTIFUNC_BASIN, MultifuncBasinRenderer::new);
        //BlockEntityRendererFactories.register(CDBETypes.VERTICAL_CUTTER, castKineticRendererFactory());
        //BlockEntityRendererFactories.register(CDBETypes.PRESSURE_COOKER, castKineticRendererFactory());
        //BlockEntityRendererFactories.register(CDBETypes.MINCER, castKineticRendererFactory());
        //BlockEntityRendererFactories.register(CDBETypes.SKEWER, SkewerRenderer::new);
        //BlockEntityRendererFactories.register(CDBETypes.SKEWER_PLATE, SkewerPlateRenderer::new);
        //BlockEntityRendererFactories.register(CDBETypes.OVEN, OvenRenderer::new);
        //BlockEntityRendererFactories.register(CDBETypes.IRON_BAR_SKEWER, IronBarSkewerRenderer::new);
        //BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), /*CDBlocks.GRILL, CDBlocks.SPRINKLER, CDBlocks.BAMBOO_STEAMER, CDBlocks.SMART_DRAIN, CDBlocks.COPPER_TUNNEL, CDBlocks.MULTIFUNC_BASIN, CDBlocks.PRESSURE_COOKER, CDBlocks.MINCER,*/ CDBlocks.JELLY_BEANS, CDBlocks.JELLY_BEANS_CAKE);

        //FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.SUNFLOWER_OIL, SimpleFluidRenderHandler.coloredWater(MapColor.TERRACOTTA_YELLOW.color));
        //FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.PUMPKIN_OIL, SimpleFluidRenderHandler.coloredWater(MapColor.TERRACOTTA_ORANGE.color));
        Identifier milk_still = new Identifier("milk", "block/milk_still");
        //FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.MELON_JUICE, VirtualFluids.newSimpleFluidRenderHandler(milk_still, 0xE24334));
        //FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.PASTE, VirtualFluids.newSimpleFluidRenderHandler(milk_still, MapColor.TERRACOTTA_WHITE.color));
        //FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.APPLE_PASTE, VirtualFluids.newSimpleFluidRenderHandler(milk_still, 0xfffab9));
        //FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.CHOCOLATE_PASTE, VirtualFluids.newSimpleFluidRenderHandler(milk_still, 0xf2aba0));
        Identifier turbid = CDIdentifiers.of("block/turbid");
        //FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.TOMATO_SAUCE, VirtualFluids.newSimpleFluidRenderHandler(turbid, MapColor.RED.color));
        //FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.BEETROOT_SOUP, VirtualFluids.newSimpleFluidRenderHandler(turbid, MapColor.DULL_RED.color));
        //FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.POPPY_RUSSIAN_SOUP, VirtualFluids.newSimpleFluidRenderHandler(turbid, MapColor.DARK_RED.color));
        //FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.MASHED_POTATO, VirtualFluids.newSimpleFluidRenderHandler(turbid, MapColor.PALE_YELLOW.color));
        //VirtualFluids.registerTexture(CDFluids.EGG_LIQUID/*, CDFluids.ICED_MELON_JUICE, CDFluids.THICK_HOT_COCOA, CDFluids.WHEAT_BLACK_TEA, CDFluids.ROSE_MILK_TEA, CDFluids.VEGETABLE_BIG_STEW*/);
        //BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), CDFluids.SUNFLOWER_OIL, /*CDFluids.VEGETABLE_BIG_STEW, CDFluids.ROSE_MILK_TEA, */CDFluids.BEETROOT_SOUP, CDFluids.TOMATO_SAUCE, /*CDFluids.POPPY_RUSSIAN_SOUP, CDFluids.WHEAT_BLACK_TEA,*/ CDFluids.PUMPKIN_OIL);

        ModelLoadingRegistry.INSTANCE.registerResourceProvider(resourceManager -> (resourceId, context) -> {
            //if (resourceId.equals(IronBowlModel.Unbaked.ID)) return new IronBowlModel.Unbaked();
            if (resourceId.equals(PrintedCakeModel.BLOCK_ID) || resourceId.equals(PrintedCakeModel.ITEM_ID)) return new PrintedCakeModel.Unbaked();
            return null;
        });
        //ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register(Lambdas.addingTextures(turbid));
        TooltipComponentCallback.EVENT.register(data -> data instanceof StatusEffectsTooltipData data1 ? new StatusEffectsTooltipComponent(data1.statusEffects()) : null);
    }

}
