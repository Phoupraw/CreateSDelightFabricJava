package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import phoupraw.mcmod.createsdelight.recipe.SprinklingRecipe;

import java.util.Collections;
import java.util.Optional;
public class SprinklingDisplay extends BasicDisplay implements SimpleGridMenuDisplay {
    public SprinklingDisplay(SprinklingRecipe recipe) {
        super(EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getOutput())), Optional.of(recipe.getId()));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SprinklingCategory.ID;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 1;
    }
}
