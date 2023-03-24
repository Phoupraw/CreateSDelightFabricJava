package phoupraw.mcmod.createsdelight.exp;

import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.common.api.BlackHoleStorage;
import phoupraw.mcmod.common.api.Lambdas;
import phoupraw.mcmod.createsdelight.api.LambdasC;
public final class RecipeOperations {
    /**
     对于无序配方{@code recipe}，尝试从{@code itemInputS}抽取物品原料，从{@code fluidInputS}抽取流体原料，向{@code itemOutputS}输出物品结果，向{@code fluidOutputS}输出流体结果。
     @param itemInputS 物品原料来源，允许与{@code itemOutputS}相同。
     @param fluidInputS 流体原料来源，允许与{@code fluidOutputS}相同。
     @param itemOutputS 物品结果存放处，允许与{@code itemInputS}相同。
     @param fluidOutputS 流体结果存放处，允许与{@code fluidInputS}相同。
     @param recipe 配方
     @param transa 如果为{@code null}，则此方法产生的效果立刻生效，不可撤销。
     @return 如果四个环节全部成功，则返回{@code true}，否则返回{@code false}。
     */
    @Contract(mutates = "param1,param2,param3,param4,param6")
    public static boolean replace(@NotNull Storage<ItemVariant> itemInputS, @NotNull Storage<FluidVariant> fluidInputS, @NotNull Storage<ItemVariant> itemOutputS, @NotNull Storage<FluidVariant> fluidOutputS, @NotNull ProcessingRecipe<?> recipe, @Nullable TransactionContext transa) {
        try (var transa2 = Transaction.openNested(transa)) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                if (StorageUtil.move(itemInputS, BlackHoleStorage.of(), Lambdas.matching(ingredient), 1, transa2) != 1) {
                    return false;
                }
            }
            for (FluidIngredient fluidIngredient : recipe.getFluidIngredients()) {
                long amount = fluidIngredient.getRequiredAmount();
                if (StorageUtil.move(fluidInputS, BlackHoleStorage.of(), LambdasC.matching(fluidIngredient), amount, transa2) != amount) {
                    return false;
                }
            }
            for (ItemStack itemStack : recipe.rollResults()) {
                if (itemOutputS.insert(ItemVariant.of(itemStack), itemStack.getCount(), transa2) != itemStack.getCount()) {
                    return false;
                }
            }
            for (FluidStack fluidStack : recipe.getFluidResults()) {
                if (fluidOutputS.insert(fluidStack.getType(), fluidStack.getAmount(), transa2) != fluidStack.getAmount()) {
                    return false;
                }
            }
            transa2.commit();
            return true;
        }
    }

    private RecipeOperations() {}
}
