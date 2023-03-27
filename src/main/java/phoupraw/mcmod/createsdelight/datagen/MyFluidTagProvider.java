package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import phoupraw.mcmod.createsdelight.registry.MyFluidTags;
import phoupraw.mcmod.createsdelight.registry.MyFluids;
public final class MyFluidTagProvider extends FabricTagProvider.FluidTagProvider {
    public MyFluidTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(MyFluidTags.OIL)
          .add(MyFluids.SUNFLOWER_OIL, MyFluids.PUMPKIN_OIL);
    }
}
