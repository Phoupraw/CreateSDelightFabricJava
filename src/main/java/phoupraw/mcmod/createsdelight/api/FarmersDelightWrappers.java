package phoupraw.mcmod.createsdelight.api;

import com.nhoryzon.mc.farmersdelight.block.BasketBlock;
import com.nhoryzon.mc.farmersdelight.entity.block.BasketBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.CuttingBoardBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.SkilletBlockEntity;
import com.nhoryzon.mc.farmersdelight.entity.block.StoveBlockEntity;
import com.simibubi.create.foundation.utility.recipe.RecipeConditions;
import com.simibubi.create.foundation.utility.recipe.RecipeFinder;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.storage.SkilletStorage;
import phoupraw.mcmod.createsdelight.storage.StoveStorage;
/**
 * 为农夫乐事的方块包装成更通用的API，以及一些实用方法。
 */
public class FarmersDelightWrappers {
    public static Storage<ItemVariant> storageOf(StoveBlockEntity stove) {
        return new StoveStorage(stove);
    }

    public static Storage<ItemVariant> storageOf(SkilletBlockEntity skillet) {
        return new SkilletStorage(skillet);
    }

    public static Storage<ItemVariant> storageOf(CuttingBoardBlockEntity board) {
        return InventoryStorage.of(board.getInventory(), null);
    }

    public static Storage<ItemVariant> sidedStorageOf(BasketBlockEntity basket, Direction side) {
        return side != basket.getCachedState().get(BasketBlock.FACING) ? Storage.empty() : InventoryStorage.of(basket, null);
    }

    @SuppressWarnings("unchecked")
    public static <T extends AbstractCookingRecipe> @Nullable T findRecipe(Object cacheKey, @NotNull World world, RecipeType<T> type, ItemVariant resource) {
        if (resource.isBlank()) return null;
        return (T) RecipeFinder.get(cacheKey, world, RecipeConditions.isOfType(type)).parallelStream().filter(RecipeConditions.firstIngredientMatches(resource.toStack())).findFirst().orElse(null);
    }
}
