package phoupraw.mcmod.createsdelight.rei;

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
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
public abstract class BasinDisplay extends BasicDisplay {
    public static @NotNull List<EntryIngredient> ofAllIngredients(@NotNull ProcessingRecipe<?> recipe) {
        List<EntryIngredient> list = new LinkedList<>();
        for (Pair<Ingredient, MutableInt> condensed : ItemHelper.condenseIngredients(recipe.getIngredients())) {
            Collection<ItemConvertible> items = new LinkedList<>();
            for (ItemStack matchingStack : condensed.getFirst().getMatchingStacks()) {
                items.add(matchingStack.getItem());
            }
            list.add(EntryIngredients.ofItems(items, condensed.getSecond().getValue()));
        }
        for (FluidIngredient fluidIngredient : recipe.getFluidIngredients()) {
            list.add(PanFryingDisplay.of(fluidIngredient));
        }
        return list;
    }

    public static List<EntryIngredient> ofAllResults(ProcessingRecipe<?> recipe) {
        List<EntryIngredient> list = new LinkedList<>();
        for (ProcessingOutput rollableResult : recipe.getRollableResults()) {
            list.add(EntryIngredients.of(rollableResult.getStack().copy()));
        }
        for (FluidStack fluidResult : recipe.getFluidResults()) {
            list.add(EntryIngredients.of(dev.architectury.fluid.FluidStack.create(fluidResult.getFluid(), fluidResult.getAmount(), fluidResult.getTag())));
        }
        return list;
    }

    public final ProcessingRecipe<?> recipe;

    public BasinDisplay(ProcessingRecipe<?> recipe) {
        super(ofAllIngredients(recipe), ofAllResults(recipe), Optional.of(recipe.getId()));
        this.recipe = recipe;
    }

}
