package phoupraw.mcmod.createsdelight.rei;

import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.util.Identifier;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
public abstract class BasinDisplay extends BasicDisplay {
    public static List<EntryIngredient> ofAllIngredients(ProcessingRecipe<?> recipe) {
        List<EntryIngredient> list = new LinkedList<>(EntryIngredients.ofIngredients(recipe.getIngredients()));
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

    private int duration = 100;

    public BasinDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, @SuppressWarnings("OptionalUsedAsFieldOrParameterType") Optional<Identifier> location) {super(inputs, outputs, location);}

    public BasinDisplay(ProcessingRecipe<?> recipe) {
        this(ofAllIngredients(recipe), ofAllResults(recipe), Optional.of(recipe.getId()));
        setDuration(recipe.getProcessingDuration());
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
