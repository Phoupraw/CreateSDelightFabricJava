package phoupraw.mcmod.createsdelight.misc;

import net.minecraft.item.Item;

/**
 * @since 1.0.0
 */

public class BucketedVirtualFluid extends VirtualFluid {

    private final Item bucketItem;

    public BucketedVirtualFluid(Item bucketItem) {
        super();
        this.bucketItem = bucketItem;
    }

    @Override
    public Item getBucketItem() {
        return bucketItem;
    }

}
