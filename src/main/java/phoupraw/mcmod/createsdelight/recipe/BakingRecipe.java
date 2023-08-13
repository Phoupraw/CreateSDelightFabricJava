package phoupraw.mcmod.createsdelight.recipe;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.recipe.RecipeConditions;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import net.minecraft.recipe.Recipe;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;

import java.util.function.Predicate;
public class BakingRecipe extends DeprecatedMatchesRecipe {
    public static @Nullable BakingRecipe findFirst(@NotNull World world, @NotNull Predicate<Recipe<?>> predicate) {
        return (BakingRecipe) RecipeFinder.get(BakingRecipe.class, world, RecipeConditions.isOfType(MyRecipeTypes.BAKING.getRecipeType())).stream().filter(predicate).findFirst().orElse(null);
    }

    public BakingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        this(MyRecipeTypes.BAKING, params);
    }

    public BakingRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(typeInfo, params);
        if (getIngredients().size() + getFluidIngredients().size() > 1) {
            throw new IllegalArgumentException("getIngredients().size() + getFluidIngredients().size() > 1");
        }
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 1;
    }

    @Override
    protected int getMaxFluidInputCount() {
        return 1;
    }

    @Override
    protected int getMaxFluidOutputCount() {
        return 0;
    }
}
