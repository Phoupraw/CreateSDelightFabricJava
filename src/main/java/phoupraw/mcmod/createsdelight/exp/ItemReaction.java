package phoupraw.mcmod.createsdelight.exp;

import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import phoupraw.mcmod.createsdelight.registry.CDIdentifiers;

import java.util.function.Function;
public interface ItemReaction extends Function<ItemReference, ItemVariant> {
    ItemApiLookup<ItemReaction, ItemReference> REACTIONS = ItemApiLookup.get(CDIdentifiers.of("reactions"), ItemReaction.class, ItemReference.class);
    @Override
    ItemVariant apply(ItemReference itemReference);
}
