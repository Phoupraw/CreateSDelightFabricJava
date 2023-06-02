package phoupraw.mcmod.createsdelight.datagen;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.simibubi.create.AllTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import phoupraw.mcmod.createsdelight.registry.CDItemTags;
import phoupraw.mcmod.createsdelight.registry.CDItems;
public final class CDItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public CDItemTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(CDItemTags.COOKED_PORK).add(new Item[]{});
        getOrCreateTagBuilder(CDItemTags.DOUGH).add(ItemsRegistry.WHEAT_DOUGH.get());
        getOrCreateTagBuilder(CDItemTags.SALT).add(new Item[]{});
        getOrCreateTagBuilder(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag).add(CDItems.EGG_SHELL);
    }
}
