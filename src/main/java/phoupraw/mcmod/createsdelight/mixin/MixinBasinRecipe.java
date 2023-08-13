package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.processing.basin.BasinRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
@Mixin(BasinRecipe.class)
public abstract class MixinBasinRecipe {
    /**
     @return 4
     @author Phoupraw
     @reason 使工作盆配方能输入4种流体。
     */
    @Overwrite(remap = false)
    protected int getMaxFluidInputCount() {
        return 4;
    }
}
