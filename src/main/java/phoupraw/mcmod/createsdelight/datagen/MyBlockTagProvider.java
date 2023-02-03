package phoupraw.mcmod.createsdelight.datagen;

import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import com.simibubi.create.AllBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import phoupraw.mcmod.createsdelight.registry.MyItemTags;
import phoupraw.mcmod.createsdelight.registry.MyItems;
public class MyBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public MyBlockTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(TagsRegistry.HEAT_SOURCES)
          .add(AllBlocks.LIT_BLAZE_BURNER.get());
    }
}
