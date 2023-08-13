package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
@Mixin(BasinBlockEntity.class)
public abstract class MixinBasinBlockEntity {
    /**
     扩容{@link com.simibubi.create.content.processing.basin.BasinBlockEntity#inputTank}，使其能储存4种流体。
     @param tanks 原本是2
     @return 4
     */
    @ModifyArg(method = "addBehaviours", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/blockEntity/behaviour/fluid/SmartFluidTankBehaviour;<init>(Lcom/simibubi/create/foundation/blockEntity/behaviour/BehaviourType;Lcom/simibubi/create/foundation/blockEntity/SmartBlockEntity;IJZ)V", ordinal = 0, remap = false), remap = false)
    private int expandInputTank(int tanks) {
        return 4;
    }
}
