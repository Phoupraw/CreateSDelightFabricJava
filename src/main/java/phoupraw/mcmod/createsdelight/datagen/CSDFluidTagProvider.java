package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.HolderLookup;

import java.util.concurrent.CompletableFuture;

public final class CSDFluidTagProvider extends FabricTagProvider.FluidTagProvider {
    public CSDFluidTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }
    @Override
    protected void configure(HolderLookup.Provider arg) {
        //getOrCreateTagBuilder(CDFluidTags.OIL).add(CDFluids.SUNFLOWER_OIL, CDFluids.PUMPKIN_OIL);
    }

}
