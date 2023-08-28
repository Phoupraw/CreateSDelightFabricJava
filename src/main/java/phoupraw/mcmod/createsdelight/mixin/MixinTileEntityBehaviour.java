package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = BlockEntityBehaviour.class)
public abstract class MixinTileEntityBehaviour {
    //@Inject(method = "get(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lcom/simibubi/create/foundation/tileEntity/behaviour/BehaviourType;)Lcom/simibubi/create/foundation/tileEntity/TileEntityBehaviour;", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/tileEntity/TileEntityBehaviour;get(Lnet/minecraft/block/entity/BlockEntity;Lcom/simibubi/create/foundation/tileEntity/behaviour/BehaviourType;)Lcom/simibubi/create/foundation/tileEntity/TileEntityBehaviour;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    //private static void onGet1(BlockView reader, BlockPos pos, BehaviourType<TileEntityBehaviour> type, CallbackInfoReturnable<TileEntityBehaviour> cir, BlockEntity te) {
    //    InjectTileEntityBehaviour.onGet1(reader, pos, type, cir, te);
    //}
    //
    //@Inject(method = "get(Lnet/minecraft/block/entity/BlockEntity;Lcom/simibubi/create/foundation/tileEntity/behaviour/BehaviourType;)Lcom/simibubi/create/foundation/tileEntity/TileEntityBehaviour;", at = @At(value = "HEAD"), cancellable = true)
    //private static void onGet2(BlockEntity te, BehaviourType<TileEntityBehaviour> type, CallbackInfoReturnable<TileEntityBehaviour> cir) {
    //    InjectTileEntityBehaviour.onGet2(te, type, cir);
    //}
}
