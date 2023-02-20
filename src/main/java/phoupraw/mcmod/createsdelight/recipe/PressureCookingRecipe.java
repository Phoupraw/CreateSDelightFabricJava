package phoupraw.mcmod.createsdelight.recipe;

import com.simibubi.create.content.contraptions.processing.BasinRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;
public class PressureCookingRecipe extends BasinRecipe {
    public PressureCookingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {this(MyRecipeTypes.PRESSURE_COOKING, params);}

    public PressureCookingRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(typeInfo, params);
    }
}
