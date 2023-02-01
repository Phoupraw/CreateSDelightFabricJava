package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.api.behaviour.BlockSpoutingBehaviour;
import com.simibubi.create.content.contraptions.fluids.actors.SpoutTileEntity;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.block.entity.PanBlockEntity;
public final class MySpoutingBehaviours {
    public static final BlockSpoutingBehaviour PAN = new BlockSpoutingBehaviour() {
        @Override
        public long fillBlock(World world, BlockPos pos, SpoutTileEntity spout, FluidStack availableFluid, boolean simulate) {
            var blockEntity = world.getBlockEntity(pos);
            if (!(blockEntity instanceof PanBlockEntity pan)) return 0;
            try(var transaction= Transaction.openOuter()) {
               var amount= pan.getTank().getCapability().insert(availableFluid.getType(),availableFluid.getAmount(),transaction);
               if (!simulate) transaction.commit();
               return amount;
            }
        }
    };
    static {
        BlockSpoutingBehaviour.addCustomSpoutInteraction(MyIdentifiers.PAN,PAN);
    }
    private MySpoutingBehaviours(){}
}
