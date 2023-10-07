package phoupraw.mcmod.createsdelight.mixin;

import net.minecraft.util.math.random.LocalRandom;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LocalRandom.class)
public interface ALocalRandom {
    //@Accessor
    //long getSeed();
}
