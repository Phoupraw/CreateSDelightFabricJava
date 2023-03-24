package phoupraw.mcmod.createsdelight.rei;

import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import phoupraw.mcmod.common.api.REICreates;

import java.util.Optional;
public abstract class BasinDisplay extends BasicDisplay {
    public final ProcessingRecipe<?> recipe;

    public BasinDisplay(ProcessingRecipe<?> recipe) {
        super(REICreates.ingredientsOf(recipe), REICreates.resultsOf(recipe), Optional.of(recipe.getId()));
        this.recipe = recipe;
    }

}
