package phoupraw.mcmod.createsdelight;

import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.DirectBeltInputBehaviour;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
public class WrappedDirectBeltInputBehaviour extends DirectBeltInputBehaviour {
    private final DirectBeltInput.InsertionHandler insertionHandler;

    public WrappedDirectBeltInputBehaviour(SmartTileEntity te, DirectBeltInput.InsertionHandler insertionHandler) {
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
