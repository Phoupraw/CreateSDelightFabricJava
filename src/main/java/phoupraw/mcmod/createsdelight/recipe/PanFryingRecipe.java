package phoupraw.mcmod.createsdelight.recipe;

import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.common.storage.BlackHoleStorage;
import phoupraw.mcmod.createsdelight.api.LambdasC;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;

import java.util.function.Predicate;

import static phoupraw.mcmod.common.misc.Lambdas.matching;
public class PanFryingRecipe extends DeprecatedMatchesRecipe{
    public static Predicate<PanFryingRecipe> testing(Storage<ItemVariant> itemS, Storage<FluidVariant> fluidS) {
        return recipe -> {
            try (var transa = Transaction.openOuter()) {
                return recipe.replace(itemS, fluidS, transa);
            }
        };
    }

    public PanFryingRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {this(MyRecipeTypes.PAN_FRYING, params);}

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
        try (var transa2 = Transaction.openNested(transa)) {
            DefaultedList<Ingredient> ingredients = getIngredients();
            if (!ingredients.isEmpty()) {
                if (StorageUtil.move(itemS, BlackHoleStorage.of(), matching(ingredients.get(0)), 1, transa2) == 0) {
                    return false;
                }
            }
            DefaultedList<FluidIngredient> fluidIngredients = getFluidIngredients();
            if (!fluidIngredients.isEmpty()) {
                FluidIngredient fluidIngredient = fluidIngredients.get(0);
                long requiredAmount = fluidIngredient.getRequiredAmount();
                if (StorageUtil.move(fluidS, BlackHoleStorage.of(), LambdasC.matching(fluidIngredient), requiredAmount, transa2) != requiredAmount) {
                    return false;
                }
            }
            ItemStack output = getOutput();
            if (!output.isEmpty()) {
                if (itemS.insert(ItemVariant.of(output), 1, transa2) != 1) {
                    return false;
                }
            }
            DefaultedList<FluidStack> fluidResults = getFluidResults();
            if (!fluidResults.isEmpty()) {
                FluidStack fluidStack = fluidResults.get(0);
                long amount = fluidStack.getAmount();
                if (fluidS.insert(fluidStack.getType(), amount, transa2) != amount) {
                    return false;
                }
            }
            transa2.commit();
            return true;
        }
    }
}
