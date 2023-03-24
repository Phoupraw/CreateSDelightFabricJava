package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.contraptions.components.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.contraptions.components.millstone.MillingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import org.spongepowered.asm.mixin.Mixin;
@Mixin(MillingRecipe.class)
public abstract class MixinMillingRecipe extends AbstractCrushingRecipe {
    public MixinMillingRecipe(IRecipeTypeInfo recipeType, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(recipeType, params);
    }

    @Override
    protected int getMaxFluidOutputCount() {
        return 1;
    }
}
