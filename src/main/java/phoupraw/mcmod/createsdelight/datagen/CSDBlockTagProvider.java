package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.HolderLookup;
import net.minecraft.registry.tag.BlockTags;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;

import java.util.concurrent.CompletableFuture;

public final class CSDBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public CSDBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }
    @Override
    protected void configure(HolderLookup.Provider arg) {
        //getOrCreateTagBuilder(TagsRegistry.HEAT_SOURCES).add(AllBlocks.LIT_BLAZE_BURNER.get());
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(CSDBlocks.CHOCOLATE_BLOCK);
        //getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(new Block[]{});
    }

}
