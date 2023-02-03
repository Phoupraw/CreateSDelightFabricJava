package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import phoupraw.mcmod.createsdelight.inject.InjectTileEntityBehaviour;
@Mixin(TileEntityBehaviour.class)
public abstract class MixinTileEntityBehaviour {
    @Inject(method = "get(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lcom/simibubi/create/foundation/tileEntity/behaviour/BehaviourType;)Lcom/simibubi/create/foundation/tileEntity/TileEntityBehaviour;", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/tileEntity/TileEntityBehaviour;get(Lnet/minecraft/block/entity/BlockEntity;Lcom/simibubi/create/foundation/tileEntity/behaviour/BehaviourType;)Lcom/simibubi/create/foundation/tileEntity/TileEntityBehaviour;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void onGet1(BlockView reader, BlockPos pos, BehaviourType<TileEntityBehaviour> type, CallbackInfoReturnable<TileEntityBehaviour> cir, BlockEntity te) {
        InjectTileEntityBehaviour.onGet1(reader, pos, type, cir, te);
    }

    @Inject(method = "get(Lnet/minecraft/block/entity/BlockEntity;Lcom/simibubi/create/foundation/tileEntity/behaviour/BehaviourType;)Lcom/simibubi/create/foundation/tileEntity/TileEntityBehaviour;", at = @At("HEAD"), cancellable = true)
    private static void onGet2(BlockEntity te, BehaviourType<TileEntityBehaviour> type, CallbackInfoReturnable<TileEntityBehaviour> cir) {
        InjectTileEntityBehaviour.onGet2(te, type, cir);
    }
}
