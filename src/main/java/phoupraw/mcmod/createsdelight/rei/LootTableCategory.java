package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import phoupraw.mcmod.common.rei.REILayouts;
import phoupraw.mcmod.createsdelight.registry.MyIdentifiers;

import java.util.LinkedList;
import java.util.List;
@Environment(EnvType.CLIENT)
public class LootTableCategory implements DisplayCategory<LootTableDisplay> {
    public static final CategoryIdentifier<LootTableDisplay> ID = CategoryIdentifier.of(MyIdentifiers.of("loot_table"));

    public static List<Widget> setupDisplay(LootTableCategory _this, LootTableDisplay display, Rectangle bounds) {
        List<Widget> widgets = new LinkedList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        var slot0 = REILayouts.slotAlignBackground(bounds);
        List<EntryIngredient> inputEntries = display.getInputEntries();
        for (int i = 0; i < inputEntries.size(); i++) {
            widgets.add(Widgets.createSlot(REILayouts.slotAlignSlot(slot0, i, 0)).entries(inputEntries.get(i)).markInput());
        }
        var arrow = REILayouts.arrowAlignSlot(REILayouts.slotAlignSlot(slot0, inputEntries.size(), 0));
        widgets.add(new LootTablePrinterWidget(arrow, display.lootTable));
        var slot1 = REILayouts.slotAlignArrow(arrow);
        List<EntryIngredient> outputEntries = display.getOutputEntries();
        for (int i = 0, outputEntriesSize = outputEntries.size(); i < outputEntriesSize; i++) {
            widgets.add(Widgets.createSlot(REILayouts.slotAlignSlot(slot1, i, 0)).entries(outputEntries.get(i)).markOutput());
        }
        return widgets;
    }

    @Override
    public CategoryIdentifier<? extends LootTableDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Text getTitle() {
        return Text.literal("战利品表");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(Items.CHEST);
    }

    @Override
    public int getDisplayHeight() {
        return REILayouts.calcHeight(1, 0);
    }

    @Override
    public int getDisplayWidth(LootTableDisplay display) {
        return REILayouts.calcWidth(display.getInputEntries().size() + display.getOutputEntries().size(), 0, 1);
    }

    @Override
    public List<Widget> setupDisplay(LootTableDisplay display, Rectangle bounds) {
        return setupDisplay(this, display, bounds);
    }
}
