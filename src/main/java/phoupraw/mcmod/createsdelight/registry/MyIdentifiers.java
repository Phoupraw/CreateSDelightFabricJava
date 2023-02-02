package phoupraw.mcmod.createsdelight.registry;

import net.minecraft.util.Identifier;

import java.util.Locale;
public final class MyIdentifiers {
    public static final String MOD_ID = "createsdelight";

    public static final Identifier PAN = of("pan");
    public static final Identifier GRILL = of("grill");

    public static final Identifier ITEM_GROUP = of("item_group");
    public static final Identifier PAN_FRIED_BEEF_PATTY = of("pan_fried_beef_patty");
    public static final Identifier THICK_PORK_SLICE = of("THICK_PORK_slice");
    public static final Identifier PAN_FRIED_PORK_SLICE = of("PAN_FRIED_PORK_slice");
    public static final Identifier THIN_PORK_SLICE = of("THIN_PORK_slice");
    public static final Identifier GRILLED_PORK_SLICE = of("GRILLED_PORK_slice");

    public static final Identifier SUNFLOWER_OIL = of("sunflower_oil");

    public static Identifier of(String path) {
        return new Identifier(MOD_ID, path.toLowerCase(Locale.ROOT));
    }

    private MyIdentifiers() {}
}
