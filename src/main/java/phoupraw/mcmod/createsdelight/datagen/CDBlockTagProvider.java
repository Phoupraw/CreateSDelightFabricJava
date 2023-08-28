package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public final class CDBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public CDBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        //getOrCreateTagBuilder(TagsRegistry.HEAT_SOURCES).add(AllBlocks.LIT_BLAZE_BURNER.get());
        //getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(CDBlocks.CAKE_OVEN);
        //getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(new Block[]{});
    }

}
