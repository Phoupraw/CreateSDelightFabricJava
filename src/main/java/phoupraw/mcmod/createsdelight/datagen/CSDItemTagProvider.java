package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public final class CSDItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public CSDItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        //getOrCreateTagBuilder(CDItemTags.COOKED_PORK).add(new Item[]{});
        //getOrCreateTagBuilder(CDItemTags.DOUGH).add(ItemsRegistry.WHEAT_DOUGH.get());
        //getOrCreateTagBuilder(CDItemTags.SALT).add(new Item[]{});
        //getOrCreateTagBuilder(AllTags.AllItemTags.UPRIGHT_ON_BELT.tag).add(CDItems.EGG_SHELL);

    }

}
