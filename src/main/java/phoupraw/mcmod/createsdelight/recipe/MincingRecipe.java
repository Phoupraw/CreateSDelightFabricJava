package phoupraw.mcmod.createsdelight.recipe;

import com.simibubi.create.content.contraptions.processing.BasinRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import phoupraw.mcmod.createsdelight.registry.CDRecipeTypes;
public class MincingRecipe extends BasinRecipe {
    public MincingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        this(CDRecipeTypes.MINCING, params);
    }

    public MincingRecipe(IRecipeTypeInfo type, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(type, params);
    }
}
