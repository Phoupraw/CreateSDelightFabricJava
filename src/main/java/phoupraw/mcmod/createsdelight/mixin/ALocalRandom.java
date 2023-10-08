package phoupraw.mcmod.createsdelight.mixin;

import net.minecraft.util.random.SingleThreadedRandom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SingleThreadedRandom.class)
public interface ALocalRandom {
    @Accessor
    long getSeed();
}
