package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import phoupraw.mcmod.createsdelight.inject.InjectTileEntityBehaviour;
@Mixin(value = BlockEntityBehaviour.class)
public abstract class MixinTileEntityBehaviour {
    @Inject(method = "get(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lcom/simibubi/create/foundation/blockEntity/behaviour/BehaviourType;)Lcom/simibubi/create/foundation/blockEntity/behaviour/BlockEntityBehaviour;", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/blockEntity/behaviour/BlockEntityBehaviour;get(Lnet/minecraft/block/entity/BlockEntity;Lcom/simibubi/create/foundation/blockEntity/behaviour/BehaviourType;)Lcom/simibubi/create/foundation/blockEntity/behaviour/BlockEntityBehaviour;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private static void onGet1(BlockView reader, BlockPos pos, BehaviourType<BlockEntityBehaviour> type, CallbackInfoReturnable<BlockEntityBehaviour> cir, BlockEntity te) {
        InjectTileEntityBehaviour.onGet1(reader, pos, type, cir, te);
    }

    @Inject(method = "get(Lnet/minecraft/block/entity/BlockEntity;Lcom/simibubi/create/foundation/blockEntity/behaviour/BehaviourType;)Lcom/simibubi/create/foundation/blockEntity/behaviour/BlockEntityBehaviour;", at = @At(value = "HEAD"), cancellable = true)
    private static void onGet2(BlockEntity te, BehaviourType<BlockEntityBehaviour> type, CallbackInfoReturnable<BlockEntityBehaviour> cir) {
        InjectTileEntityBehaviour.onGet2(te, type, cir);
    }
}
