package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
@Mixin(value = LerpedFloat.class)
public interface AccessLerpedFloat {
    @Accessor(remap = false)
    float getPreviousValue();
    @Accessor(remap = false)
    void setPreviousValue(float value);
}
