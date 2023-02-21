package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.minecraft.text.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
public abstract class BasinCategory<T extends BasinDisplay> implements DisplayCategory<T> {
    @Override
    public Text getTitle() {
        return Text.translatable("category." + getCategoryIdentifier().getNamespace() + "." + getCategoryIdentifier().getPath());
    }

    @Override
    public int getDisplayHeight() {
        return 4 + 18 * 3 + 4;
    }

    @Override
    public int getDisplayWidth(T display) {
        return 4 + 18 * ((display.getInputEntries().size() + 2) / 3) + 24 + 18 * ((display.getOutputEntries().size() + 2) / 3) + 4;
    }

    @Override
    public List<Widget> setupDisplay(T display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        int x0 = bounds.getX() + 4, y0 = bounds.getY() + 4;
        List<EntryIngredient> inputs = display.getInputEntries();
        for (int i = 0; i < inputs.size(); i++) {
            widgets.add(Widgets.createSlot(new Point(x0 + 1 + 18 * (i / 3), y0 + 1 + 18 * ((i + 1) % 3))).entries(inputs.get(i)).markInput());
        }
        x0 += 18 * ((inputs.size() + 2) / 3);
        int duration = display.getDuration();
        widgets.add(Widgets.createArrow(new Point(x0, y0 + 18 + 1)).animationDurationTicks(duration));
        widgets.add(Widgets.createLabel(new Point(x0, y0 + 18 - 5), Text.translatable("category.rei.campfire.time", new DecimalFormat("###.##").format(duration / 20.0))).leftAligned());
        widgets.add(Widgets.createBurningFire(new Point(x0 + 5, y0 + 18 * 2 - 1)).animationDurationTicks(100));
        x0 += 24;
        List<EntryIngredient> outputs = display.getOutputEntries();
        for (int i = 0; i < outputs.size(); i++) {
            widgets.add(Widgets.createSlot(new Point(x0 + 1 + 18 * (i / 3), y0 + 1 + 18 * ((i + 1) % 3))).entries(outputs.get(i)).markOutput());
        }
        return widgets;
    }
}
