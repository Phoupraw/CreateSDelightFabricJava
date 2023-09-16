package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.Create;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoupraw.mcmod.createsdelight.CreateSDelight;

@Mixin(value = Create.class, remap = false)
class MCreate {
    @Inject(method = "onInitialize", at = @At("RETURN"))
    private void afterCreateInit(CallbackInfo ci) {
        CreateSDelight.afterCreateInit();
    }
}
