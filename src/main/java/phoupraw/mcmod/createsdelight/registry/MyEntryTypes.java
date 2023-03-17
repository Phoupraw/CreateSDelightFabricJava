package phoupraw.mcmod.createsdelight.registry;

import me.shedaniel.rei.api.common.entry.type.EntryType;
import net.minecraft.entity.EntityType;
public final class MyEntryTypes {
    public static final EntryType<EntityType<?>> ENTITY = EntryType.deferred(MyIdentifiers.of("entity"));

    private MyEntryTypes() {}
}
