package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
public final class MyItemTags {
    public static final TagKey<Item>
      COOKED_PORK = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("cooked_pork");

    private MyItemTags() {}
}
