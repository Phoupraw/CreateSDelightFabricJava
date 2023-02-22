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
import phoupraw.mcmod.createsdelight.registry.MyItems;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;

import java.util.ArrayList;
import java.util.List;

import static phoupraw.mcmod.createsdelight.rei.BasinCategory.*;
public class VerticalCuttingCategory implements DisplayCategory<VerticalCuttingDisplay> {
    public static final CategoryIdentifier<VerticalCuttingDisplay> ID = CategoryIdentifier.of(MyRecipeTypes.VERTICAL_CUTTING.getId());
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
        return EntryStacks.of(MyItems.VERTICAL_CUTTER);
    }

    @Override
    public int getDisplayHeight() {
        return BasinCategory.calcHeight(1, 0);
    }

    @Override
    public int getDisplayWidth(VerticalCuttingDisplay display) {
        return BasinCategory.calcWidth(1 + display.getOutputEntries().size(), 0, 1);
    }

    @Override
    public List<Widget> setupDisplay(VerticalCuttingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        int x0 = bounds.getX() + BACKGROUND_BORDER_BREADTH, y = bounds.getY() + BACKGROUND_BORDER_BREADTH;
        widgets.add(Widgets.createSlot(new Point(x0 + 1, y)).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createArrow(new Point(x0 + SLOT_LENGTH, y)));
        widgets.add(Widgets.createLabel(new Point(x0 + SLOT_LENGTH + 8, y), Text.of(String.valueOf(display.knives))));
        List<EntryIngredient> outputEntries = display.getOutputEntries();
        for (int i = 0, outputEntriesSize = outputEntries.size(); i < outputEntriesSize; i++) {
            EntryIngredient entry = outputEntries.get(i);
            widgets.add(new DecimalCountSlot(new Point(x0 + SLOT_LENGTH + ARROW_WIDTH + 1 + SLOT_LENGTH * i, y)).withCount(display.counts[i]).entries(entry).markOutput());
        }
        return widgets;
    }
}
