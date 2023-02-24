package phoupraw.mcmod.createsdelight.registry;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.createsdelight.CreateSDelight;
public final class MyIdentifiers {

    public static final Identifier
      PAN = of("pan"),
      GRILL = of("grill"),
      SPRINKLER = of("sprinkler"),
      ITEM_GROUP = of("item_group"),
      PAN_FRIED_BEEF_PATTY = of("pan_fried_beef_patty"),
      THICK_PORK_SLICE = of("thick_pork_slice"),
      PAN_FRIED_PORK_SLICE = of("pan_fried_pork_slice"),
      THIN_PORK_SLICE = of("thin_pork_slice"),
      GRILLED_PORK_SLICE = of("grilled_pork_slice"),
      SUGAR_PORK = of("sugar_pork"),
      SUNFLOWER_OIL = of("sunflower_oil"),
      LEAVES_RICE = of("leaves_rice"),
      VANILLA = of("vanilla"),
      VANILLA_SWEET_ROLL = of("vanilla_sweet_roll"),
      BAMBOO_STEAMER = of("bamboo_steamer"),
      STEAMED_BUNS = of("steamed_buns"),
      SMART_DRAIN = of("smart_drain"),
      COPPER_TUNNEL = of("copper_tunnel"),
      MULTIFUNC_BASIN = of("multifunc_basin"),
      VERTICAL_CUTTER = of("vertical_cutter"),
      PRESSURE_COOKER = of("pressure_cooker"),
      COOKED_RICE = of("cooked_rice"),
      VEGETABLE_BIG_STEW = of("vegetable_big_stew"),
      MINCER = of("mincer"),
      SATIATION = of("satiation"),
      ROSE_MILK_TEA = of("rose_milk_tea"),
      BUCKETED_SUNFLOWER_OIL = of("bucketed_" + SUNFLOWER_OIL.getPath()),
      BOTTLED_SUNFLOWER_OIL = of("bottled_" + SUNFLOWER_OIL.getPath()),
      CORAL_COLORFULS = of("coral_colorfuls"),
      BEETROOT_SOUP = of("beetroot_soup"),
      TOMATO_SAUCE = of("tomato_sauce"),
      POPPY_RUSSIAN_SOUP = of("poppy_russian_soup");

    @Contract("_ -> new")
    public static @NotNull Identifier of(String path) {
        return new Identifier(CreateSDelight.MOD_ID, path);
    }

    private MyIdentifiers() {}
}
