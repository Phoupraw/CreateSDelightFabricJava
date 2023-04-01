package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import phoupraw.mcmod.common.api.REICreates;
import phoupraw.mcmod.createsdelight.recipe.BakingRecipe;

import java.util.Collections;
import java.util.Optional;
public class BakingDisplay extends BasicDisplay {
    private int duration = 100;

    public BakingDisplay(BakingRecipe recipe) {
        super(REICreates.ingredientsOf(recipe), Collections.singletonList(EntryIngredients.of(recipe.getOutput())), Optional.of(recipe.getId()));
        setDuration(recipe.getProcessingDuration());
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return BakingCategory.ID;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
