package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.CreateSDelight;
public final class CDItemTags {
    public static final TagKey<Item>
      COOKED_PORK = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("cooked_pork"),
      FOOD_TOOLTIP = TagKey.of(Registry.ITEM.getKey(), new Identifier(CreateSDelight.MOD_ID, "food_tooltip")),
      DOUGH = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("dough"),
      SALT = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("salt");

    private CDItemTags() {
    }
}
