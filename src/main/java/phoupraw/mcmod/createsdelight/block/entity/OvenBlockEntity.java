package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;

import java.util.List;
public class OvenBlockEntity extends SmartTileEntity {
    public OvenBlockEntity(BlockPos pos, BlockState state) {
        this(MyBlockEntityTypes.OVEN, pos, state);
    }

    public OvenBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {

    }
}
