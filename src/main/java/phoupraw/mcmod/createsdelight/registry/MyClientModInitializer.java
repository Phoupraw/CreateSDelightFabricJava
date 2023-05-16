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
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import phoupraw.mcmod.common.api.Lambdas;
import phoupraw.mcmod.common.api.StatusEffectsTooltipComponent;
import phoupraw.mcmod.common.api.StatusEffectsTooltipData;
import phoupraw.mcmod.common.api.VirtualFluids;
import phoupraw.mcmod.createsdelight.block.entity.renderer.*;
import phoupraw.mcmod.createsdelight.model.PrintedCakeModel;
import phoupraw.mcmod.createsdelight.model.UnbakedIronBowlModel;

import java.awt.*;
@Environment(EnvType.CLIENT)
@ApiStatus.Internal
public final class MyClientModInitializer implements ClientModInitializer {
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
        MyInstancings.SPRINKLER.hashCode();
        MyPartialModels.SPRINKLER_LID.hashCode();
    }

    @SuppressWarnings("deprecation")
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
        BlockEntityRendererFactories.register(MyBlockEntityTypes.SKEWER, SkewerRenderer::new);
        BlockEntityRendererFactories.register(MyBlockEntityTypes.SKEWER_PLATE, SkewerPlateRenderer::new);
        BlockEntityRendererFactories.register(MyBlockEntityTypes.OVEN, OvenRenderer::new);
        BlockEntityRendererFactories.register(MyBlockEntityTypes.IRON_BAR_SKEWER, IronBarSkewerRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), MyBlocks.GRILL, MyBlocks.SPRINKLER, MyBlocks.BAMBOO_STEAMER, MyBlocks.SMART_DRAIN, MyBlocks.COPPER_TUNNEL, MyBlocks.MULTIFUNC_BASIN, MyBlocks.PRESSURE_COOKER, MyBlocks.MINCER, MyBlocks.JELLY_BEANS, MyBlocks.JELLY_BEANS_CAKE, MyBlocks.OVEN);

        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.SUNFLOWER_OIL, SimpleFluidRenderHandler.coloredWater(MapColor.TERRACOTTA_YELLOW.color));
        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.PUMPKIN_OIL, SimpleFluidRenderHandler.coloredWater(MapColor.TERRACOTTA_ORANGE.color));
        Identifier milk_still = new Identifier("milk", "block/milk_still");
        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.MELON_JUICE, VirtualFluids.newSimpleFluidRenderHandler(milk_still, 0xE24334));
        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.PASTE, VirtualFluids.newSimpleFluidRenderHandler(milk_still, MapColor.TERRACOTTA_WHITE.color));
        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.APPLE_PASTE, VirtualFluids.newSimpleFluidRenderHandler(milk_still, 0xfffab9));
        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.CHOCOLATE_PASTE, VirtualFluids.newSimpleFluidRenderHandler(milk_still, 0xf2aba0));
        Identifier turbid = MyIdentifiers.of("block/turbid");
        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.TOMATO_SAUCE, VirtualFluids.newSimpleFluidRenderHandler(turbid, MapColor.RED.color));
        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.BEETROOT_SOUP, VirtualFluids.newSimpleFluidRenderHandler(turbid, MapColor.DULL_RED.color));
        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.POPPY_RUSSIAN_SOUP, VirtualFluids.newSimpleFluidRenderHandler(turbid, MapColor.DARK_RED.color));
        FluidRenderHandlerRegistry.INSTANCE.register(MyFluids.MASHED_POTATO, VirtualFluids.newSimpleFluidRenderHandler(turbid, MapColor.PALE_YELLOW.color));
        VirtualFluids.registerTexture(MyFluids.EGG_LIQUID, MyFluids.ICED_MELON_JUICE, MyFluids.THICK_HOT_COCOA, MyFluids.WHEAT_BLACK_TEA, MyFluids.ROSE_MILK_TEA, MyFluids.VEGETABLE_BIG_STEW);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), MyFluids.SUNFLOWER_OIL, MyFluids.VEGETABLE_BIG_STEW, MyFluids.ROSE_MILK_TEA, MyFluids.BEETROOT_SOUP, MyFluids.TOMATO_SAUCE, MyFluids.POPPY_RUSSIAN_SOUP, MyFluids.WHEAT_BLACK_TEA, MyFluids.PUMPKIN_OIL);

        ModelLoadingRegistry.INSTANCE.registerResourceProvider(resourceManager -> (resourceId, context) -> {
            if (resourceId.equals(UnbakedIronBowlModel.ID)) return new UnbakedIronBowlModel();
            if (resourceId.equals(PrintedCakeModel.BLOCK_ID) || resourceId.equals(PrintedCakeModel.ITEM_ID)) return new PrintedCakeModel.Unbaked();
            return null;
        });
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register(Lambdas.addingTextures(turbid));
        TooltipComponentCallback.EVENT.register(data -> data instanceof StatusEffectsTooltipData data1 ? new StatusEffectsTooltipComponent(data1.statusEffects()) : null);
    }
}
