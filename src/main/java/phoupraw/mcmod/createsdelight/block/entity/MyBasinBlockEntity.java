package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;

import java.util.List;
public class MyBasinBlockEntity extends SmartTileEntity implements IHaveGoggleInformation, SidedStorageBlockEntity {
    public MyBasinBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.BASIN, pos, state);}

    public MyBasinBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {

    }
}
