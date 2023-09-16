package phoupraw.mcmod.createsdelight.block.entity;

import com.google.common.collect.Iterables;
import com.simibubi.create.AllSpecialTextures;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.outliner.AABBOutline;
import com.simibubi.create.foundation.utility.VecHelper;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.block.CakeOvenBlock;
import phoupraw.mcmod.createsdelight.misc.DefaultedMap;
import phoupraw.mcmod.createsdelight.misc.SupplierDefaultedMap;
import phoupraw.mcmod.createsdelight.misc.VoxelRecord;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;

import java.util.*;

import static phoupraw.mcmod.createsdelight.block.entity.CakeOvenBlockEntity.expanded;

public class VoxelMakerBlockEntity extends KineticBlockEntity {
    public double prevOutline0Len;
    public double outline0Len;
    public double outlineLinger;
    protected TriState working = TriState.DEFAULT;
    public DefaultedMap<Integer, Object> outlineSlots = new SupplierDefaultedMap<>(new HashMap<>(), Object::new);
    public Map<Integer, @Nullable VoxelRecord> len1s = new HashMap<>();
    public VoxelMakerBlockEntity(BlockPos pos, BlockState state) {
        this(CSDBlockEntityTypes.VOXEL_MAKER, pos, state);
    }
    public VoxelMakerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    @Override
    public void tick() {
        super.tick();
        if (!getWorking().get()) return;
        World world = getWorld();
        int edgeLen = getBehaviour(ScrollValueBehaviour.TYPE).getValue();
        Vec3i size = new Vec3i(edgeLen, edgeLen, edgeLen);
        RailShape facing = getCachedState().get(CakeOvenBlock.FACING);
        var biDirection = CakeOvenBlock.BI_DIRECTION_MAP.get(facing);
        BlockPos origin = getPos().up();
        BlockBox bound = new BlockBox(origin);
        Iterable<Direction> triDirection = Iterables.concat(biDirection.values(), List.of(Direction.UP));
        for (Direction direction : triDirection) {
            bound = expanded(bound, direction, edgeLen - 1);
        }
        BlockPos vertex000 = new BlockPos(bound.getMinX(), bound.getMinY(), bound.getMinZ());
        double step = (Math.abs(getSpeed())) / 256 / 1;
        prevOutline0Len = outline0Len;
        outline0Len += step;
        double len0 = outline0Len;
        Box outline0 = new Box(origin);//扩大的框
        for (Direction direction : biDirection.values()) {
            outline0 = expanded(outline0, direction, MathHelper.clamp(len0 - 1, 0, edgeLen - 1));
        }
        outline0 = expanded(outline0, Direction.UP, Math.min(len0, edgeLen) - 1);
        Box actual0 = outline0;//这个初始化只是占位符，在客户端设置真正的值
        if (world.isClient()) {
            CreateClient.OUTLINER
              .chaseAABB(this, outline0)
              .withFaceTexture(AllSpecialTextures.CHECKERED)
              .colored(0xFFAA00)
              .lineWidth(1 / 16f);
            if (CreateClient.OUTLINER.getOutlines().get(this).getOutline() instanceof AABBOutline aabbOutline) {
                actual0 = aabbOutline.getBounds();//outline0的实际大小
            }
        }
        boolean finished = false;
        for (int len1 = 1; len1 <= len0 && len1 <= edgeLen; len1++) {
            double len2 = 2 * len1 - len0;
            finished = true;
            if (len2 < 0) continue;
            len2 = MathHelper.clamp(len2, (double) len1 / edgeLen, actual0.getYLength());//防止outline1超出outline0
            Box outline1 = new Box(origin);//缩小的框
            BlockPos end = origin;
            Collection<BlockPos> starts = new LinkedList<>();
            for (Direction direction : triDirection) {
                outline1 = expanded(outline1, direction, len2 - 1);
                end = end.offset(direction, len1 - 1);
                starts.add(origin.offset(direction, len1 - 1));
            }
            if (!len1s.containsKey(len1)) {
                Collection<Iterable<BlockPos>> posIterable0s = new LinkedList<>();
                for (BlockPos start : starts) {
                    posIterable0s.add(BlockPos.iterate(start, end));
                }
                Iterable<BlockPos> posIterable1 = Iterables.concat(posIterable0s);
                Map<BlockPos, BlockState> blocks = new HashMap<>();
                for (BlockPos pos1 : posIterable1) {
                    if (blocks.containsKey(pos1)) continue;
                    BlockState blockState = world.getBlockState(pos1);
                    if (blockState.isAir()) continue;
                    blocks.put(pos1.subtract(vertex000), blockState);
                    world.setBlockState(pos1, world.getFluidState(pos1).getBlockState(), Block.NOTIFY_NEIGHBORS);
                }
                if (!blocks.isEmpty()) {
                    len1s.put(len1, new VoxelRecord(blocks, size));
                }
            }
            if (world.isClient()) {
                CreateClient.OUTLINER
                  .chaseAABB(outlineSlots.get(len1), outline1)
                  .withFaceTexture(AllSpecialTextures.THIN_CHECKERED)
                  .colored(0xFFAA00)
                  .lineWidth(1 / 16f);
            }
            finished = false;
        }
        if (finished) {
            outlineLinger += step;
            if (outlineLinger >= 1) {
                world.setBlockState(origin, CSDBlocks.MADE_VOXEL.getDefaultState());
                ((MadeVoxelBlockEntity) world.getBlockEntity(origin)).voxelRecord = new VoxelRecord(len1s.values().stream().filter(Objects::nonNull).map(VoxelRecord::blocks).collect(HashMap::new, Map::putAll, Map::putAll), size);
                setWorking(TriState.FALSE);
            }
        }
    }
    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.putString("working", getWorking().toString());
        if (!len1s.isEmpty()) {

        }
    }
    @Override
    protected void read(NbtCompound tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        setWorking(tag.contains("working", NbtElement.STRING_TYPE) ? TriState.valueOf(tag.getString("working")) : TriState.DEFAULT);
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(new Scroll(this));
    }
    @Override
    protected Box createRenderBoundingBox() {
        var facing = getCachedState().get(CakeOvenBlock.FACING);
        var biDirection = CakeOvenBlock.BI_DIRECTION_MAP.get(facing);
        int edgeLen = getBehaviour(ScrollValueBehaviour.TYPE).getValue();
        Box box = new Box(getPos());
        for (Direction direction : biDirection.values()) {
            box = expanded(box, direction, edgeLen - 1);
        }
        box = expanded(box, Direction.UP, edgeLen);
        return box;
    }
    public TriState getWorking() {
        return working;
    }
    public void setWorking(TriState working) {
        this.working = working;
        if (!getWorking().get()) {
            prevOutline0Len = outline0Len = 0;
            outlineLinger = 0;
            len1s.clear();
        }
    }
    @ApiStatus.Internal
    public static class InWorldSlot extends ValueBoxTransform.Sided {
        @Override
        protected Vec3d getSouthLocation() {
            return VecHelper.voxelSpace(8, 13, 16);
        }
        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            return CakeOvenBlock.BI_DIRECTION.get(state.get(CakeOvenBlock.FACING)).contains(direction.getOpposite());
        }
    }
    @ApiStatus.Internal
    public static class Scroll extends ScrollValueBehaviour {
        public Scroll(VoxelMakerBlockEntity be) {this(Text.of("蛋糕边长"), be, new InWorldSlot());}
        public Scroll(Text label, VoxelMakerBlockEntity be, ValueBoxTransform slot) {
            super(label, be, slot);
            between(1, 64);
            setValue(16);
            withCallback(newValue -> getBlockEntity().invalidateRenderBoundingBox());
        }
        public VoxelMakerBlockEntity getBlockEntity() {
            return (VoxelMakerBlockEntity) blockEntity;
        }
        @Override
        public void setValueSettings(PlayerEntity player, ValueSettings valueSetting, boolean ctrlDown) {
            if (getBlockEntity().getWorking().get()) return;
            super.setValueSettings(player, valueSetting, ctrlDown);
        }
    }
}
