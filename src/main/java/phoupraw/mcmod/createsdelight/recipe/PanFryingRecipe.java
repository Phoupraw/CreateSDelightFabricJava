package phoupraw.mcmod.createsdelight.recipe;

import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.exp.RecipeOperations;
import phoupraw.mcmod.createsdelight.registry.CDRecipeTypes;

import java.util.function.Predicate;
public class PanFryingRecipe extends DeprecatedMatchesRecipe {
    public static Predicate<PanFryingRecipe> testing(Storage<ItemVariant> itemS, Storage<FluidVariant> fluidS) {
        return recipe -> {
            try (var transa = Transaction.openOuter()) {
                return recipe.replace(itemS, fluidS, transa);
            }
        };
    }

    public PanFryingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        this(CDRecipeTypes.PAN_FRYING, params);
    }

    public PanFryingRecipe(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(typeInfo, params);
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

    public boolean replace(@NotNull Storage<ItemVariant> itemS, @NotNull Storage<FluidVariant> fluidS, @Nullable TransactionContext transa) {
        return RecipeOperations.replace(itemS, fluidS, itemS, fluidS, this, transa);
    }
}
