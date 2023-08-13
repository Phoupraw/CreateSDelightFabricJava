package phoupraw.mcmod.createsdelight.recipe;

import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder.ProcessingRecipeParams;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;
public class SteamingRecipe extends DeprecatedMatchesRecipe{
    public SteamingRecipe(ProcessingRecipeParams params) {this(MyRecipeTypes.STEAMING, params);}

    public SteamingRecipe(IRecipeTypeInfo typeInfo,ProcessingRecipeParams params) {
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
