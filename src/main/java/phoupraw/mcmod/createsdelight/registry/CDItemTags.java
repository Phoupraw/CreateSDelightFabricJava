package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.createsdelight.CreateSDelight;

public final class CDItemTags {

    public static final TagKey<Item>
      COOKED_PORK = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("cooked_pork"),
      FOOD_TOOLTIP = TagKey.of(Registries.ITEM.getKey(), new Identifier(CreateSDelight.MOD_ID, "food_tooltip")),
      DOUGH = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("dough"),
      SALT = TagRegistration.ITEM_TAG_REGISTRATION.registerCommon("salt");

    private CDItemTags() {
    }

}
