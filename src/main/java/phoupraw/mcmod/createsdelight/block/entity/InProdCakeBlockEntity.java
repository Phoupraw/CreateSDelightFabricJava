package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.foundation.blockEntity.SyncedBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

public class InProdCakeBlockEntity extends SyncedBlockEntity {
    public static final int SHRINKING_TICKS = 20;
    public static final int MOVING_TICKS = 20;
    public CakeIngredient cakeIngredient;
    public BlockPos origin;
    public @Nullable BlockPos relative;
    public @Range(from = 0, to = Byte.MAX_VALUE) int edgeLen;
    public CakeOvenBlockEntity oven;
    @Nullable protected VoxelCake voxelCake;
    @Deprecated
    protected @Range(from = 0, to = 1) float progress;
    @Deprecated
    public float preProgress;
    public @Nullable Direction direction;
    public volatile long timeBegin = -1;
    public InProdCakeBlockEntity(BlockPos pos, BlockState state) {this(CSDBlockEntityTypes.IN_PROD_CAKE, pos, state);}
    public InProdCakeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        setVoxelCake(VoxelCake.of(nbt.getCompound("voxelCake")));
        if (nbt.contains("relative", NbtElement.COMPOUND_TYPE)) {
            relative = NbtHelper.toBlockPos(nbt.getCompound("relative"));
        } else {
            relative = null;
        }
        edgeLen = nbt.getByte("edgeLen");
        progress = nbt.getFloat("progress");
        if (nbt.contains("direction", NbtElement.BYTE_TYPE)) {
            direction = Direction.byId(nbt.getByte("direction"));
        } else {
            direction = null;
        }
        if (nbt.contains("timeBegin", NbtElement.LONG_TYPE)) {
            timeBegin = nbt.getLong("timeBegin");
        } else {
            timeBegin = -1;
        }
    }
    @Override
    public void readClient(@NotNull NbtCompound nbt) {
        super.readClient(nbt);
    }
    @Override
    public NbtCompound writeClient(@NotNull NbtCompound nbt) {
        return super.writeClient(nbt);
    }
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (getVoxelCake() != null) {
            nbt.put("voxelCake", getVoxelCake().toNbt());
        }
        if (relative != null) {
            nbt.put("relative", NbtHelper.fromBlockPos(relative));
        }
        if (direction != null) {
            nbt.putByte("direction", (byte) direction.ordinal());
        }
        nbt.putByte("edgeLen", (byte) edgeLen);
        nbt.putFloat("progress", progress);
        if (timeBegin != -1)
            nbt.putLong("timeBegin", timeBegin);
    }
    public @Nullable VoxelCake getVoxelCake() {
        return voxelCake;
    }
    public void setVoxelCake(@Nullable VoxelCake voxelCake) {
        this.voxelCake = voxelCake;
        //sendData();
    }
    public float getProgress() {
        return progress;
    }
    public void setProgress(float progress) {
        this.preProgress = this.progress = MathHelper.clamp(progress, 0, 1);
    }
    public void addProgress(float add) {
        preProgress = getProgress();
        this.progress += add;
    }
}
