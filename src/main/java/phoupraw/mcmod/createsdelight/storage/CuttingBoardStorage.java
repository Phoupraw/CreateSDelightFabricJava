package phoupraw.mcmod.createsdelight.storage;

import com.nhoryzon.mc.farmersdelight.entity.block.CuttingBoardBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.minecraft.item.ItemStack;
import phoupraw.mcmod.createsdelight.api.FarmersDelightWrappers;
/**
 * @see FarmersDelightWrappers#storageOf(CuttingBoardBlockEntity)
 */
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
