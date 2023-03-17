package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.CombinedEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.entry.TagEntry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
public class LootTableDisplay extends BasicDisplay {
    public static List<EntryIngredient> inputsOf(@Nullable Object owner, LootTable lootTable) {
        List<EntryIngredient> inputs = new LinkedList<>();
        if (owner instanceof Block block) {
            if (block.asItem() != Items.AIR) {
                inputs.add(EntryIngredients.of(block));
            }
        }
        return inputs;
    }

    public static List<EntryIngredient> outputsOf(LootTable lootTable) {
        List<EntryIngredient> outputs = new LinkedList<>();
        for (LootPool pool : lootTable.pools) {
            addTo(outputs, pool);
        }
        return outputs;
    }

    public static void addTo(List<EntryIngredient> outputs, LootPool pool) {
        for (LootPoolEntry entry : pool.entries) {
            addTo(outputs, entry);
        }
    }

    public static void addTo(List<EntryIngredient> outputs, LootPoolEntry entry) {
        if (entry instanceof ItemEntry itemEntry) {
            addTo(outputs, itemEntry);
        } else if (entry instanceof TagEntry tagEntry) {
            addTo(outputs, tagEntry);
        } else if (entry instanceof CombinedEntry combinedEntry) {
            addTo(outputs, combinedEntry);
        }
    }

    public static void addTo(List<EntryIngredient> outputs, ItemEntry entry) {
        outputs.add(EntryIngredients.of(entry.item));
    }

    public static void addTo(List<EntryIngredient> outputs, TagEntry entry) {
        outputs.add(EntryIngredients.ofItemTag(entry.name));
    }

    public static void addTo(List<EntryIngredient> outputs, CombinedEntry entry) {
        for (LootPoolEntry child : entry.children) {
            addTo(outputs, child);
        }
    }

    public static LootTableDisplay of(@Nullable Object owner, LootTable lootTable) {
        return new LootTableDisplay(inputsOf(owner, lootTable), outputsOf(lootTable), Optional.of(lootTable.getLootTableId()), owner, lootTable);
    }

    public static LootTableDisplay of(NbtCompound nbt) {
        Object owner = null;
        if (nbt.contains("owner", NbtElement.COMPOUND_TYPE)) {
            NbtCompound ownerNbt = nbt.getCompound("owner");
            if (ownerNbt.getString("type").equals("block")) {
                owner = Registry.BLOCK.get(new Identifier(ownerNbt.getString("id")));
            }
        }
        LootTable lootTable = LootManager.GSON.fromJson(nbt.getString("lootTableJson"), LootTable.class);
        return of(owner, lootTable);
    }

    public final Object owner;
    public final LootTable lootTable;

    public LootTableDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, @SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<Identifier> location, @Nullable Object owner, LootTable lootTable) {
        super(inputs, outputs, location);
        this.owner = owner;
        this.lootTable = lootTable;
    }


    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return LootTableCategory.ID;
    }
}
