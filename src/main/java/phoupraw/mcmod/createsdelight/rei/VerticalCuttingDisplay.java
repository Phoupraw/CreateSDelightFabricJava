package phoupraw.mcmod.createsdelight.rei;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import phoupraw.mcmod.createsdelight.recipe.VerticalCuttingRecipe;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
public class VerticalCuttingDisplay extends BasicDisplay implements SimpleGridMenuDisplay {
    public static List<EntryIngredient> of(List<ProcessingOutput> rollableResults) {
        List<EntryIngredient> list = new LinkedList<>();
        for (ProcessingOutput rollableResult : rollableResults) {
            var s = rollableResult.getStack().copy();
            s.setCount(1);
            list.add(EntryIngredients.of(s));
        }
        return list;
    }

    public int knives;
    /**
     * 考虑了概率的数量
     */
    public double[] counts;

    public VerticalCuttingDisplay(VerticalCuttingRecipe recipe) {
        super(EntryIngredients.ofIngredients(recipe.getIngredients()), of(recipe.getRollableResults()), Optional.of(recipe.getId()));
        knives = recipe.getKnives();
        List<ProcessingOutput> rollableResults = recipe.getRollableResults();
        counts = new double[rollableResults.size()];
        for (int i = 0; i < rollableResults.size(); i++) {
            ProcessingOutput output = rollableResults.get(i);
            counts[i] = output.getStack().getCount() * output.getChance();
        }
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return VerticalCuttingCategory.ID;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    @Override
    public int getHeight() {
        return 1;
    }
}
