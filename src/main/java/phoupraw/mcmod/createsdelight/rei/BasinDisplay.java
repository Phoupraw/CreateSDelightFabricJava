package phoupraw.mcmod.createsdelight.rei;

import com.simibubi.create.content.contraptions.itemAssembly.SequencedRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.item.ItemHelper;
import com.simibubi.create.foundation.utility.Pair;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.collection.DefaultedList;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
public abstract class BasinDisplay extends BasicDisplay {
    public static @NotNull List<EntryIngredient> ingredientsOf(@NotNull ProcessingRecipe<?> recipe) {
        return ingredientsOf(recipe.getIngredients(), recipe.getFluidIngredients());
    }

    public static @NotNull List<EntryIngredient> ingredientsOf(@NotNull SequencedRecipe<?> recipe0) {
        var recipe = recipe0.getRecipe();
        DefaultedList<Ingredient> ingredients = DefaultedList.of();
        ingredients.addAll(recipe.getIngredients());
        ingredients.remove(0);
        return ingredientsOf(ingredients, recipe.getFluidIngredients());
    }

    public static @NotNull List<EntryIngredient> ingredientsOf(@NotNull DefaultedList<Ingredient> ingredients, @NotNull DefaultedList<FluidIngredient> fluidIngredients) {
        List<EntryIngredient> list = new LinkedList<>();
        for (Pair<Ingredient, MutableInt> condensed : ItemHelper.condenseIngredients(ingredients)) {
            Collection<ItemConvertible> items = new LinkedList<>();
            for (ItemStack matchingStack : condensed.getFirst().getMatchingStacks()) {
                items.add(matchingStack.getItem());
            }
            list.add(EntryIngredients.ofItems(items, condensed.getSecond().getValue()));
        }
        for (FluidIngredient fluidIngredient : fluidIngredients) {
            list.add(PanFryingDisplay.of(fluidIngredient));
        }
        return list;
    }

    public static List<EntryIngredient> resultsOf(ProcessingRecipe<?> recipe) {
        return resultsOf(recipe.getRollableResults(), recipe.getFluidResults());
    }

    public static List<EntryIngredient> resultsOf(@NotNull SequencedRecipe<?> recipe0) {
        var recipe = recipe0.getRecipe();
        var rollableResults = new LinkedList<>(recipe.getRollableResults());
        rollableResults.remove(0);
        return resultsOf(rollableResults, recipe.getFluidResults());
    }

    public static List<EntryIngredient> resultsOf(List<ProcessingOutput> rollableResults, DefaultedList<FluidStack> fluidResults) {
        List<EntryIngredient> list = new LinkedList<>();
        for (ProcessingOutput rollableResult : rollableResults) {
            list.add(EntryIngredients.of(rollableResult.getStack().copy()));
        }
        for (FluidStack fluidResult : fluidResults) {
            list.add(EntryIngredients.of(dev.architectury.fluid.FluidStack.create(fluidResult.getFluid(), fluidResult.getAmount(), fluidResult.getTag())));
        }
        return list;
    }

    public final ProcessingRecipe<?> recipe;

    public BasinDisplay(ProcessingRecipe<?> recipe) {
        super(ingredientsOf(recipe), resultsOf(recipe), Optional.of(recipe.getId()));
        this.recipe = recipe;
    }

}
