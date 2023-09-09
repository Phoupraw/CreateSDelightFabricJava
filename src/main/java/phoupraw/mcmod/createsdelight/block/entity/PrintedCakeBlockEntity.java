package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.CSDRegistries;

import java.util.*;

public class PrintedCakeBlockEntity extends SmartBlockEntity implements Nameable {
    public static final Comparator<BlockBox> BLOCK_BOX_COMPARATOR = Comparator
      .comparingInt(BlockBox::getMinY)
      .reversed()
      .thenComparingInt(BlockBox::getMinX)
      .thenComparingInt(BlockBox::getMinZ);
    public static @Nullable VoxelCake nbt2content(NbtCompound blockEntityTag) {
        if (blockEntityTag.contains("predefined", NbtElement.STRING_TYPE)) {
            Identifier id = new Identifier(blockEntityTag.getString("predefined"));
            return CSDRegistries.PREDEFINED_CAKE.get(id);
        }
        return VoxelCake.of(blockEntityTag.getCompound("voxelCake"));
    }
    public final Map<Direction, VoxelShape> shapes = new EnumMap<>(Direction.class);
    public @Nullable Identifier predefined;
    private @Nullable VoxelCake voxelCake = VoxelCake.empty();
    private @Nullable Text customName;
    public PrintedCakeBlockEntity(BlockPos pos, BlockState state) {
        this(CSDBlockEntityTypes.PRINTED_CAKE, pos, state);
    }
    public PrintedCakeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    @Override
    public Text getName() {
        return Objects.requireNonNullElse(getCustomName(), getCachedState().getBlock().getName());
    }
    @Nullable
    @Override
    public Text getCustomName() {
        return customName;
    }
    public void setCustomName(@Nullable Text customName) {
        this.customName = customName;
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    }
    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        writeBlockEntityTag(tag);
        if (getCustomName() != null) {
            tag.putString("CustomName", Text.Serializer.toJson(getCustomName()));
        }
    }
    public NbtCompound writeBlockEntityTag(NbtCompound nbt) {
        if (getVoxelCake() != null) {
            nbt.put("voxelCake", getVoxelCake().toNbt());
        }
        return nbt;
    }
    @Override
    protected void read(NbtCompound tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        if (tag.contains("CustomName", NbtElement.STRING_TYPE)) {
            setCustomName(Text.Serializer.fromJson(tag.getString("CustomName")));
        } else {
            setCustomName(null);
        }
        setVoxelCake(nbt2content(tag));
        shapes.clear();
        World world = getWorld();
        if (world == null) return;
        world.updateListeners(getPos(), getCachedState(), getCachedState(), Block.REDRAW_ON_MAIN_THREAD);
    }
    public @Nullable VoxelCake getVoxelCake() {
        return voxelCake;
    }
    public void setVoxelCake(@Nullable VoxelCake voxelCake) {
        this.voxelCake = voxelCake;
        if (voxelCake != null) {
            sendData();
        } else {
            CreateSDelight.LOGGER.error("在坐标(%s)的蛋糕的NBT不正确！".formatted(getPos().toShortString()));
        }
    }
}
