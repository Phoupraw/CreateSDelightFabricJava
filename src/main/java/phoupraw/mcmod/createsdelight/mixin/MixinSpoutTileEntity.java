package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.contraptions.fluids.actors.SpoutTileEntity;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoupraw.mcmod.createsdelight.registry.CDSpoutingBehaviours;
@Mixin(value = SpoutTileEntity.class)
public abstract class MixinSpoutTileEntity extends SmartTileEntity implements CDSpoutingBehaviours.SpoutExtra {
    private double bottomY = 13 / 16.0;

    public MixinSpoutTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public double getBottomY() {
        return bottomY;
    }

    @Override
    public void setBottomY(double y) {
        if (bottomY != y) {
            bottomY = y;
            sendData();
        }
    }

    @Inject(method = "write", at = @At("RETURN"))
    private void write(NbtCompound root, boolean clientPacket, CallbackInfo ci) {
        if (clientPacket) {
            root.putDouble("bottomY", getBottomY());
        }
    }

    @Inject(method = "read", at = @At("RETURN"))
    private void read(NbtCompound root, boolean clientPacket, CallbackInfo ci) {
        if (clientPacket) {
            setBottomY(root.getDouble("bottomY"));
        }
    }
}
