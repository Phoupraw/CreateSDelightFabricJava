package phoupraw.mcmod.createsdelight.api;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleItemStorage;
public class ConstantSingleFluidStorage extends SingleFluidStorage {
    private long capacity;

    public ConstantSingleFluidStorage(long capacity) {this.setCapacity(capacity);}

    @Override
    protected long getCapacity(FluidVariant variant) {
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
