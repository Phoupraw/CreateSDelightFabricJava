package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.kinetics.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
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
