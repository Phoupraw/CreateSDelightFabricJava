package phoupraw.mcmod.createsdelight.rei;

import com.simibubi.create.compat.rei.category.animations.AnimatedBlazeBurner;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.minecraft.text.Text;
import phoupraw.mcmod.common.api.REICreates;
import phoupraw.mcmod.common.api.REILayouts;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
public abstract class BasinCategory<T extends BasinDisplay> implements DisplayCategory<T> {
    public static final AnimatedBlazeBurner HEATED = new AnimatedBlazeBurner().withHeat(HeatCondition.HEATED.visualizeAsBlazeBurner());
    public static final AnimatedBlazeBurner SUPERHEATED = new AnimatedBlazeBurner().withHeat(HeatCondition.SUPERHEATED.visualizeAsBlazeBurner());

    @Override
    public Text getTitle() {
        return Text.translatable("category." + getCategoryIdentifier().getNamespace() + "." + getCategoryIdentifier().getPath());
    }

    @Override
    public int getDisplayHeight() {
        return REILayouts.calcHeight(3, 0);
    }

    /**
     根据配方的原料和产出数量动态计算格子列数。
     */
    @Override
    public int getDisplayWidth(T display) {
        return REILayouts.calcWidth((display.getInputEntries().size() + 2) / 3 + (display.getOutputEntries().size() + 2) / 3, 0, 1);
    }

    @Override
    public List<Widget> setupDisplay(T display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        var slot0 = REILayouts.slotAlignBackground(bounds);
        List<EntryIngredient> inputs = display.getInputEntries();
        for (int i = 0; i < inputs.size(); i++) {
            widgets.add(REICreates.slotOf(REILayouts.slotAlignSlot(slot0, i / 3, (i + 1) % 3), inputs.get(i)).markInput());
        }
        var arrow = REILayouts.arrowAlignSlot(REILayouts.slotAlignSlot(slot0, (inputs.size() - 1) / 3, 1));
        int duration = display.recipe.getProcessingDuration();
        widgets.add(Widgets.createArrow(arrow).animationDurationTicks(duration));
        widgets.add(Widgets.createLabel(new Point(arrow.getX(), arrow.getY() - 6), Text.translatable("category.rei.campfire.time", new DecimalFormat("###.##").format(duration / 20.0))).leftAligned());
        HeatCondition heat = display.recipe.getRequiredHeat();
        if (heat != HeatCondition.NONE) {
            widgets.add(Widgets.createDrawableWidget((helper, matrices, mouseX, mouseY, partialTick) -> {
                matrices.push();
                matrices.translate(arrow.getX(), arrow.getY() + 2, 0);
                float scale = 0.8f;
                matrices.scale(scale, scale, 1);
                (heat == HeatCondition.HEATED ? HEATED : SUPERHEATED).draw(matrices, 0, 0);
                matrices.pop();
            }));
        }
        var slot1 = REILayouts.slotAlignSlot(REILayouts.slotAlignArrow(arrow), 0, -1);
        List<EntryIngredient> outputs = display.getOutputEntries();
        for (int i = 0; i < outputs.size(); i++) {
            widgets.add(REICreates.slotOf(REILayouts.slotAlignSlot(slot1, i / 3, (i + 1) % 3), outputs.get(i)).markOutput());
        }
//        CreateRecipeCategory.addFluidTooltip(widgets, display.recipe.getFluidIngredients(), display.recipe.getFluidResults());
        return widgets;
    }

}
