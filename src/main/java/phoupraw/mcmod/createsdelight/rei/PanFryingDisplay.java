package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import phoupraw.mcmod.common.api.REICreates;
import phoupraw.mcmod.createsdelight.recipe.PanFryingRecipe;

import java.util.Collections;
import java.util.Optional;
public class PanFryingDisplay extends BasicDisplay implements SimpleGridMenuDisplay {

    private int duration = 100;

    public PanFryingDisplay(PanFryingRecipe recipe) {
        super(REICreates.ingredientsOf(recipe), Collections.singletonList(EntryIngredients.of(recipe.getOutput())), Optional.of(recipe.getId()));
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
