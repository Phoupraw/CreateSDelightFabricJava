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
import phoupraw.mcmod.common.api.Internationals;
import phoupraw.mcmod.common.api.REICreates;
import phoupraw.mcmod.common.api.REILayouts;
import phoupraw.mcmod.createsdelight.registry.CDItems;
import phoupraw.mcmod.createsdelight.registry.CDRecipeTypes;

import java.util.ArrayList;
import java.util.List;
public class BakingCategory implements DisplayCategory<BakingDisplay> {
    public static final CategoryIdentifier<BakingDisplay> ID = CategoryIdentifier.of(CDRecipeTypes.BAKING.getId());
    public static final BakingCategory INSTANCE = new BakingCategory();

    @Override
    public CategoryIdentifier<? extends BakingDisplay> getCategoryIdentifier() {
        return ID;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category." + getCategoryIdentifier().getNamespace() + "." + getCategoryIdentifier().getPath());
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(CDItems.OVEN);
    }

    @Override
    public int getDisplayHeight() {
        return REILayouts.calcHeight(1, 0);
    }

    @Override
    public int getDisplayWidth(BakingDisplay display) {
        return REILayouts.calcWidth(2, 0, 1);
    }

    @Override
    public List<Widget> setupDisplay(BakingDisplay display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        Point point = REILayouts.slotAlignBackground(bounds);
        widgets.add(REICreates.slotOf(point, display.getInputEntries().get(0)).markInput());
        point = REILayouts.arrowAlignSlot(point);
        widgets.add(Widgets.createArrow(point).animationDurationTicks(display.getDuration()));
        widgets.add(Widgets.createLabel(new Point(point.getX(), point.getY() - 2), Internationals.translateToSeconds(display.getDuration())).leftAligned());
        point = REILayouts.slotAlignArrow(point);
        widgets.add(REICreates.slotOf(point, display.getOutputEntries().get(0)).markOutput());
        return widgets;
    }
}
