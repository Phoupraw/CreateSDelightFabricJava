package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
@Mixin(BasinTileEntity.class)
public abstract class MixinBasinTileEntity {
    /**
     扩容{@link BasinTileEntity#inputTank}，使其能储存4种流体。
     @param tanks 原本是2
     @return 4
     */
    @ModifyArg(method = "addBehaviours", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/tileEntity/behaviour/fluid/SmartFluidTankBehaviour;<init>(Lcom/simibubi/create/foundation/tileEntity/behaviour/BehaviourType;Lcom/simibubi/create/foundation/tileEntity/SmartTileEntity;IJZ)V", ordinal = 0, remap = false), remap = false)
    private int expandInputTank(int tanks) {
        return 4;
    }
}
