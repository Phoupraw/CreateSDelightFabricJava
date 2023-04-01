package phoupraw.mcmod.createsdelight.api;

import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.util.Identifier;
public final class ItemStorage2 {
    public static final ItemApiLookup<Storage<ItemVariant>, ContainerItemContext> ITEM = ItemApiLookup.get(new Identifier("fabric", "item_storage"), Storage.asClass(), ContainerItemContext.class);

    private ItemStorage2() {

    }
}
