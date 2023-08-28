package phoupraw.mcmod.createsdelight.storage;

import com.nhoryzon.mc.farmersdelight.entity.block.SkilletBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import phoupraw.mcmod.createsdelight.api.FarmersDelightWrappers;

import java.util.Objects;
/**
 @see FarmersDelightWrappers#storageOf(SkilletBlockEntity) */
public class SkilletStorage extends SingleStackStorage {
    private final SkilletBlockEntity skillet;

    public SkilletStorage(SkilletBlockEntity skillet) {this.skillet = skillet;}

    @Override
    protected ItemStack getStack() {
        return skillet.getStoredStack();
    }

    @Override
    protected void setStack(ItemStack stack) {
        skillet.setStack(0, stack);
    }

    @Override
    protected boolean canInsert(ItemVariant itemVariant) {
        return FarmersDelightWrappers.findRecipe(SkilletStorage.class, Objects.requireNonNull(skillet.getWorld()), RecipeType.CAMPFIRE_COOKING, itemVariant) != null;
    }

    @Override
    protected void onFinalCommit() {
        super.onFinalCommit();
        var itemStack = getStack();
        setStack(ItemStack.EMPTY);
        skillet.addItemToCook(itemStack, null);
    }
}
