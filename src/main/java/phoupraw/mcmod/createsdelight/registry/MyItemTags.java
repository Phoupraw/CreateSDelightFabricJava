package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
public final class MyItemTags {
    public static final TagKey<Item>
      COOKED_PORK = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("cooked_pork"),
      FOOD_TOOLTIP = TagKey.of(Registry.ITEM.getKey(), new Identifier(MyIdentifiers.MOD_ID, "food_tooltip"));

    private MyItemTags() {}
}
