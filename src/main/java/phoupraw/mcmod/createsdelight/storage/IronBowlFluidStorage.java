package phoupraw.mcmod.createsdelight.storage;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantItemStorage;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
public class IronBowlFluidStorage extends SingleVariantItemStorage<FluidVariant> {
    public final ContainerItemContext itemContext;
    public final @Nullable World world;
    private boolean amnesty = true;

    public IronBowlFluidStorage(ContainerItemContext context, @Nullable World world) {
        super(context);
        this.itemContext = context;
        this.world = world;
    }

    @Override
    protected FluidVariant getBlankResource() {
        return FluidVariant.blank();
    }

    @Override
    protected FluidVariant getResource(ItemVariant currentVariant) {
        var nbt = currentVariant.getNbt();
        if (nbt == null) return FluidVariant.blank();
        return FluidVariant.fromNbt(nbt.getCompound("fluid"));
    }

    @Override
    protected long getAmount(ItemVariant currentVariant) {
        var nbt = currentVariant.getNbt();
        if (nbt == null) return 0;
        return nbt.getLong("amount");
    }

    @Override
    protected long getCapacity(FluidVariant variant) {
        return FluidConstants.BUCKET / 2;
    }

    @Override
    protected ItemVariant getUpdatedVariant(ItemVariant currentVariant, FluidVariant newResource, long newAmount) {
        if (newResource.isBlank() || newAmount == 0) {
            var nbt = currentVariant.copyNbt();
            if (nbt == null) return currentVariant;
            nbt.remove("fluid");
            nbt.remove("amount");
            return ItemVariant.of(currentVariant.getItem(), nbt);
        }
        var nbt = currentVariant.copyOrCreateNbt();
        nbt.put("fluid", newResource.toNbt());
        nbt.putLong("amount", newAmount);
        return ItemVariant.of(currentVariant.getItem(), nbt);
    }

    @Override
    protected boolean canInsert(FluidVariant resource) {
        if (!new IronBowlItemStorage(itemContext, world).isResourceBlank()) return false;
        if (!isAmnesty() && world != null) {
            //return BakingRecipe.findFirst(world, LambdasC.firstIngredientMatching(resource.getFluid())) != null;
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
