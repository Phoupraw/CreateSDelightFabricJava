package phoupraw.mcmod.createsdelight.mixin;

import net.minecraft.util.random.SingleThreadedRandom;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = SingleThreadedRandom.class)
public interface ALocalRandom {
    //@Accessor
    //long getSeed();
}
