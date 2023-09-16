package phoupraw.mcmod.createsdelight.block.entity;

import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.simibubi.create.AllSpecialTextures;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.outliner.AABBOutline;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.RailShape;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import phoupraw.mcmod.createsdelight.block.CakeOvenBlock;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.misc.BlockPosVoxelCake;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;

import java.util.*;
import java.util.function.Predicate;

//TODO 像结构方块那样，用两个名称相同的方块作为长方体的体对角线端点，以此只要两个方块就能确定一个长方体。内部可储存燃料，但是不会自动燃烧，而是在接收到红石信号后开始燃烧。有GUI，用于编辑名称，名称在挖掘后会保留，放置后会保留。可以被扳手潜行右键拆卸，会掉落内部的燃料。燃料的消耗是瞬间的，概率的，一次燃烧开始时，蛋糕就已经烘焙完毕，燃料也消耗完毕，只是动画效果持续。手持扳手右键任一角点时，会出现一个类似于蓝图的框（但是是黄色的）来指示烘焙范围，如果同名角点数量不为2，则不会显示框，而是会用红色框高亮自身，并在消息栏报告错误。可以用剪贴板复制粘贴名字。动画效果：两个角点向中心喷撒火焰粒子和蒸汽粒子，粒子数量与持续时间与框的大小成正相关。
public class CakeOvenBlockEntity extends KineticBlockEntity implements Nameable {
    public static BlockBox toBlockBox(Box box) {
        return new BlockBox((int) box.minX, (int) box.minY, (int) box.minZ, (int) box.maxX, (int) box.maxY, (int) box.maxZ);
    }
    public static Box toBox(BlockBox box) {
        return new Box(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ());
    }
    public static Box expanded(Box box, Direction direction, double extent) {
        return switch (direction) {
            case WEST -> box.withMinX(box.minX - extent);
            case EAST -> box.withMaxX(box.maxX + extent);
            case DOWN -> box.withMinY(box.minY - extent);
            case UP -> box.withMaxY(box.maxY + extent);
            case NORTH -> box.withMinZ(box.minZ - extent);
            case SOUTH -> box.withMaxZ(box.maxZ + extent);
        };
    }
    public static BlockBox expanded(BlockBox box, Direction direction, int extent) {
        return toBlockBox(expanded(toBox(box), direction, extent));
    }
    /**
     * 在调用处按<code>Ctrl+Alt+N</code>内联。
     * @param box 遍历这个区域内的每个坐标，闭区间。
     * @param loopBody 循环体，如果返回<code>true</code>就退出整个循环。
     */
    public static void forEach(BlockBox box, Predicate<? super BlockPos> loopBody) {
        outer:
        for (int x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (int y = box.getMinY(); y <= box.getMaxY(); y++) {
                for (int z = box.getMinZ(); z <= box.getMaxZ(); z++) {
                    if (loopBody.test(new BlockPos(x, y, z))) {
                        break outer;
                    }
                }
            }
        }
    }
    public static <T> @UnmodifiableView Iterable<T> appended(Iterable<T> first, T second) {
        return Iterables.concat(first, List.of(second));
    }
    @ApiStatus.Internal
    protected long timeBegin = -1;
    @ApiStatus.Internal
    protected @Nullable Text customName;
    public double prevOutline0Len;
    public double outline0Len;
    public double outlineLinger = 1;
    public Map<Integer, BlockPosVoxelCake> len1s = new HashMap<>();
    //public Collection<BlockPos> toRemove = new ArrayList<>();
    public CakeOvenBlockEntity(BlockPos pos, BlockState state) {
        this(CSDBlockEntityTypes.CAKE_OVEN, pos, state);
    }
    public CakeOvenBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    @Override
    public void tick() {
        super.tick();
        if (isNotWorking()) {
            return;
        }
        World world = getWorld();
        int elapsed = (int) (world.getTime() - getTimeBegin());
        int edgeLen = getBehaviour(ScrollValueBehaviour.TYPE).getValue();
        RailShape facing = getCachedState().get(CakeOvenBlock.FACING);
        var biDirection = CakeOvenBlock.BI_DIRECTION_MAP.get(facing);
        Direction directionX = biDirection.get(Direction.Axis.X);
        Direction directionZ = biDirection.get(Direction.Axis.Z);
        BlockPos origin = getPos().up();
        BlockBox bound = new BlockBox(origin);
        Iterable<Direction> triDirection = appended(biDirection.values(), Direction.UP);
        for (Direction direction : triDirection) {
            bound = expanded(bound, direction, edgeLen - 1);
        }
        double step = (Math.abs(getSpeed())) / 256 / 2;
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
            if (len2 < 0) {
                continue;
            }
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
                var pair = BlockPosVoxelCake.of(world, bound, posIterable1);
                if (pair == null) {
                    len1s.put(len1, null);
                } else {
                    len1s.put(len1, pair.getLeft());
                    for (BlockPos pos2 : pair.getRight()) {
                        world.setBlockState(pos2, world.getFluidState(pos2).getBlockState(), Block.NOTIFY_NEIGHBORS);
                    }
                }
            }
            if (world.isClient()) {
                CreateClient.OUTLINER
                  .chaseAABB(len1, outline1)
                  .withFaceTexture(AllSpecialTextures.THIN_CHECKERED)
                  .colored(0xFFAA00)
                  .lineWidth(1 / 16f);
            }
            finished = false;
        }
        if (finished) {
            outlineLinger -= step;
            if (outlineLinger <= 0) {
                Multimap<CakeIngredient, BlockPos> blockPosContent = MultimapBuilder.hashKeys().arrayListValues().build();
                for (BlockPosVoxelCake cake : len1s.values()) {
                    if (cake == null) continue;
                    blockPosContent.putAll(cake.blockPosContent);
                }
                if (!blockPosContent.isEmpty()) {
                    var entireCake = BlockPosVoxelCake.of(edgeLen, blockPosContent);
                    world.setBlockState(origin, CSDBlocks.PRINTED_CAKE.getDefaultState());
                    PrintedCakeBlockEntity cakeBE = (PrintedCakeBlockEntity) world.getBlockEntity(origin);
                    cakeBE.setVoxelCake(entireCake);
                }
                setTimeBegin(-2);
            }
        }
    }
    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        if (getCustomName() != null) {
            tag.putString("CustomName", Text.Serializer.toJson(getCustomName()));
        }
        tag.putLong("timeBegin", getTimeBegin());
        if (!len1s.isEmpty()) {

        }
    }
    @Override
    protected void read(NbtCompound tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        if (tag.contains("CustomName", NbtElement.STRING_TYPE)) {
            setCustomName(Text.Serializer.fromJson(tag.getString("CustomName")));
        } else {
            setCustomName(null);
        }
        if (tag.contains("timeBegin", NbtElement.LONG_TYPE)) {
            setTimeBegin(tag.getLong("timeBegin"));
        } else {
            setTimeBegin(-1);
        }
    }
    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        ScrollValueBehaviour scroll = new ScrollValueBehaviour(Text.of("蛋糕边长"), this, new InWorldSlot())
          .between(1, 64)
          .onlyActiveWhen(this::isNotWorking)
          .withCallback(newValue -> invalidateRenderBoundingBox());
        behaviours.add(scroll);
        scroll.setValue(16);
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
    public boolean isNotWorking() {return getTimeBegin() < 0;}
    public long getTimeBegin() {
        return timeBegin;
    }
    public void setTimeBegin(long timeBegin) {
        this.timeBegin = timeBegin;
        if (isNotWorking()) {
            prevOutline0Len = outline0Len = 0;
            outlineLinger = 1;
            len1s.clear();
            //toRemove.clear();
        }
    }
    @FunctionalInterface
    public interface TriIntPredicate {
        boolean test(int i, int j, int k);
    }
    public static class InWorldSlot extends ValueBoxTransform.Sided {
        @Override
        protected Vec3d getSouthLocation() {
            return VecHelper.voxelSpace(8, 13, 15.5);
        }
        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            return direction.getAxis().isHorizontal();
        }
    }
}
