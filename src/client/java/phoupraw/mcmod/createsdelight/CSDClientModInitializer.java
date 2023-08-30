package phoupraw.mcmod.createsdelight;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.CSDFluids;
import phoupraw.mcmod.createsdelight.registry.CSDIdentifiers;

@Environment(EnvType.CLIENT)
public final class CSDClientModInitializer implements ClientModInitializer {

    /**
     * 如果一个流体的流动纹理和静止纹理相同，可以用此方法快捷创建{@link SimpleFluidRenderHandler}。
     * @param textureId 纹理路径，具体写法可以参考{@link SimpleFluidRenderHandler#WATER_STILL}等。
     * @param tint 用于给纹理染色的RGB颜色，忽略透明度。
     * @return {@link SimpleFluidRenderHandler}
     * @see SimpleFluidRenderHandler#SimpleFluidRenderHandler(Identifier, Identifier, int)
     * @since 1.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    @Environment(EnvType.CLIENT)
    public static @NotNull FluidRenderHandler newSimpleFluidRenderHandler(Identifier textureId, int tint) {
        return new SimpleFluidRenderHandler(textureId, textureId, tint);
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
        Identifier textureId = CSDIdentifiers.EGG_LIQUID.withPrefixedPath("block/");
        FluidRenderHandlerRegistry.INSTANCE.register(CSDFluids.EGG_LIQUID, new SimpleFluidRenderHandler(textureId, textureId));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(resourceManager -> (resourceId, context) -> {
            if (resourceId.equals(ReadyCakeModel.ID)) return new ConstUnbakedModel(new ReadyCakeModel());
            if (resourceId.equals(PrintedCakeModel.BLOCK_ID) || resourceId.equals(PrintedCakeModel.ITEM_ID)) return new ConstUnbakedModel(new PrintedCakeModel());
            return null;
        });
        TooltipComponentCallback.EVENT.register(data -> data instanceof StatusEffectsTooltipData data1 ? new StatusEffectsTooltipComponent(data1.statusEffects()) : null);
        //BlockEntityRendererFactories.register(CSDBlockEntityTypes.READY_CAKE, ReadyCakeRenderer::new);
        BlockEntityRendererFactories.register(CSDBlockEntityTypes.SHRINKING_CAKE, ShrinkingCakeRenderer::new);
        BlockEntityRendererFactories.register(CSDBlockEntityTypes.MOVING_CAKE, MovingCakeRenderer::new);
    }

}
