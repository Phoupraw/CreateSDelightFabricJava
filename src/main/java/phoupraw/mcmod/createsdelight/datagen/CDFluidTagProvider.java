package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import phoupraw.mcmod.createsdelight.registry.CDFluidTags;
import phoupraw.mcmod.createsdelight.registry.CDFluids;
public final class CDFluidTagProvider extends FabricTagProvider.FluidTagProvider {
    public CDFluidTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(CDFluidTags.OIL).add(CDFluids.SUNFLOWER_OIL, CDFluids.PUMPKIN_OIL);
    }
}
