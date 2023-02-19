package phoupraw.mcmod.createsdelight.api;

import com.nhoryzon.mc.farmersdelight.recipe.CuttingBoardRecipe;
import com.simibubi.create.content.contraptions.components.saw.CuttingRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Predicate;
public final class Lambdas {
    public static <T> Predicate<@Nullable T> always() {
        return t -> true;
    }

    public static <T> Predicate<@Nullable T> never() {
        return t -> false;
    }

    public static <T> Consumer<T> nothing() {
        return t -> {};
    }

    @Contract(pure = true)
    public static @NotNull Predicate<ItemVariant> matching(Ingredient predicate) {
        return v -> predicate.test(v.toStack());
    }

    @Contract(pure = true)
    public static @NotNull Predicate<FluidVariant> matching(FluidIngredient predicate) {
        return v -> predicate.test(new FluidStack(v, Long.MAX_VALUE));
    }

    /**
     * 如果配方是{@link CuttingRecipe}或{@link CuttingBoardRecipe}，则匹配工具，否则返回{@code false}
     * @param tool 工具物品，例如小刀、斧头等。
     */
    @Contract(pure = true)
    public static @NotNull Predicate<Recipe<?>> matchingTool(ItemStack tool) {
        return recipe -> {
            if (recipe instanceof CuttingRecipe cutting) return cutting.getIngredients().get(1).test(tool);
            if (recipe instanceof CuttingBoardRecipe cutting) return cutting.getTool().test(tool);
            return false;
        };
    }

    /**
     * 转换泛型而不引起额外警告。
     */
    @Contract(value = "_ -> param1", pure = true)
    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> cast(Predicate<?> predicate) {
        return (Predicate<T>) predicate;
    }
    private Lambdas() {}
}
