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
      BUCKETED_SUNFLOWER_OIL = of("bucketed_sunflower_oil"),
      BOTTLED_SUNFLOWER_OIL = of("bottled_sunflower_oil"),
      CORAL_COLORFULS = of("coral_colorfuls"),
      BEETROOT_SOUP = of("beetroot_soup"),
      TOMATO_SAUCE = of("tomato_sauce"),
      POPPY_RUSSIAN_SOUP = of("poppy_russian_soup"),
      EGG_LIQUID = of("egg_liquid"),
      EGG_SHELL = of("egg_shell"),
      EGG_DOUGH = of("egg_dough"),
      CRUSHED_ICE = of("crushed_ice"),
      WHEAT_BLACK_TEA = of("wheat_black_tea"),
      ICED_MELON_JUICE = of("iced_melon_juice"),
      MELON_JUICE = of("melon_juice"),
      THICK_HOT_COCOA = of("thick_hot_cocoa"),
      SKEWER = of("skewer"),
      BASIN = of("basin"),
      SKEWER_PLATE = of("skewer_plate"),
      SALT = of("salt"),
      KELP_ASH = of("kelp_ash"),
      JELLY_BEANS = of("jelly_beans"),
      JELLY_BEANS_CAKE = of("jelly_beans_cake"),
      YEAST = of("yeast"),
      PASTE = of("paste"),
      CAKE_BASE = of("cake_base"),
      CAKE_BASE_SLICE = of("cake_base_slice"),
      SWEET_BERRIES_CAKE = of("sweet_berries_cake"),
      BASQUE_CAKE = of("basque_cake"),
      RAW_BASQUE_CAKE = of("raw_basque_cake"),
      SWEET_BERRIES_CAKE_S = of("sweet_berries_cakes"),
      BROWNIE = of("brownie"),
      APPLE_CREAM_CAKE = of("apple_cream_cake"),
      SUNFLOWER_KERNELS = of("sunflower_kernels"),
      PUMPKIN_OIL = of("pumpkin_oil"),
      BUCKETED_PUMPKIN_OIL = of("bucketed_pumpkin_oil"),
      OVEN = of("oven"),
      APPLE_PASTE = of("apple_paste"),
      APPLE_CAKE = of("apple_cake"),
      CARROT_CREAM_CAKE = of("carrot_cream_cake"),
      MASHED_PATATO = of("mashed_potato"),
      INCOMPLETE_JELLY_BEANS_CAKE = of("incomplete_jelly_beans_cake"),
      INCOMPLETE_SWEET_BERRIES_CAKE = of("incomplete_sweet_berries_cake"),
      INCOMPLETE_SWEET_BERRIES_CAKE_S = of("incomplete_sweet_berries_cake_s"),
      INCOMPLETE_RAW_BASQUE_CAKE = of("incomplete_raw_basque_cake"),
      INCOMPLETE_BROWNIE = of("incomplete_brownie"),
      INCOMPLETE_APPLE_CREAM_CAKE = of("incomplete_apple_cream_cake"),
      INCOMPLETE_CARROT_CREAM_CAKE = of("incomplete_carrot_cream_cake"),
      IRON_BOWL = of("iron_bowl");

    @Contract("_ -> new")
    public static @NotNull Identifier of(String path) {
        return new Identifier(CreateSDelight.MOD_ID, path);
    }

    private MyIdentifiers() {}
}
