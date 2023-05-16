package phoupraw.mcmod.createsdelight.recipe;

import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import phoupraw.mcmod.createsdelight.registry.CDRecipeTypes;
public class SteamingRecipe extends DeprecatedMatchesRecipe{
    public SteamingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        this(CDRecipeTypes.STEAMING, params);
    }

    public SteamingRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(typeInfo, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 1;
    }
}
