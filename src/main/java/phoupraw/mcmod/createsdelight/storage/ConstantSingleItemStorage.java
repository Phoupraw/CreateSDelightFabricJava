package phoupraw.mcmod.createsdelight.storage;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleItemStorage;
public class ConstantSingleItemStorage extends SingleItemStorage {
    private long capacity;

    public ConstantSingleItemStorage(long capacity) {this.setCapacity(capacity);}

    @Override
    protected long getCapacity(ItemVariant variant) {
        return getCapacity();
    }

    @Override
    public long getCapacity() {
        return capacity;
    }

    public void setCapacity(long capacity) {
        this.capacity = capacity;
    }
}
