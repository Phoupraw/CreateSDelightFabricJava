package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.Create;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoupraw.mcmod.createsdelight.registry.CDModInitializer;
@Mixin(value = Create.class)
public class MixinCreate {
    /**
     * stupid fabric loader made loading more complex
     */
    @Inject(method = "onInitialize",at = @At("TAIL"),remap = false)
    private void afterInitialize(CallbackInfo ci){
        CDModInitializer.initializeAfterCreate();
    }
}
