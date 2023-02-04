package phoupraw.mcmod.createsdelight.registry;

import net.minecraft.util.Identifier;

import java.util.Locale;
public final class MyIdentifiers {
    public static final String MOD_ID = "createsdelight";

    public static final Identifier
      PAN = of("pan"),
      GRILL = of("grill"),
      SPRINKLER = of("sprinkler"),
      ITEM_GROUP = of("item_group"),
      PAN_FRIED_BEEF_PATTY = of("pan_fried_beef_patty"),
      THICK_PORK_SLICE = of("THICK_PORK_slice"),
      PAN_FRIED_PORK_SLICE = of("PAN_FRIED_PORK_slice"),
      THIN_PORK_SLICE = of("THIN_PORK_slice"),
      GRILLED_PORK_SLICE = of("GRILLED_PORK_slice"),
      SUGAR_PORK = of("sugar_pork"),
      SUNFLOWER_OIL = of("sunflower_oil"),
      LEAVES_RICE = of("LEAVES_RICE"),
      VANILLA = of("vanilla"),
    VANILLA_SWEET_ROLL=of("vanilla_sweet_roll"),
    BAMBOO_STEAMER=of("BAMBOO_STEAMER"),
    STEAMED_BUNS =of("steamed_buns");

    public static Identifier of(String path) {
        return new Identifier(MOD_ID, path.toLowerCase(Locale.ROOT));
    }

    private MyIdentifiers() {}
}
