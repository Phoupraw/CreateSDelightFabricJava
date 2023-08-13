package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.deployer.DeployerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoupraw.mcmod.createsdelight.inject.InjectDeployerBlockEntity;
@Mixin(DeployerBlockEntity.class)
public abstract class MixinDeployerBlockEntity extends KineticBlockEntity implements InjectDeployerBlockEntity {
    @Shadow(remap = false)
    protected DeployerBlockEntity.State state;

    @Shadow(remap = false)
    protected DeployerBlockEntity.Mode mode;

    public MixinDeployerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/deployer/DeployerBlockEntity;getSpeed()F"))
    private void tickBeforeCheckSpeed(CallbackInfo ci) {
        InjectDeployerBlockEntity.tickBeforeCheckSpeed(this);
    }

    @Override
    public Enum<?> getState() {
        return state;
//        try {
//            return (Enum<?>) State.FIELD.get(this);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void setState(Enum<?> state) {
        this.state = (DeployerBlockEntity.State) state;
//        try {
//            State.FIELD.set(this, state);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public Enum<?> getMode() {
        return mode;
//        try {
//            return (Enum<?>) Mode.FIELD.get(this);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Override
    public void setMode(Enum<?> mode) {
        this.mode = (DeployerBlockEntity.Mode) mode;
//        try {
//            Mode.FIELD.set(this, state);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
    }
}
