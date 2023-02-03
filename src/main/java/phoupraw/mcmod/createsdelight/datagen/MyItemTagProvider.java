package phoupraw.mcmod.createsdelight.datagen;

import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import com.simibubi.create.AllBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import phoupraw.mcmod.createsdelight.registry.MyItemTags;
import phoupraw.mcmod.createsdelight.registry.MyItems;
public class MyItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public MyItemTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(MyItemTags.COOKED_PORK)
          .add(MyItems.PAN_FRIED_PORK_SLICE);
    }
}
