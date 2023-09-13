package phoupraw.mcmod.createsdelight.client;

import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.CSDFluids;
import phoupraw.mcmod.createsdelight.registry.CSDIdentifiers;
import phoupraw.mcmod.createsdelight.registry.CSDItems;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public final class CSDClientModInitializer implements ClientModInitializer {
    public static final Map<Identifier, UnbakedModel> CUSTOM_MODEL_REGISTRY = Map.of(
      InProdCakeModel.ID, new ConstUnbakedModel(new InProdCakeModel()),
      PrintedCakeModel.BLOCK_ID, new ConstUnbakedModel(new BlockVoxelModel()),
      PrintedCakeModel.ITEM_ID, new ConstUnbakedModel(new PrintedCakeModel())/*,
      ModelIds.getBlockModelId(Blocks.BIRCH_PLANKS), new ConstUnbakedModel(new BlockVoxelModel())*/
    );
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void loadClasses() {
        CSDInstancings.CAKE_OVEN.hashCode();
        //CSDPartialModels.IN_RPOD_CAKE.hashCode();
    }
    @SuppressWarnings("deprecation")
    @Override
    public void onInitializeClient() {
        loadClasses();
        Identifier textureId = CSDIdentifiers.EGG_LIQUID.withPrefixedPath("block/");
        FluidRenderHandlerRegistry.INSTANCE.register(CSDFluids.EGG_LIQUID, new SimpleFluidRenderHandler(textureId, textureId));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(resourceManager -> (resourceId, context) -> CUSTOM_MODEL_REGISTRY.get(resourceId));
        TooltipComponentCallback.EVENT.register(data -> data instanceof StatusEffectsTooltipData data1 ? new StatusEffectsTooltipComponent(data1.statusEffects()) : null);
        //BlockEntityRendererFactories.register(CSDBlockEntityTypes.IN_PROD_CAKE, InProdCakeRenderer::new);
        BlockEntityRendererFactories.register(CSDBlockEntityTypes.CAKE_OVEN, CakeOvenRenderer::new);
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> ((ReloadableResourceManagerImpl) client.getResourceManager()).registerReloader((synchronizer, manager1, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor) -> CompletableFuture
          .completedFuture(null)
          .thenCompose(synchronizer::whenPrepared)
          .thenRunAsync(() -> {
              PrintedCakeModel.BLOCK_CACHE.clear();
              PrintedCakeModel.ITEM_CACHE.clear();
              PrintedCakeModel.SPRITE_CACHE.clear();
              InProdCakeModel.CACHE.invalidateAll();
          }, applyExecutor)));
        for (Item item : CSDItems.ITEM_GROUP.getDisplayStacks().stream().map(ItemStack::getItem).collect(Collectors.toSet())) {
            TooltipModifier.REGISTRY.register(item, new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE).andThen(TooltipModifier.mapNull(KineticStats.create(item))));
        }
        //for (Item item : new Item[]{CSDItems.CAKE_OVEN}) {
        //    TooltipModifier.REGISTRY.register(item, new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE).andThen(TooltipModifier.mapNull(KineticStats.create(item))));
        //}
    }
}
