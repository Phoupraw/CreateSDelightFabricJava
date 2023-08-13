package phoupraw.mcmod.createsdelight.recipe;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.inventory.Inventory;
import net.minecraft.world.World;
public abstract class DeprecatedMatchesRecipe extends ProcessingRecipe<Inventory> {
    public DeprecatedMatchesRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(typeInfo, params);
    }

    /**
     * 限制太大，屁用没有
     *
     * @return 总是 {@code true}
     */
    @Override
    @Deprecated
    public boolean matches(Inventory inventory, World world) {
        return false;
    }
}
