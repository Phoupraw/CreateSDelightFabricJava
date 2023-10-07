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
import net.fabricmc.fabric.api.client.rendering.v1.TooltipComponentCallback;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.data.client.ModelIds;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ReloadableResourceManagerImpl;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.misc.VirtualFluids;
import phoupraw.mcmod.createsdelight.registry.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public final class CSDClientModInitializer implements ClientModInitializer {
    public static final Map<Identifier, UnbakedModel> MODELS = Map.of(
      ModelIds.getBlockModelId(CSDBlocks.MADE_VOXEL), new ConstUnbakedModel(new MadeVoxelModel()),
      ModelIds.getItemModelId(CSDItems.MADE_VOXEL), new ConstUnbakedModel(new MadeVoxelModel())
    );
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void loadClasses() {
        CSDInstancings.VOXEL_MAKER.hashCode();
        //CSDPartialModels.IN_RPOD_CAKE.hashCode();
    }
    @SuppressWarnings("deprecation")
    @Override
    public void onInitializeClient() {
        loadClasses();
        FluidRenderHandlerRegistry.INSTANCE.register(CSDFluids.EGG_LIQUID, VirtualFluids.simpleFluidRenderHandlerOfBlock(CSDIdentifiers.EGG_LIQUID));
        FluidRenderHandlerRegistry.INSTANCE.register(CSDFluids.APPLE_JAM, VirtualFluids.simpleFluidRenderHandlerOfBlock(CSDIdentifiers.APPLE_JAM));
        FluidRenderHandlerRegistry.INSTANCE.register(CSDFluids.WHEAT_PASTE, VirtualFluids.simpleFluidRenderHandlerOfBlock(CSDIdentifiers.WHEAT_PASTE));
        FluidRenderHandlerRegistry.INSTANCE.register(CSDFluids.CREAM, VirtualFluids.simpleFluidRenderHandlerOfBlock(CSDIdentifiers.CREAM));
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(resourceManager -> (resourceId, context) -> MODELS.get(resourceId));
        TooltipComponentCallback.EVENT.register(data -> data instanceof StatusEffectsTooltipData data1 ? new StatusEffectsTooltipComponent(data1.statusEffects()) : null);
        //BlockEntityRendererFactories.register(CSDBlockEntityTypes.CAKE_OVEN, CakeOvenRenderer::new);
        BlockEntityRendererFactories.register(CSDBlockEntityTypes.VOXEL_MAKER, VoxelMakerRenderer::new);
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            CreateSDelight.LOGGER.debug("ClientLifecycleEvents.CLIENT_STARTED");
            ((ReloadableResourceManagerImpl) client.getResourceManager()).registerReloader((synchronizer, manager1, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor) -> {
                CreateSDelight.LOGGER.debug("ReloadableResourceManagerImpl.registerReloader");
                return CompletableFuture
                  .completedFuture(null)
                  .thenCompose(synchronizer::whenPrepared)
                  .thenRunAsync(() -> {
                      MadeVoxelModel.NBT2MODEL.clear();
                      MadeVoxelModel.SPRITES.clear();
                      MadeVoxelModel.VOXEL_2_MODEL.invalidateAll();
                  }, applyExecutor);
            });
        });
        for (Item item : CSDItems.ITEM_GROUP.getDisplayStacks().stream().map(ItemStack::getItem).collect(Collectors.toSet())) {
            TooltipModifier.REGISTRY.register(item, new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE).andThen(TooltipModifier.mapNull(KineticStats.create(item))));
        }
    }
}
