package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.KineticTileEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.MapColor;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import phoupraw.mcmod.common.api.Lambdas;
import phoupraw.mcmod.common.api.StatusEffectsTooltipComponent;
import phoupraw.mcmod.common.api.StatusEffectsTooltipData;
import phoupraw.mcmod.common.api.VirtualFluids;
import phoupraw.mcmod.createsdelight.model.IronBowlModel;
import phoupraw.mcmod.createsdelight.model.PrintedCakeModel;

import java.awt.*;
@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public final class CDClientModInitializer implements ClientModInitializer {
    @SuppressWarnings("unchecked")
    public static <T extends KineticTileEntity> BlockEntityRendererFactory<T> castKineticRendererFactory() {
        return (BlockEntityRendererFactory<T>) (BlockEntityRendererFactory<KineticTileEntity>) KineticTileEntityRenderer::new;
    }

    /**
     屁用没有
     */
    @Contract(pure = true)
    public static int mixColor(int rgb1, double weight1, int rgb2, double weight2) {
        float[] hsb1 = Color.RGBtoHSB(rgb1 >> 16, rgb1 >> 16 & 0xff, rgb1 & 0xff, new float[3]);
        float[] hsb2 = Color.RGBtoHSB(rgb2 >> 16, rgb2 >> 16 & 0xff, rgb2 & 0xff, new float[3]);
        float[] hsb3 = new float[3];
        for (int i = 0; i < 3; i++) {
            hsb3[i] = (float) ((hsb1[i] * weight1 + hsb2[i] * weight2) / (weight1 + weight2));
        }
        return Color.HSBtoRGB(hsb3[0], hsb3[1], hsb3[2]);
    }

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
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), /*CDBlocks.GRILL, CDBlocks.SPRINKLER, CDBlocks.BAMBOO_STEAMER, CDBlocks.SMART_DRAIN, CDBlocks.COPPER_TUNNEL, CDBlocks.MULTIFUNC_BASIN, CDBlocks.PRESSURE_COOKER, CDBlocks.MINCER,*/ CDBlocks.JELLY_BEANS, CDBlocks.JELLY_BEANS_CAKE);

        FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.SUNFLOWER_OIL, SimpleFluidRenderHandler.coloredWater(MapColor.TERRACOTTA_YELLOW.color));
        FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.PUMPKIN_OIL, SimpleFluidRenderHandler.coloredWater(MapColor.TERRACOTTA_ORANGE.color));
        Identifier milk_still = new Identifier("milk", "block/milk_still");
        FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.MELON_JUICE, VirtualFluids.newSimpleFluidRenderHandler(milk_still, 0xE24334));
        FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.PASTE, VirtualFluids.newSimpleFluidRenderHandler(milk_still, MapColor.TERRACOTTA_WHITE.color));
        FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.APPLE_PASTE, VirtualFluids.newSimpleFluidRenderHandler(milk_still, 0xfffab9));
        FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.CHOCOLATE_PASTE, VirtualFluids.newSimpleFluidRenderHandler(milk_still, 0xf2aba0));
        Identifier turbid = CDIdentifiers.of("block/turbid");
        FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.TOMATO_SAUCE, VirtualFluids.newSimpleFluidRenderHandler(turbid, MapColor.RED.color));
        FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.BEETROOT_SOUP, VirtualFluids.newSimpleFluidRenderHandler(turbid, MapColor.DULL_RED.color));
        //FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.POPPY_RUSSIAN_SOUP, VirtualFluids.newSimpleFluidRenderHandler(turbid, MapColor.DARK_RED.color));
        //FluidRenderHandlerRegistry.INSTANCE.register(CDFluids.MASHED_POTATO, VirtualFluids.newSimpleFluidRenderHandler(turbid, MapColor.PALE_YELLOW.color));
        VirtualFluids.registerTexture(CDFluids.EGG_LIQUID/*, CDFluids.ICED_MELON_JUICE, CDFluids.THICK_HOT_COCOA, CDFluids.WHEAT_BLACK_TEA, CDFluids.ROSE_MILK_TEA, CDFluids.VEGETABLE_BIG_STEW*/);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), CDFluids.SUNFLOWER_OIL, /*CDFluids.VEGETABLE_BIG_STEW, CDFluids.ROSE_MILK_TEA, */CDFluids.BEETROOT_SOUP, CDFluids.TOMATO_SAUCE, /*CDFluids.POPPY_RUSSIAN_SOUP, CDFluids.WHEAT_BLACK_TEA,*/ CDFluids.PUMPKIN_OIL);

        ModelLoadingRegistry.INSTANCE.registerResourceProvider(resourceManager -> (resourceId, context) -> {
            if (resourceId.equals(IronBowlModel.Unbaked.ID)) return new IronBowlModel.Unbaked();
            if (resourceId.equals(PrintedCakeModel.BLOCK_ID) || resourceId.equals(PrintedCakeModel.ITEM_ID)) return new PrintedCakeModel.Unbaked();
            return null;
        });
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register(Lambdas.addingTextures(turbid));
        TooltipComponentCallback.EVENT.register(data -> data instanceof StatusEffectsTooltipData data1 ? new StatusEffectsTooltipComponent(data1.statusEffects()) : null);
    }
}
