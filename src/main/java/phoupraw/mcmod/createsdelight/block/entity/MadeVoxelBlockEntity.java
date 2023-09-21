package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.foundation.blockEntity.SyncedBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.misc.VoxelRecord;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

public class MadeVoxelBlockEntity extends SyncedBlockEntity {
    public static MadeVoxelBlockEntity of(BlockPos pos, BlockState state) {
        return new MadeVoxelBlockEntity(CSDBlockEntityTypes.MADE_VOXEL, pos, state);
    }
    protected VoxelRecord voxelRecord;
    public MadeVoxelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (getVoxelRecord() != null) {
            nbt.put("voxelRecord", getVoxelRecord().write(new NbtCompound()));
        }
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        setVoxelRecord(VoxelRecord.of(nbt.getCompound("voxelRecord"), blockHolderGetter()));
    }
    public VoxelRecord getVoxelRecord() {
        return voxelRecord;
    }
    public void setVoxelRecord(VoxelRecord voxelRecord) {
        this.voxelRecord = voxelRecord;
        //long t = System.currentTimeMillis();
        notifyUpdate();
        //CreateSDelight.LOGGER.info("MadeVoxelBlockEntity.setVoxelRecord notifyUpdate运行了%d毫秒".formatted(System.currentTimeMillis() - t));
        World world = getWorld();
        if (world != null) {
            long t = System.currentTimeMillis();
            world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.REDRAW_ON_MAIN_THREAD);
            //CreateSDelight.LOGGER.info("MadeVoxelBlockEntity.setVoxelRecord world.updateListeners运行了%d毫秒".formatted(System.currentTimeMillis() - t));
        }
    }
}
