package phoupraw.mcmod.createsdelight.datagen;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.simibubi.create.AllTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import phoupraw.mcmod.createsdelight.registry.CDItemTags;
import phoupraw.mcmod.createsdelight.registry.CDItems;
public final class MyItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public MyItemTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(CDItemTags.COOKED_PORK).add(CDItems.PAN_FRIED_PORK_SLICE);
        getOrCreateTagBuilder(CDItemTags.DOUGH).add(ItemsRegistry.WHEAT_DOUGH.get());
        getOrCreateTagBuilder(CDItemTags.SALT).add(CDItems.SALT);
        getOrCreateTagBuilder(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag).add(CDItems.EGG_SHELL);
    }
}
