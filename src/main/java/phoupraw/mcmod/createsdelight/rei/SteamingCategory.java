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
public class SteamingCategory implements DisplayCategory<SteamingDisplay> {
    public static final CategoryIdentifier<SteamingDisplay> ID = CategoryIdentifier.of(MyRecipeTypes.STEAMING.getId());
    public static final SteamingCategory INSTANCE = new SteamingCategory();

    @Override
    public CategoryIdentifier<? extends SteamingDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category." + getCategoryIdentifier().getNamespace() + "." + getCategoryIdentifier().getPath());
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MyItems.BAMBOO_STEAMER);
    }

    @Override
    public int getDisplayHeight() {
        return 34;
    }

    @Override
    public int getDisplayWidth(SteamingDisplay display) {
        return 76;
    }

    @Override
    public List<Widget> setupDisplay(SteamingDisplay display, Rectangle bounds) {
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
