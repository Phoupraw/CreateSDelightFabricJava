package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import phoupraw.mcmod.createsdelight.recipe.SteamingRecipe;

import java.util.Collections;
import java.util.Optional;
public class SteamingDisplay extends BasicDisplay implements SimpleGridMenuDisplay {
    private int duration = 100;
    public SteamingDisplay(SteamingRecipe recipe) {
        super(EntryIngredients.ofIngredients(recipe.getIngredients()), Collections.singletonList(EntryIngredients.of(recipe.getOutput())), Optional.of(recipe.getId()));
        setDuration(recipe.getProcessingDuration());
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SteamingCategory.ID;
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
