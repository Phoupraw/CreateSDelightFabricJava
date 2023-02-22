package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import phoupraw.mcmod.createsdelight.registry.MyItems;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
public class GrillingCategory implements DisplayCategory<GrillingDisplay> {
    public static final CategoryIdentifier<GrillingDisplay> ID = CategoryIdentifier.of(MyRecipeTypes.GRILLING.getId());
    public static final GrillingCategory INSTANCE = new GrillingCategory();

    @Override
    public CategoryIdentifier<? extends GrillingDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category." + getCategoryIdentifier().getNamespace() + "." + getCategoryIdentifier().getPath());
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MyItems.GRILL);
    }

    @Override
    public int getDisplayHeight() {
        return BasinCategory.calcHeight(0, 1);
    }

    @Override
    public int getDisplayWidth(GrillingDisplay display) {
        return BasinCategory.calcWidth(1, 1, 1);
    }

    @Override
    public List<Widget> setupDisplay(GrillingDisplay display, Rectangle bounds) {
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
        widgets.add(Widgets.createSlot(new Point(x  , maxY - dy))
          .entries(display.getInputEntries().get(0))
          .markInput());
        return widgets;
    }
}
