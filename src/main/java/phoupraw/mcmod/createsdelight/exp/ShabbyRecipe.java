package phoupraw.mcmod.createsdelight.exp;

import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.world.World;
public interface ShabbyRecipe extends Recipe<Inventory> {

    @Override
    default boolean matches(Inventory inventory, World world) {
        return false;
    }
    //@Override
    //default ItemStack craft(Inventory inventory) {
    //    return ItemStack.EMPTY;
    //}
    @Override
    default boolean fits(int width, int height) {
        return false;
    }
    //@Override
    //default ItemStack getOutput() {
    //    return ItemStack.EMPTY;
    //}
}
