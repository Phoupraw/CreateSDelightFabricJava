package phoupraw.mcmod.createsdelight.inject;

import com.simibubi.create.compat.rei.display.CreateDisplay;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipe;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.recipe.Recipe;
import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import phoupraw.mcmod.common.api.REICreates;

import java.util.LinkedList;
import java.util.List;
@ApiStatus.Internal
public final class InjectCreateDisplay {
    /**
     把{@link ProcessingRecipe}和{@link SequencedAssemblyRecipe}的多物品输出和流体输入输出也加入{@link Display}的输入输出。
     @param args {@link CreateDisplay#CreateDisplay(Recipe, CategoryIdentifier, List, List)}的参数
     @param recipe0 {@link CreateDisplay#CreateDisplay(Recipe, CategoryIdentifier)}的参数{@code recipe}
     @param id {@link CreateDisplay#CreateDisplay(Recipe, CategoryIdentifier)}的参数{@code id}
     */
    public static void improveStupidDisplay(Args args, Recipe<?> recipe0, CategoryIdentifier<CreateDisplay<Recipe<?>>> id) {
        if (recipe0 instanceof ProcessingRecipe<?> recipe) {
            args.set(2, REICreates.ingredientsOf(recipe));
            args.set(3, REICreates.resultsOf(recipe));
        } else if (recipe0 instanceof SequencedAssemblyRecipe recipe) {
            List<EntryIngredient> inputs = new LinkedList<>(EntryIngredients.ofIngredients(recipe.getIngredients()));
            List<EntryIngredient> outputs = new LinkedList<>();
            for (SequencedRecipe<?> sequencedRecipe : recipe.getSequence()) {
                inputs.addAll(REICreates.ingredientsOf(sequencedRecipe));
                outputs.addAll(REICreates.resultsOf(sequencedRecipe));
            }
            for (ProcessingOutput output : InjectSequencedAssemblyRecipe.Interface.getResultPool(recipe)) {
                outputs.add(EntryIngredients.of(output.getStack()));
            }
            args.set(2, inputs);
            args.set(3, outputs);
        }
    }

    private InjectCreateDisplay() {

    }
}
