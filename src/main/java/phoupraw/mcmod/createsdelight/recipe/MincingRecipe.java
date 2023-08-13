package phoupraw.mcmod.createsdelight.recipe;

import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;
public class MincingRecipe extends BasinRecipe {
    public MincingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {this(MyRecipeTypes.MINCING, params);}

    public MincingRecipe(IRecipeTypeInfo type, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(type, params);
    }
}
