package phoupraw.mcmod.createsdelight.recipe;

import com.simibubi.create.content.contraptions.components.deployer.DeployerRecipeSearchEvent;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.CDItems;
import phoupraw.mcmod.createsdelight.storage.IronBowlItemStorage;
public class ItemBowlRecipe implements Recipe<Inventory> {
    public final DeployerRecipeSearchEvent event;

    public ItemBowlRecipe(DeployerRecipeSearchEvent event) {
        this.event = event;
    }

    @Override
    public boolean matches(Inventory inventory, World world) {
        return false;
    }

    @Override
    public ItemStack craft(Inventory inventory) {
        return getOutput();
    }

    @Override
    public boolean fits(int width, int height) {
        return false;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return DefaultedList.copyOf(Ingredient.empty(), Ingredient.ofItems(CDItems.IRON_BOWL), Ingredient.ofStacks(event.getInventory().getStack(1)));
    }

    @Override
    public ItemStack getOutput() {
        var itemS = new IronBowlItemStorage(ContainerItemContext.ofSingleSlot(InventoryStorage.of(event.getInventory(), null).getSlot(0)), event.getTileEntity().getWorld());
        TransferUtil.insertItem(itemS, event.getInventory().getStack(1));
        return event.getInventory().getStack(0).copy();
    }

    @Override
    public @Nullable Identifier getId() {
        return null;
    }

    @Override
    public @Nullable RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public @Nullable RecipeType<?> getType() {
        return null;
    }
}
