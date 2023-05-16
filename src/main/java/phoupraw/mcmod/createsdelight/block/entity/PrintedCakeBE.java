package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;

import java.util.List;
public class PrintedCakeBE extends SmartTileEntity {

    public PrintedCakeBE(BlockPos pos, BlockState state) {
        this(MyBlockEntityTypes.PRINTED_CAKE, pos, state);
    }

    public PrintedCakeBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {

    }
}
