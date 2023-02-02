package phoupraw.mcmod.createsdelight.registry;

import net.minecraft.block.MapColor;
import phoupraw.mcmod.createsdelight.api.VirtualFluid;
public final class MyFluids {
    public static final VirtualFluid SUNFLOWER_OIL = new VirtualFluid.Builder().withId(MyIdentifiers.SUNFLOWER_OIL).withItemGroup(MyItems.ITEM_GROUP).withTint(MapColor.TERRACOTTA_YELLOW.color).buildAndRegister();

    private MyFluids() {}
}
