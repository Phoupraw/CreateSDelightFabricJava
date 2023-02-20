package phoupraw.mcmod.createsdelight.registry;

import net.minecraft.block.MapColor;
import net.minecraft.item.Items;
import phoupraw.mcmod.createsdelight.api.VirtualFluid;
public final class MyFluids {
    public static final VirtualFluid SUNFLOWER_OIL = new VirtualFluid.Builder().withId(MyIdentifiers.SUNFLOWER_OIL).withItemGroup(MyItems.ITEM_GROUP).withTint(MapColor.TERRACOTTA_YELLOW.color).buildAndRegister();
    public static final VirtualFluid VEGETABLE_BIG_STEW = new VirtualFluid.Builder().withId(MyIdentifiers.VEGETABLE_BIG_STEW).withItemGroup(MyItems.ITEM_GROUP).withTint(MapColor.LICHEN_GREEN.color).withBottle(Items.AIR).withBucket(Items.AIR).buildAndRegister();

    private MyFluids() {}
}
