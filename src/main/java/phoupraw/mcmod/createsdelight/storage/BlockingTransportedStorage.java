package phoupraw.mcmod.createsdelight.storage;

import com.simibubi.create.content.kinetics.belt.BeltHelper;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.logistics.depot.DepotBehaviour;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;
public class BlockingTransportedStorage extends SingleStackStorage implements DirectBeltInputBehaviour.InsertionCallback, Supplier<TransportedItemStack>, Consumer<TransportedItemStack> {
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

    /**
     * 根据{@link TransportedItemStack#insertedFrom}、{@link TransportedItemStack#prevBeltPosition}、{@link TransportedItemStack#beltPosition}、{@link TransportedItemStack#prevSideOffset}、{@link TransportedItemStack#sideOffset}计算相对于方块位置{@code (0,?,0)}的水平偏移量。
     *
     * @param partialTicks 如果没有来自{@link BlockEntityRenderer#render}的{@code partialTicks}，则填1
     * @return 相对于方块位置 {@code (0,?,0)} 水平偏移量，返回值的y为0
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static Vec3d getHorizontalOffset(@NotNull TransportedItemStack transp, float partialTicks) {
        Direction insertedFrom = transp.insertedFrom;
        Vec3d base = new Vec3d(0.5, 0, 0.5);
        if (insertedFrom.getAxis().isVertical()) return base;
        float beltPosition = MathHelper.lerp(partialTicks, transp.prevBeltPosition, transp.beltPosition);
        float sideOffset = MathHelper.lerp(partialTicks, transp.prevSideOffset, transp.sideOffset);
        boolean alongX = insertedFrom.rotateYClockwise().getAxis() == Direction.Axis.X;
        if (!alongX) sideOffset *= -1;
        return Vec3d.of(insertedFrom.getOpposite().getVector())
          .multiply(.5f - beltPosition)
          .add(alongX ? sideOffset : 0, 0, alongX ? 0 : sideOffset)
          .add(base);
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
