package phoupraw.mcmod.createsdelight.inject;

import com.simibubi.create.compat.rei.category.CreateRecipeCategory;
import com.simibubi.create.compat.rei.category.CrushingCategory;
import com.simibubi.create.compat.rei.display.CreateDisplay;
import com.simibubi.create.content.kinetics.crusher.AbstractCrushingRecipe;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.util.collection.DefaultedList;
import phoupraw.mcmod.common.api.REICreates;

import java.util.List;
public interface InjectCrushingCategory {
    static void drawFluidSlot(CrushingCategory category, CreateDisplay<AbstractCrushingRecipe> display, Rectangle bounds, Point origin, List<Widget> widgets) {
        DefaultedList<FluidStack> fluidResults = display.getRecipe().getFluidResults();
        if (fluidResults.isEmpty()) return;
        int size = display.getRecipe().getRollableResults().size() + 1;
        int outputIndex = size - 1;
        int offset = -size * 19 / 2;
        widgets.add(REICreates.slotOf(new Point((origin.x + category.getDisplayWidth(display) / 2 + offset + 19 * outputIndex) + 1, origin.y + 78 + 1), List.of(EntryStacks.of(CreateRecipeCategory.convertToREIFluid(fluidResults.get(0)))))
          .markOutput());
    }
}
