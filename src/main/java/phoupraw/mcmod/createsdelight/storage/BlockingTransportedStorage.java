package phoupraw.mcmod.createsdelight.storage;

import com.simibubi.create.content.contraptions.relays.belt.BeltHelper;
import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;

import com.simibubi.create.content.logistics.block.depot.DepotBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.DirectBeltInputBehaviour;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;
public class BlockingTransportedStorage extends SingleStackStorage implements DirectBeltInputBehaviour.InsertionCallback, Supplier<TransportedItemStack> , Consumer<TransportedItemStack> {
    public static DirectBeltInputBehaviour.InsertionCallback callbackOf(Iterable<BlockingTransportedStorage> storages) {
        return (transported, side, simulate) -> {
            TransportedItemStack copy = transported.copy();
            for (BlockingTransportedStorage storage : storages) {
                copy.stack = storage.apply(copy, side, simulate);
            }
            return copy.stack;
        };
    }

    /**
     * @return 没有移动
     * @see DepotBehaviour#tick(TransportedItemStack)
     */
    public static boolean tickMovement(TransportedItemStack heldItem) {
        heldItem.prevBeltPosition = heldItem.beltPosition;
        heldItem.prevSideOffset = heldItem.sideOffset;
        float diff = .5f - heldItem.beltPosition;
        if (diff > 1 / 512f) {
            if (diff > 1 / 32f && !BeltHelper.isItemUpright(heldItem.stack))
                heldItem.angle += 1;
            heldItem.beltPosition += diff / 4f;
        }
        return diff < 1 / 16f;
    }
    private @NotNull TransportedItemStack transported = TransportedItemStack.EMPTY;

    @Override
    public ItemStack getStack() {
        return getTransported().stack;
    }

    @Override
    public void setStack(ItemStack stack) {
        setTransported(new TransportedItemStack(stack));
    }

    @Override
    protected boolean canInsert(ItemVariant itemVariant) {
        return getStack().isEmpty();
    }

    @Override
    protected int getCapacity(ItemVariant itemVariant) {
        return 1;
    }

    /**
     * 当{@code simulate}为{@code true}时，该方法无副作用。
     *
     * @param transported
     * @param side
     * @param simulate
     * @return 剩余
     */
    @Override
    public ItemStack apply(@NotNull TransportedItemStack transported, Direction side, boolean simulate) {
        if (!getStack().isEmpty()) return transported.stack;
        var copy = transported.copy();
        copy.insertedFrom = side;
        copy.beltPosition = side.getAxis().isVertical() ? 0.5f : 0;
        if (!simulate) setTransported(copy);
        return ItemStack.EMPTY;
    }

    @Override
    public TransportedItemStack get() {
        return getTransported();
    }

    @Override
    public void accept(TransportedItemStack transportedItemStack) {
        setTransported(transportedItemStack);
    }

    public @NotNull TransportedItemStack getTransported() {
        return transported;
    }

    public void setTransported(@NotNull TransportedItemStack transported) {
        this.transported = transported;
    }

}
