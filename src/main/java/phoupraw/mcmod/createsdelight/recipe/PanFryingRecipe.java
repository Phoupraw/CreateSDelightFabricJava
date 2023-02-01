package phoupraw.mcmod.createsdelight.recipe;

import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import net.minecraft.inventory.Inventory;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.block.entity.PanBlockEntity;
import phoupraw.mcmod.createsdelight.block.entity.MyBlockEntity1;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;

import java.util.function.Predicate;
public class PanFryingRecipe extends ProcessingRecipe<Inventory> implements Predicate<PanBlockEntity> {
    public PanFryingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {this(MyRecipeTypes.PAN_FRYING, params);}

    public PanFryingRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
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
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 1;
    }

    @Override
    protected int getMaxFluidInputCount() {
        return 1;
    }

    @Override
    protected int getMaxFluidOutputCount() {
        return 1;
    }

    /**
     * @see PanBlockEntity#test(PanFryingRecipe)
     * @param pan the input argument
     * @return
     */
    @Override
    public boolean test(PanBlockEntity pan) {
        return (getIngredients().isEmpty() || getIngredients().get(0).test(pan.getItem().getStorage().getStack())) && (getFluidIngredients().isEmpty() || getFluidIngredients().get(0).test(pan.getTank().getPrimaryHandler().getFluid()));
    }
}
