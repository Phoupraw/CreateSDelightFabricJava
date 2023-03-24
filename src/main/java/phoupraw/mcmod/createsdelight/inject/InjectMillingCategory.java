package phoupraw.mcmod.createsdelight.inject;

import com.simibubi.create.compat.rei.category.CreateRecipeCategory;
import com.simibubi.create.compat.rei.display.CreateDisplay;
import com.simibubi.create.content.contraptions.components.crusher.AbstractCrushingRecipe;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.collection.DefaultedList;
import phoupraw.mcmod.common.api.REICreates;

import java.util.List;
@Environment(EnvType.CLIENT)
public interface InjectMillingCategory {
    static void drawFluidSlot(CreateDisplay<AbstractCrushingRecipe> display, Rectangle bounds, Point origin, List<Widget> widgets) {
        DefaultedList<FluidStack> fluidResults = display.getRecipe().getFluidResults();
        if (fluidResults.isEmpty()) return;
        int outputIndex = display.getRecipe().getRollableResults().size();
        int xOffset = outputIndex % 2 == 0 ? 0 : 19;
        int yOffset = (outputIndex / 2) * -19;
        widgets.add(REICreates.slotOf(new Point((origin.x + 133 + xOffset) + 1, (origin.y + 27 + yOffset) + 1), List.of(EntryStacks.of(CreateRecipeCategory.convertToREIFluid(fluidResults.get(0)))))
          .markOutput());
    }
}
