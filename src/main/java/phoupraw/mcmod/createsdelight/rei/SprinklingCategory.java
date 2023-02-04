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
public class SprinklingCategory implements DisplayCategory<SprinklingDisplay> {
    public static final CategoryIdentifier<SprinklingDisplay> ID = CategoryIdentifier.of(MyRecipeTypes.SPRINKLING.getId());
    public static final SprinklingCategory INSTANCE = new SprinklingCategory();

    @Override
    public CategoryIdentifier<? extends SprinklingDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category." + getCategoryIdentifier().getNamespace() + "." + getCategoryIdentifier().getPath());
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(MyItems.SPRINKLER);
    }

    @Override
    public int getDisplayHeight() {
        return 45;
    }

    @Override
    public int getDisplayWidth(SprinklingDisplay display) {
        return 76;
    }

    @Override
    public List<Widget> setupDisplay(SprinklingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        Point origin = new Point(bounds.getX() + 5, bounds.getY() + 5);
        List<EntryIngredient> inputEntries = display.getInputEntries();
        widgets.add(Widgets.createSlot(origin)
          .entries(inputEntries.get(1))
          .markInput());
        widgets.add(Widgets.createSlot(new Point(origin.getX(), origin.getY() + 19))
          .entries(inputEntries.get(0))
          .markInput());
        widgets.add(Widgets.createArrow(new Point(origin.getX() + 17, origin.getY() + 18)));
        Point resultPos = new Point(origin.getX() + 17 + 29, origin.getY() + 15);
        widgets.add(Widgets.createResultSlotBackground(resultPos));
        widgets.add(Widgets.createSlot(resultPos)
          .entries(display.getOutputEntries().get(0))
          .disableBackground()
          .markOutput());
        return widgets;
    }
}
