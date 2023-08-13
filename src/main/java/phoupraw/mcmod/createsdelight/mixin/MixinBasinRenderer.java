package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.processing.basin.BasinRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
@Mixin(BasinRenderer.class)
@Environment(EnvType.CLIENT)
public class MixinBasinRenderer {
    @ModifyConstant(method = "renderFluids", constant = @Constant(floatValue = 12 / 16f))
    private float modifyFluidBoxHeight(float fluidBoxHeight) {
        return 12.01f / 16f;
    }
}
