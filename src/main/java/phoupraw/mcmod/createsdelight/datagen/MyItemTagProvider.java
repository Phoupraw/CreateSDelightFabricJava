package phoupraw.mcmod.createsdelight.datagen;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.simibubi.create.AllTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import phoupraw.mcmod.createsdelight.registry.MyItemTags;
import phoupraw.mcmod.createsdelight.registry.MyItems;
public final class MyItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public MyItemTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(MyItemTags.COOKED_PORK).add(MyItems.PAN_FRIED_PORK_SLICE);
        getOrCreateTagBuilder(MyItemTags.DOUGH).add(ItemsRegistry.WHEAT_DOUGH.get());
        getOrCreateTagBuilder(MyItemTags.SALT).add(MyItems.SALT);
        getOrCreateTagBuilder(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag).add(MyItems.EGG_SHELL);
    }
}
