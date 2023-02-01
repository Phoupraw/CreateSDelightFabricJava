package phoupraw.mcmod.createsdelight.rei;

import com.simibubi.create.compat.rei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.client.MinecraftClient;
import phoupraw.mcmod.createsdelight.recipe.PanFryingRecipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
public class PanFryingDisplay extends BasicDisplay implements SimpleGridMenuDisplay {
    public static List<EntryIngredient> inputsOf(PanFryingRecipe recipe) {
        var list = new ArrayList<>(EntryIngredients.ofIngredients(recipe.getIngredients()));
        for (FluidIngredient i : recipe.getFluidIngredients()) {
            EntryIngredient of = EntryIngredients.of(VanillaEntryTypes.FLUID, CreateRecipeCategory.convertToREIFluids(i.getMatchingFluidStacks()));
            list.add(of);
        }
        return list;
    }
    private int duration = 100;
    public PanFryingDisplay(PanFryingRecipe recipe) {
        super(inputsOf(recipe), Collections.singletonList(EntryIngredients.of(recipe.getOutput())), Optional.of(recipe.getId()));
        setDuration(recipe.getProcessingDuration());
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PanFryingCategory.ID;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 1;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
