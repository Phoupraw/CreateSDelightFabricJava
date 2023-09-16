package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.foundation.outliner.ChasingAABBOutline;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = ChasingAABBOutline.class, remap = false)
public interface AChasingAABBOutline {
    @Invoker
    static Box invokeInterpolateBBs(Box current, Box target, float pt) {
        throw new AssertionError("mixin static invoker");
    }
    @Accessor
    Box getTargetBB();
    @Accessor
    Box getPrevBB();
}
