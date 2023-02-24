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
import phoupraw.mcmod.common.rei.REILayouts;
import phoupraw.mcmod.createsdelight.registry.MyItems;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
public class PanFryingCategory implements DisplayCategory<PanFryingDisplay> {
    public static final CategoryIdentifier<PanFryingDisplay> ID = CategoryIdentifier.of(MyRecipeTypes.PAN_FRYING.getId());
    public static final PanFryingCategory INSTANCE = new PanFryingCategory();

    @Override
    public CategoryIdentifier<? extends PanFryingDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category." + getCategoryIdentifier().getNamespace() + "." + getCategoryIdentifier().getPath());
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MyItems.PAN);
    }

    @Override
    public int getDisplayHeight() {
        return REILayouts.calcHeight(0, 1);
    }

    @Override
    public int getDisplayWidth(PanFryingDisplay display) {
        return REILayouts.calcWidth(2, 1, 1);
    }

    @Override
    public List<Widget> setupDisplay(PanFryingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        int dw = 25;
        int x = bounds.getMaxX() - dw;
        int maxY = bounds.getMaxY();
        var p3 = new Point(x, maxY - dw);
        widgets.add(Widgets.createResultSlotBackground(p3));
        widgets.add(Widgets.createSlot(p3)
          .entries(display.getOutputEntries().get(0))
          .disableBackground()
          .markOutput());
        x -= 8;
        int duration = display.getDuration();
        widgets.add(Widgets.createLabel(new Point(x - 1, bounds.getY() + 3), Text.translatable("category.rei.campfire.time", new DecimalFormat("###.##").format(duration / 20.0)))
          .noShadow()
          .rightAligned()
          .color(0xff40_4040, 0xffbb_bbbb));
        x -= 21;
        int dy = 21;
        widgets.add(Widgets.createArrow(new Point(x, maxY - dy))
          .animationDurationTicks(duration));
        x -= 17;
        List<EntryIngredient> inputEntries = display.getInputEntries();
        int size = inputEntries.size();
        for (int i = 0; i < size; i++) {
            widgets.add(Widgets.createSlot(new Point(x - 19 * i, maxY - dy))
              .entries(inputEntries.get(size - 1 - i))
              .markInput());
        }
        return widgets;
    }
}
