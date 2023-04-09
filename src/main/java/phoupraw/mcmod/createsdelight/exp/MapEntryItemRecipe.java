package phoupraw.mcmod.createsdelight.exp;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.Map;
public abstract class MapEntryItemRecipe extends BasicRecipe implements Map.Entry<Item, Item> {
    private final Item key;
    private final Item value;

    protected MapEntryItemRecipe(Identifier id, Item key, Item value) {
        super(id);
        this.key = key;
        this.value = value;
    }

    @Override
    public Item getKey() {
        return key;
    }

    @Override
    public Item getValue() {
        return value;
    }

    @Override
    public Item setValue(Item value) {
        throw new UnsupportedOperationException();
    }
}
