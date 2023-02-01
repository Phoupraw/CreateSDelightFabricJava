package phoupraw.mcmod.createsdelight.storage;

import com.nhoryzon.mc.farmersdelight.entity.block.StoveBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.InsertionOnlyStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeType;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.api.FarmersDelightWrappers;

import java.util.List;
import java.util.Objects;
/**
 * @see FarmersDelightWrappers#storageOf(StoveBlockEntity)
 */
public class StoveStorage extends SnapshotParticipant<List<ItemVariant>> implements InsertionOnlyStorage<ItemVariant> {

    private final StoveBlockEntity stove;

    public StoveStorage(StoveBlockEntity stove) {this.stove = stove;}

    @Override
    protected List<ItemVariant> createSnapshot() {
        return stove.getInventory().stream().map(ItemVariant::of).toList();
    }

    @Override
    protected void readSnapshot(List<ItemVariant> snapshot) {
        for (int i = 0; i < snapshot.size(); i++) stove.getInventory().set(i, snapshot.get(i).toStack());
    }

    @Override
    public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        var recipe = findRecipe(resource);
        if (recipe == null) return 0;
        updateSnapshots(transaction);
        long amount = 0;
        while (amount < maxAmount && stove.addItem(resource.toStack(), recipe.getCookTime())) amount++;
        return amount;
    }

    public @Nullable CampfireCookingRecipe findRecipe(ItemVariant resource) {
        return FarmersDelightWrappers.findRecipe(StoveStorage.class, Objects.requireNonNull(stove.getWorld()), RecipeType.CAMPFIRE_COOKING, resource);
    }
}
