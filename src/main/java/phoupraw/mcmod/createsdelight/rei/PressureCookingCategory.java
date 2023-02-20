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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
public class PressureCookingCategory implements DisplayCategory<PressureCookingDisplay> {
    public static final CategoryIdentifier<PressureCookingDisplay> ID = CategoryIdentifier.of(MyRecipeTypes.PRESSURE_COOKING.getId());
    public static final PressureCookingCategory INSTANCE = new PressureCookingCategory();

    @Override
    public CategoryIdentifier<? extends PressureCookingDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category." + getCategoryIdentifier().getNamespace() + "." + getCategoryIdentifier().getPath());
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MyItems.PRESSURE_COOKER);
    }

    @Override
    public int getDisplayHeight() {
        return 4 + 18 * 3 + 4;
    }

    @Override
    public int getDisplayWidth(PressureCookingDisplay display) {
        return 4 + 18 * ((display.getInputEntries().size() + 2) / 3) + 24 + 18 * ((display.getOutputEntries().size() + 2) / 3) + 4;
    }

    @Override
    public List<Widget> setupDisplay(PressureCookingDisplay display, Rectangle bounds) {
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
