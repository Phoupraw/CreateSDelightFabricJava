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
public class SprinklingCategory implements DisplayCategory<SprinklingDisplay> {
    public static final CategoryIdentifier<SprinklingDisplay> ID = CategoryIdentifier.of(CDRecipeTypes.SPRINKLING.getId());
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
        return EntryStacks.of(CDItems.SPRINKLER);
    }

    @Override
    public int getDisplayHeight() {
        return REILayouts.calcHeight(2, 0);
    }

    @Override
    public int getDisplayWidth(SprinklingDisplay display) {
        return REILayouts.calcWidth(1, 1, 1);
    }

    @Override
    public List<Widget> setupDisplay(SprinklingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        var slot0 = REILayouts.slotAlignBackground(bounds);
        List<EntryIngredient> inputEntries = display.getInputEntries();
        widgets.add(Widgets.createSlot(slot0)
          .entries(inputEntries.get(1))
          .markInput());
        var slot1 = REILayouts.slotAlignSlot(slot0, 0, 1);
        widgets.add(Widgets.createSlot(slot1)
          .entries(inputEntries.get(0))
          .markInput());
        var arrow = REILayouts.arrowAlignSlot(slot0.getX(), slot0.getY(), slot1.getY());
        widgets.add(Widgets.createArrow(arrow));
        Point result = REILayouts.bigSlotAlignArrow(arrow);
        widgets.add(Widgets.createResultSlotBackground(result));
        widgets.add(Widgets.createSlot(result)
          .entries(display.getOutputEntries().get(0))
          .disableBackground()
          .markOutput());
        return widgets;
    }
}
