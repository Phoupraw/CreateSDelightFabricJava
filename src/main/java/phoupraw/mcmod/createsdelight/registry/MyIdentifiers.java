package phoupraw.mcmod.createsdelight.registry;

import net.minecraft.util.Identifier;
import phoupraw.mcmod.createsdelight.CreateSDelight;

import java.util.Locale;
public final class MyIdentifiers {

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
      VANILLA_SWEET_ROLL = of("vanilla_sweet_roll"),
      BAMBOO_STEAMER = of("BAMBOO_STEAMER"),
      STEAMED_BUNS = of("steamed_buns"),
      SMART_DRAIN = of("smart_drain"),
      COPPER_TUNNEL = of("copper_tunnel"),
      MULTIFUNC_BASIN = of("multifunc_basin"),
      VERTICAL_CUTTER = of("vertical_cutter"),
      PRESSURE_COOKER = of("pressure_cooker"),
      COOKED_RICE = of("cooked_rice"),
      VEGETABLE_BIG_STEW = of("vegetable_big_stew"),
      MINCER = of("mincer"),
      SATIATION = of("satiation");

    public static Identifier of(String path) {
        return new Identifier(CreateSDelight.MOD_ID, path.toLowerCase(Locale.ROOT));
    }

    private MyIdentifiers() {}
}
