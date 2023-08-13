package phoupraw.mcmod.createsdelight.behaviour;

import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import phoupraw.mcmod.createsdelight.api.DirectBeltInput;
import phoupraw.mcmod.createsdelight.mixin.MixinTileEntityBehaviour;
/**
 * @see DirectBeltInput
 * @see MixinTileEntityBehaviour
 */
public class WrappedBeltInputBehaviour extends DirectBeltInputBehaviour {
    private final DirectBeltInput.InsertionHandler insertionHandler;

    public WrappedBeltInputBehaviour(SmartBlockEntity te, DirectBeltInput.InsertionHandler insertionHandler) {
        super(te);
        this.insertionHandler = insertionHandler;
    }

    @Override
    public ItemStack handleInsertion(TransportedItemStack stack, Direction side, boolean simulate) {
        return getInsertionHandler().handleInsertion(stack, side, simulate);
    }

    @Override
    public ItemStack handleInsertion(ItemStack stack, Direction side, boolean simulate) {
        return getInsertionHandler().handleInsertion(stack, side, simulate);
    }

    public DirectBeltInput.InsertionHandler getInsertionHandler() {
        return insertionHandler;
    }
//    public static DirectBeltInputBehaviour of(BlockEntity blockEntity, DirectBeltInput.InsertionHandler insertionHandler) {
//        return of()
//    }
//    public static DirectBeltInputBehaviour of(World world, BlockPos blockPos, @Nullable BlockState blockState, DirectBeltInput.InsertionHandler insertionHandler) {
//        return new FakeDirectBeltInputBehaviour(FakeSmartTileEntity.of(world, blockPos, blockState, null), insertionHandler);
//    }
}
