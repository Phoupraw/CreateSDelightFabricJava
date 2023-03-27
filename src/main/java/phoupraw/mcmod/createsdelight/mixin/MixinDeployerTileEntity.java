package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.components.deployer.DeployerTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoupraw.mcmod.createsdelight.inject.InjectDeployerTileEntity;
@Mixin(DeployerTileEntity.class)
public abstract class MixinDeployerTileEntity extends KineticTileEntity implements InjectDeployerTileEntity {
    @Shadow(remap = false)
    protected DeployerTileEntity.State state;

    @Shadow(remap = false)
    protected DeployerTileEntity.Mode mode;

    public MixinDeployerTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/contraptions/components/deployer/DeployerTileEntity;getSpeed()F"))
    private void tickBeforeCheckSpeed(CallbackInfo ci) {
        InjectDeployerTileEntity.tickBeforeCheckSpeed(this);
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
        this.state = (DeployerTileEntity.State) state;
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
        this.mode = (DeployerTileEntity.Mode) mode;
//        try {
//            Mode.FIELD.set(this, state);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
    }
}
