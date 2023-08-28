package phoupraw.mcmod.createsdelight.datagen;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.simibubi.create.AllTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;
import phoupraw.mcmod.createsdelight.registry.CDItemTags;
import phoupraw.mcmod.createsdelight.registry.CDItems;

import java.util.concurrent.CompletableFuture;

public final class CDItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public CDItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(CDItemTags.COOKED_PORK).add(new Item[]{});
        getOrCreateTagBuilder(CDItemTags.DOUGH).add(ItemsRegistry.WHEAT_DOUGH.get());
        getOrCreateTagBuilder(CDItemTags.SALT).add(new Item[]{});
        getOrCreateTagBuilder(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag).add(CDItems.EGG_SHELL);

    }

}
