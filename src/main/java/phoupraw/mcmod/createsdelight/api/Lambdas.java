package phoupraw.mcmod.createsdelight.api;

import com.simibubi.create.foundation.fluid.FluidIngredient;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.minecraft.recipe.Ingredient;
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

    public static Predicate<ItemVariant> of(Ingredient predicate) {
        return v -> predicate.test(v.toStack());
    }

    public static Predicate<FluidVariant> of(FluidIngredient predicate) {
        return v -> predicate.test(new FluidStack(v, Long.MAX_VALUE));
    }

    private Lambdas() {}
}
