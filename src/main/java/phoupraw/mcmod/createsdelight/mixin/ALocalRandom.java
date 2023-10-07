package phoupraw.mcmod.createsdelight.mixin;

import net.minecraft.util.math.random.LocalRandom;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = LocalRandom.class, remap = false)
public interface ALocalRandom {
    @Accessor
    long getSeed();
}
