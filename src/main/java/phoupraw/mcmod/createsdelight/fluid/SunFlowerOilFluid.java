package phoupraw.mcmod.createsdelight.fluid;

import net.minecraft.item.Item;
import phoupraw.mcmod.common.fluid.VirtualFluid;
import phoupraw.mcmod.createsdelight.registry.MyItems;
public class SunFlowerOilFluid extends VirtualFluid {
    @Override
    public Item getBucketItem() {
        return MyItems.BUCKETED_SUNFLOWER_OIL;
    }
}
