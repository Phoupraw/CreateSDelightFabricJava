package phoupraw.mcmod.createsdelight.api;

import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
public interface GetWorldContainerItemContext extends ContainerItemContext, GetWorld {
    @Contract(value = "_ -> new", pure = true)
    static @NotNull ContainerItemContext of(ItemStack mutable) {
        return ContainerItemContext.ofSingleSlot(InventoryStorage.of(new SimpleInventory(mutable), null).getSlot(0));
    }
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull GetWorldContainerItemContext of(@Nullable World world, ContainerItemContext wrapped) {
        return new Wrapper(world, wrapped);
    }
    @ApiStatus.Experimental
    class Wrapper implements GetWorldContainerItemContext {
        private final @Nullable World world;
        private final ContainerItemContext wrapped;

        public Wrapper(@Nullable World world, ContainerItemContext wrapped) {
            this.world = world;
            this.wrapped = wrapped;
        }

        @Nullable
        @Override
        public World getWorld() {
            return world;
        }

        @Override
        public <A> @Nullable A find(ItemApiLookup<A, ContainerItemContext> lookup) {
            return getWrapped().find(lookup);
        }

        @Override
        public ItemVariant getItemVariant() {
            return getWrapped().getItemVariant();
        }

        @Override
        public long getAmount() {
            return getWrapped().getAmount();
        }

        @Override
        public long insert(ItemVariant itemVariant, long maxAmount, TransactionContext transaction) {
            return getWrapped().insert(itemVariant, maxAmount, transaction);
        }

        @Override
        public long extract(ItemVariant itemVariant, long maxAmount, TransactionContext transaction) {
            return getWrapped().extract(itemVariant, maxAmount, transaction);
        }

        @Override
        public long exchange(ItemVariant newVariant, long maxAmount, TransactionContext transaction) {
            return getWrapped().exchange(newVariant, maxAmount, transaction);
        }

        @Override
        public SingleSlotStorage<ItemVariant> getMainSlot() {
            return getWrapped().getMainSlot();
        }

        @Override
        public long insertOverflow(ItemVariant itemVariant, long maxAmount, TransactionContext transactionContext) {
            return getWrapped().insertOverflow(itemVariant, maxAmount, transactionContext);
        }

        @Override
        public List<SingleSlotStorage<ItemVariant>> getAdditionalSlots() {
            return getWrapped().getAdditionalSlots();
        }

        public ContainerItemContext getWrapped() {
            return wrapped;
        }
    }
}
