package phoupraw.mcmod.createsdelight.recipe;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;
public class SprinklingRecipe extends DeprecatedMatchesRecipe {
    public SprinklingRecipe(ProcessingRecipeParams params) {this(MyRecipeTypes.SPRINKLING, params);}

    public SprinklingRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeParams params) {
        super(typeInfo, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 2;
    }

    @Override
    protected int getMaxOutputCount() {
        return 1;
    }
}
