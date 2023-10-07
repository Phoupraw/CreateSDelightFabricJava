package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.foundation.blockEntity.SyncedBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.misc.BlockFoods;
import phoupraw.mcmod.createsdelight.misc.VoxelRecord;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

public class MadeVoxelBlockEntity extends SyncedBlockEntity implements Nameable {
    static {
        CreateSDelight.LOGGER.debug("MadeVoxelBlockEntity.<clinit> BlockFoods.BLOCK=" + BlockFoods.BLOCK);
    }
    public static MadeVoxelBlockEntity of(BlockPos pos, BlockState state) {
        return new MadeVoxelBlockEntity(CSDBlockEntityTypes.MADE_VOXEL, pos, state);
    }
    protected VoxelRecord voxelRecord;
    protected @Nullable Text customName;
    public MadeVoxelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (getVoxelRecord() != null) {
            nbt.put("voxelRecord", getVoxelRecord().write(new NbtCompound()));
        }
        if (getCustomName() != null) {
            nbt.putString("CustomName", Text.Serializer.toJson(getCustomName()));
        }
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        setVoxelRecord(VoxelRecord.of(getWorld(), nbt.getCompound("voxelRecord")));
        if (nbt.contains("CustomName", NbtElement.STRING_TYPE)) {
            setCustomName(Text.Serializer.fromJson(nbt.getString("CustomName")));
        }
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
    @Override
    public @NotNull Text getName() {
        return getCustomName() != null ? getCustomName() : getCachedState().getBlock().getName();
    }
    @Override
    public @Nullable Text getCustomName() {
        return customName;
    }
    public void setCustomName(@Nullable Text customName) {
        this.customName = customName;
    }
}
