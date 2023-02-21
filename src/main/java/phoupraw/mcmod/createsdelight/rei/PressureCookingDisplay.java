package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import phoupraw.mcmod.createsdelight.recipe.PressureCookingRecipe;
public class PressureCookingDisplay extends BasinDisplay {

    public PressureCookingDisplay(PressureCookingRecipe recipe) {
        super(recipe);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return PressureCookingCategory.ID;
    }

}
