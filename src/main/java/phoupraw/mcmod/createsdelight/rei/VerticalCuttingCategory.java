package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import phoupraw.mcmod.common.api.REILayouts;
import phoupraw.mcmod.createsdelight.registry.CDItems;
import phoupraw.mcmod.createsdelight.registry.CDRecipeTypes;

import java.util.ArrayList;
import java.util.List;
public class VerticalCuttingCategory implements DisplayCategory<VerticalCuttingDisplay> {
    public static final CategoryIdentifier<VerticalCuttingDisplay> ID = CategoryIdentifier.of(CDRecipeTypes.VERTICAL_CUTTING.getId());
    public static final VerticalCuttingCategory INSTANCE = new VerticalCuttingCategory();

    @Override
    public CategoryIdentifier<? extends VerticalCuttingDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category." + getCategoryIdentifier().getNamespace() + "." + getCategoryIdentifier().getPath());
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(CDItems.VERTICAL_CUTTER);
    }

    @Override
    public int getDisplayHeight() {
        return REILayouts.calcHeight(1, 0);
    }

    @Override
    public int getDisplayWidth(VerticalCuttingDisplay display) {
        return REILayouts.calcWidth(1 + display.getOutputEntries().size(), 0, 1);
    }

    @Override
    public List<Widget> setupDisplay(VerticalCuttingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        var slot0 = REILayouts.slotAlignBackground(bounds);
        widgets.add(Widgets.createSlot(slot0).entries(display.getInputEntries().get(0)).markInput());
        var arrow = REILayouts.arrowAlignSlot(slot0);
        widgets.add(Widgets.createArrow(arrow));
        widgets.add(Widgets.createLabel(new Point(arrow.getX() + 8, arrow.getY()), Text.of(String.valueOf(display.knives))));
        var slot1 = REILayouts.slotAlignArrow(arrow);
        List<EntryIngredient> outputEntries = display.getOutputEntries();
        for (int i = 0, outputEntriesSize = outputEntries.size(); i < outputEntriesSize; i++) {
            EntryIngredient entry = outputEntries.get(i);
            widgets.add(new DecimalCountSlot(REILayouts.slotAlignSlot(slot1, i, 0)).withCount(display.counts[i]).entries(entry).markOutput());
        }
        return widgets;
    }
}
