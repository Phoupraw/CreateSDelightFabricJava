package phoupraw.mcmod.createsdelight.registry;

import me.shedaniel.rei.api.common.entry.type.EntryType;
import net.minecraft.entity.EntityType;

public final class CDEntryTypes {

    public static final EntryType<EntityType<?>> ENTITY = EntryType.deferred(CSDIdentifiers.of("entity"));

    private CDEntryTypes() {
    }

}
