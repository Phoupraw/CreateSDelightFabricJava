package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.foundation.blockEntity.SyncedBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.block.MadeVoxelBlock;
import phoupraw.mcmod.createsdelight.misc.DefaultedMap;
import phoupraw.mcmod.createsdelight.misc.FunctionDefaultedMap;
import phoupraw.mcmod.createsdelight.misc.VoxelRecord;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

import java.util.EnumMap;

public class MadeVoxelBlockEntity extends SyncedBlockEntity {
    public static MadeVoxelBlockEntity of(BlockPos pos, BlockState state) {
        return new MadeVoxelBlockEntity(CSDBlockEntityTypes.MADE_VOXEL, pos, state);
    }
    protected VoxelRecord voxelRecord;
    public final DefaultedMap<Direction, @Nullable VoxelShape> shapes = new FunctionDefaultedMap<>(new EnumMap<>(Direction.class), facing -> {
        if (voxelRecord == null) return null;
        return MadeVoxelBlock.shape(voxelRecord);
    });
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
        sendData();
        shapes.clear();
        World world = getWorld();
        if (world != null) {
            world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.REDRAW_ON_MAIN_THREAD);
        }
    }
}
