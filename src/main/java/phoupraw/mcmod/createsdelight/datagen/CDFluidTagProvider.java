package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import phoupraw.mcmod.createsdelight.registry.CDFluidTags;
import phoupraw.mcmod.createsdelight.registry.CDFluids;

import java.util.concurrent.CompletableFuture;

public final class CDFluidTagProvider extends FabricTagProvider.FluidTagProvider {

    public CDFluidTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(CDFluidTags.OIL).add(CDFluids.SUNFLOWER_OIL, CDFluids.PUMPKIN_OIL);
    }

}
