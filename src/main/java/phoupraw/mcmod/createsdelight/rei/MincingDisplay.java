package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import phoupraw.mcmod.createsdelight.recipe.MincingRecipe;
public class MincingDisplay extends BasinDisplay {

    public MincingDisplay(MincingRecipe recipe) {
        super(recipe);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return MincingCategory.ID;
    }

}
