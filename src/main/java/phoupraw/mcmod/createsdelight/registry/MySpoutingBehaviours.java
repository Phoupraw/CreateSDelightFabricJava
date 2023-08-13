package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.api.behaviour.BlockSpoutingBehaviour;
import com.simibubi.create.content.fluids.spout.SpoutBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.block.entity.PanBlockEntity;
import phoupraw.mcmod.createsdelight.block.entity.SmartDrainBlockEntity;
public final class MySpoutingBehaviours {
    public static final BlockSpoutingBehaviour PAN = new BlockSpoutingBehaviour() {
        @Override
        public long fillBlock(World world, BlockPos pos, SpoutBlockEntity spout, FluidStack availableFluid, boolean simulate) {
            var blockEntity = world.getBlockEntity(pos);
            if (!(blockEntity instanceof PanBlockEntity pan)) return 0;
            try (var transaction = Transaction.openOuter()) {
                long amount = pan.getTank().getCapability().insert(availableFluid.getType(), availableFluid.getAmount(), transaction);
                if (amount == 0) return 0;
                ((SpoutExtra) spout).setBottomY(1 / 16.0);
                if (!simulate) transaction.commit();
                return amount;
            }
        }
    };
    public static final BlockSpoutingBehaviour SMART_DARIN = new BlockSpoutingBehaviour() {
        @Override
        public long fillBlock(World world, BlockPos pos, SpoutBlockEntity spout, FluidStack availableFluid, boolean simulate) {
            var blockEntity = world.getBlockEntity(pos);
            if (!(blockEntity instanceof SmartDrainBlockEntity drain)) return 0;
            try (var transaction = TransferUtil.getTransaction()) {
                long amount = drain.getBehaviour(SmartFluidTankBehaviour.TYPE).getCapability().insert(availableFluid.getType(), availableFluid.getAmount(), transaction);
                if (amount == 0) return 0;
                ((SpoutExtra) spout).setBottomY(2 / 16.0);
                if (!simulate) transaction.commit();
                return amount;
            }
        }
    };
    static {
        BlockSpoutingBehaviour.addCustomSpoutInteraction(MyIdentifiers.PAN, PAN);
        BlockSpoutingBehaviour.addCustomSpoutInteraction(MyIdentifiers.SMART_DRAIN, SMART_DARIN);
    }
    private MySpoutingBehaviours() {}

    public interface SpoutExtra {
        double getBottomY();
        void setBottomY(double y);
    }
}
