package phoupraw.mcmod.createsdelight.inject;

import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@ApiStatus.Internal
public class InjectTileEntityBehaviour {

    //public static final BlockApiCache<DirectBeltInput.InsertionHandler,Void> BLOCK_API_CACHE = BlockApiCache.create()
    public static void onGet1(BlockView reader, BlockPos pos, BehaviourType<BlockEntityBehaviour> type, CallbackInfoReturnable<BlockEntityBehaviour> cir, BlockEntity te) {
        //if (te != null || !(reader instanceof World world)) return;
        //var handler = DirectBeltInput.LOOKUP.find(world, pos, null, null, null);
        //if (handler == null) return;
        //cir.setReturnValue(new WrappedBeltInputBehaviour(FakeSmartTileEntity.of(world, pos, null, null), handler));
    }

    public static void onGet2(BlockEntity te, BehaviourType<BlockEntityBehaviour> type, CallbackInfoReturnable<BlockEntityBehaviour> cir) {
        //if (te == null || te.getWorld() == null || te instanceof SmartBlockEntity || !Objects.equals(DirectBeltInputBehaviour.TYPE, type)) return;
        //var handler = DirectBeltInput.LOOKUP.find(te.getWorld(), te.getPos(), te.getCachedState(), te, null);
        //if (handler == null) return;
        //cir.setReturnValue(new WrappedBeltInputBehaviour(FakeSmartBlockEntity.of(te), handler));
    }

}
