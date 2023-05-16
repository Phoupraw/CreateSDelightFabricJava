package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.impl.tag.convention.TagRegistration;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.TagKey;
public final class CDFluidTags {
    public static final TagKey<Fluid>
      OIL = TagRegistration.FLUID_TAG_REGISTRATION.registerCommon("oil");

    private CDFluidTags() {
    }
}
