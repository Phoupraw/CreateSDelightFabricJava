package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import phoupraw.mcmod.createsdelight.registry.CDBlockEntityTypes;

import java.util.List;
public class MultifuncBasinBlockEntity extends SmartTileEntity {
    public MultifuncBasinBlockEntity(BlockPos pos, BlockState state) {
        this(CDBlockEntityTypes.MULTIFUNC_BASIN, pos, state);
    }

    public MultifuncBasinBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {

    }
}
