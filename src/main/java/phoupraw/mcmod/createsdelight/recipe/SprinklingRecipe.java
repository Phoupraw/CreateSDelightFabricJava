package phoupraw.mcmod.createsdelight.recipe;

import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import phoupraw.mcmod.createsdelight.registry.CDRecipeTypes;
public class SprinklingRecipe extends DeprecatedMatchesRecipe {
    public SprinklingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        this(CDRecipeTypes.SPRINKLING, params);
    }

    public SprinklingRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
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
