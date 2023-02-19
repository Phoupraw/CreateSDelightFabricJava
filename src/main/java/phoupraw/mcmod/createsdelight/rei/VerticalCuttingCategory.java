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
        return 26;
    }

    @Override
    public int getDisplayWidth(VerticalCuttingDisplay display) {
        return 138;
    }

    @Override
    public List<Widget> setupDisplay(VerticalCuttingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        int x0 = bounds.getX() + 5, y0 = bounds.getY() + 5;
        widgets.add(Widgets.createSlot(new Point(x0, y0)).entries(display.getInputEntries().get(0)).markInput());
        widgets.add(Widgets.createArrow(new Point(x0 + 17, y0)));
        widgets.add(Widgets.createLabel(new Point(x0 + 25, y0), Text.of(String.valueOf(display.knives))));
        List<EntryIngredient> outputEntries = display.getOutputEntries();
        for (int i = 0, outputEntriesSize = outputEntries.size(); i < outputEntriesSize; i++) {
            EntryIngredient entry = outputEntries.get(i);
            widgets.add(new DecimalCountSlot(new Point(x0 + 17 + 25 + 18 * i, y0)).withCount(display.counts[i]).entries(entry).markOutput());
//            widgets.add(new DecimalCountSlot(new Point(x0 + 17 + 25 + 18 * i + 14, y0 + 9)).withCount(display.counts[i]));
        }
        return widgets;
    }
}
