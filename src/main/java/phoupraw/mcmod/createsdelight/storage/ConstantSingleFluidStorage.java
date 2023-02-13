package phoupraw.mcmod.createsdelight.storage;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
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
