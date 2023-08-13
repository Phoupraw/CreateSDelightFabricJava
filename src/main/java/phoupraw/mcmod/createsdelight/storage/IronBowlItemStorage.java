package phoupraw.mcmod.createsdelight.storage;

import com.simibubi.create.foundation.recipe.RecipeConditions;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantItemStorage;
import net.minecraft.nbt.NbtElement;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.recipe.BakingRecipe;
public class IronBowlItemStorage extends SingleVariantItemStorage<ItemVariant> {
    public final ContainerItemContext itemContext;
    public final @Nullable World world;
    private boolean amnesty;

    public IronBowlItemStorage(ContainerItemContext context, @Nullable World world) {
        super(context);
        this.itemContext = context;
        this.world = world;
    }

    @Override
    protected ItemVariant getBlankResource() {
        return ItemVariant.blank();
    }

    @Override
    protected ItemVariant getResource(ItemVariant currentVariant) {
        var nbt = currentVariant.getNbt();
        if (nbt == null) return ItemVariant.blank();
        return ItemVariant.fromNbt(nbt.getCompound("item"));
    }

    @Override
    protected long getAmount(ItemVariant currentVariant) {
        var nbt = currentVariant.getNbt();
        return nbt == null || !nbt.contains("item", NbtElement.COMPOUND_TYPE) ? 0 : 1;
    }

    @Override
    protected long getCapacity(ItemVariant variant) {
        return 1;
    }

    @Override
    protected ItemVariant getUpdatedVariant(ItemVariant currentVariant, ItemVariant newResource, long newAmount) {
        if (newResource.isBlank() || newAmount == 0) {
            var nbt = currentVariant.copyNbt();
            if (nbt == null) return currentVariant;
            nbt.remove("item");
            return ItemVariant.of(currentVariant.getItem(), nbt);
        }
        var nbt = currentVariant.copyOrCreateNbt();
        nbt.put("item", newResource.toNbt());
        return ItemVariant.of(currentVariant.getItem(), nbt);
    }

    @Override
    protected boolean canInsert(ItemVariant resource) {
        if (!new IronBowlFluidStorage(itemContext, world).isResourceBlank()) return false;
        if (!isAmnesty() && world != null) {
            return BakingRecipe.findFirst(world, RecipeConditions.firstIngredientMatches(resource.toStack())) != null;
        }
        return super.canInsert(resource);
    }

    public boolean isAmnesty() {
        return amnesty;
    }

    public void setAmnesty(boolean amnesty) {
        this.amnesty = amnesty;
    }
}
