package phoupraw.mcmod.createsdelight.block.entity;

import com.google.common.collect.HashMultimap;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.cake.VoxelCakeRecord;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;
@Deprecated
public class MovingCakeBlockEntity extends BlockEntity {

    public static VoxelCake combineSame(VoxelCake cake1, VoxelCake cake2) {
        Vec3i size1 = cake1.getSize();
        if (!size1.equals(cake2.getSize())) {
            throw new IllegalArgumentException(size1 + " " + cake2.getSize());
        }
        var content1 = HashMultimap.create(cake1.getContent());
        content1.putAll(cake2.getContent());
        return VoxelCakeRecord.of(content1, size1);
    }

    public VoxelCake voxelCake;
    public @NotNull Direction direction = Direction.UP;
    public double progress = 0;
    public BlockPos ovenPos;
    public CakeOvenBlockEntity oven;

    public MovingCakeBlockEntity(BlockPos pos, BlockState state) {this(CSDBlockEntityTypes.MOVING_CAKE, pos, state);}

    public MovingCakeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public @NotNull Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public @NotNull NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    @Override
    public String toString() {
        return "MakingCakeBlockEntity{" +
               "voxelCake=" + voxelCake +
               ", direction=" + direction +
               ", progress=" + progress +
               ", ovenPos=" + ovenPos +
               ", pos=" + pos +
               '}';
    }

    public void tick() {
        progress = Math.min(1, progress + 1.0 / 4);
        if (progress >= 1) {
            BlockPos pos = getPos();
            BlockPos pos1 = pos.offset(direction);
            World world = getWorld();
            //noinspection ConstantConditions
            if (world.getBlockEntity(pos1) instanceof MovingCakeBlockEntity combined) {
                combined.voxelCake = combineSame(this.voxelCake, combined.voxelCake);
                combined.setDirection();
            } else if (world.getBlockState(pos1).isAir()) {
                world.setBlockState(pos1, getCachedState());
                MovingCakeBlockEntity copy = (MovingCakeBlockEntity) world.getBlockEntity(pos1);
                //noinspection ConstantConditions
                copy.voxelCake = this.voxelCake;
                copy.setDirection();
            } else {
                world.breakBlock(pos, true);
            }
        }
    }

    //@Override
    //public String toString() {
    //    return "MakingCakeBlockEntity{" +
    //           "voxelCake=" + voxelCake +
    //           ", direction=" + direction +
    //           ", progress=" + progress +
    //           ", ovenPos=" + ovenPos +
    //           ", pos=" + pos +
    //           '}';
    //}

    public void setDirection() {
        BlockPos pos = getPos();
        if (pos.getY() > ovenPos.getY() + 1) {
            direction = Direction.DOWN;
        } else if (pos.getX() < ovenPos.getX()) {
            direction = Direction.EAST;
        } else if (pos.getX() > ovenPos.getX()) {
            direction = Direction.WEST;
        } else if (pos.getZ() < ovenPos.getZ()) {
            direction = Direction.SOUTH;
        } else if (pos.getZ() > ovenPos.getZ()) {
            direction = Direction.NORTH;
        } else {
            direction = Direction.UP;
        }
    }

}
