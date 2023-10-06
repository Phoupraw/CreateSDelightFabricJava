package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.foundation.blockEntity.SyncedBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.misc.BlockFoods;
import phoupraw.mcmod.createsdelight.misc.VoxelRecord;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

public class MadeVoxelBlockEntity extends SyncedBlockEntity {
    static {
        CreateSDelight.LOGGER.debug("MadeVoxelBlockEntity.<clinit> BlockFoods.BLOCK=" + BlockFoods.BLOCK);
    }
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
        setVoxelRecord(VoxelRecord.of(getWorld(), nbt.getCompound("voxelRecord")));
    }
    public VoxelRecord getVoxelRecord() {
        return voxelRecord;
    }
    public void setVoxelRecord(VoxelRecord voxelRecord) {
        this.voxelRecord = voxelRecord;
        notifyUpdate();
        World world = getWorld();
        if (world == null) {return;}
        world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.REDRAW_ON_MAIN_THREAD);
        if (VoxelRecord.EMPTY.equals(voxelRecord)) {
            world.removeBlock(getPos(), false);
        }
    }
}
