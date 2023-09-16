package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.foundation.blockEntity.SyncedBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import phoupraw.mcmod.createsdelight.misc.VoxelRecord;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

public class MadeVoxelBlockEntity extends SyncedBlockEntity {
    public VoxelRecord voxelRecord;
    public MadeVoxelBlockEntity(BlockPos pos, BlockState state) {this(CSDBlockEntityTypes.MADE_VOXEL, pos, state);}
    public MadeVoxelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (voxelRecord != null) {
            nbt.put("voxelRecord", voxelRecord.write(new NbtCompound()));
        }
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        voxelRecord = VoxelRecord.of(nbt.getCompound("voxelRecord"));
    }
}
