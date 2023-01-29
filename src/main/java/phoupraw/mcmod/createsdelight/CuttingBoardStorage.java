package phoupraw.mcmod.createsdelight;

import com.nhoryzon.mc.farmersdelight.entity.block.CuttingBoardBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.item.ItemStack;
public class CuttingBoardStorage extends SingleStackStorage {
    private final CuttingBoardBlockEntity board;

    public CuttingBoardStorage(CuttingBoardBlockEntity board) {this.board = board;}


    @Override
    protected ItemStack getStack() {
        return board.getStoredItem();
    }

    @Override
    protected void setStack(ItemStack stack) {
        board.getInventory().setStack(0, stack);
    }
}
