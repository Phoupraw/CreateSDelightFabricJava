package phoupraw.mcmod.createsdelight;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.CSDFluids;
import phoupraw.mcmod.createsdelight.registry.CSDIdentifiers;

import java.util.Map;

@Environment(EnvType.CLIENT)
public final class CSDClientModInitializer implements ClientModInitializer {

    public static final Map<Identifier, UnbakedModel> CUSTOM_MODEL_REGISTRY = Map.of(
      ReadyCakeModel.ID, new ConstUnbakedModel(new ReadyCakeModel()),
      PrintedCakeModel.BLOCK_ID, new ConstUnbakedModel(new PrintedCakeModel()),
      PrintedCakeModel.ITEM_ID, new ConstUnbakedModel(new PrintedCakeModel())
    );

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
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(resourceManager -> (resourceId, context) -> CUSTOM_MODEL_REGISTRY.get(resourceId));
        TooltipComponentCallback.EVENT.register(data -> data instanceof StatusEffectsTooltipData data1 ? new StatusEffectsTooltipComponent(data1.statusEffects()) : null);
        BlockEntityRendererFactories.register(CSDBlockEntityTypes.SHRINKING_CAKE, ShrinkingCakeRenderer::new);
        BlockEntityRendererFactories.register(CSDBlockEntityTypes.MOVING_CAKE, MovingCakeRenderer::new);
    }

}
