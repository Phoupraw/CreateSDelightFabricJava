package phoupraw.mcmod.createsdelight.exp;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.LinkedList;
public final class ItemReferences {
    public static final Collection<ItemReference> ALL = new LinkedList<>();
    static {
        ServerTickEvents.START_SERVER_TICK.register(server -> ALL.parallelStream().<Pair<ItemReference, ItemVariant>>mapMulti((itemReference, consumer) -> {
            var reaction = ItemReaction.REACTIONS.find(itemReference.getVariant().toStack(), itemReference);
            if (reaction == null) return;
            consumer.accept(Pair.of(itemReference, reaction.apply(itemReference)));
        }).toList().parallelStream().forEach(pair -> pair.getLeft().setVariant(pair.getRight())));
    }
    private ItemReferences() {}
}
