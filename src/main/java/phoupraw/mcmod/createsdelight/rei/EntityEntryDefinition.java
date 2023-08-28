package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.rei.api.client.entry.renderer.EntryRenderer;
import me.shedaniel.rei.api.common.entry.EntrySerializer;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.comparison.ComparisonContext;
import me.shedaniel.rei.api.common.entry.type.EntryDefinition;
import me.shedaniel.rei.api.common.entry.type.EntryType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.CDEntryTypes;

import java.util.stream.Stream;
public class EntityEntryDefinition implements EntryDefinition<EntityType<?>> {
    @Environment(EnvType.CLIENT)
    public final EntryRenderer<EntityType<?>> renderer;

    @Environment(EnvType.CLIENT)
    public EntityEntryDefinition(EntryRenderer<EntityType<?>> renderer) {this.renderer = renderer;}

    @SuppressWarnings("unchecked")
    @Override
    public Class<EntityType<?>> getValueType() {
        return (Class<EntityType<?>>) (Class<?>) EntityType.class;
    }

    @Override
    public EntryType<EntityType<?>> getType() {
        return CDEntryTypes.ENTITY;
    }

    @Override
    public EntryRenderer<EntityType<?>> getRenderer() {
        return renderer;
    }

    @Override
    public @Nullable Identifier getIdentifier(EntryStack<EntityType<?>> entry, EntityType<?> value) {
        return Registries.ENTITY_TYPE.getId(value);
    }

    @Override
    public boolean isEmpty(EntryStack<EntityType<?>> entry, EntityType<?> value) {
        return false;
    }

    @Override
    public EntityType<?> copy(EntryStack<EntityType<?>> entry, EntityType<?> value) {
        return value;
    }

    @Override
    public EntityType<?> normalize(EntryStack<EntityType<?>> entry, EntityType<?> value) {
        return null;
    }

    @Override
    public EntityType<?> wildcard(EntryStack<EntityType<?>> entry, EntityType<?> value) {
        return null;
    }

    @Override
    public long hash(EntryStack<EntityType<?>> entry, EntityType<?> value, ComparisonContext context) {
        return 0;
    }

    @Override
    public boolean equals(EntityType<?> o1, EntityType<?> o2, ComparisonContext context) {
        return false;
    }

    @Override
    public @Nullable EntrySerializer<EntityType<?>> getSerializer() {
        return null;
    }

    @Override
    public Text asFormattedText(EntryStack<EntityType<?>> entry, EntityType<?> value) {
        return null;
    }

    @Override
    public Stream<? extends TagKey<?>> getTagsFor(EntryStack<EntityType<?>> entry, EntityType<?> value) {
        return null;
    }

}
