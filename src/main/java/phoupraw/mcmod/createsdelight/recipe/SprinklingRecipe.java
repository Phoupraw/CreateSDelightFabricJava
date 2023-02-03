package phoupraw.mcmod.createsdelight.recipe;

import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import net.minecraft.inventory.Inventory;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;
public class SprinklingRecipe extends ProcessingRecipe<Inventory> {
    public SprinklingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {this(MyRecipeTypes.SPRINKLING, params);}

    public SprinklingRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
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

    @Override
    protected int getMaxInputCount() {
        return 2;
    }

    @Override
    protected int getMaxOutputCount() {
        return 1;
    }
}
