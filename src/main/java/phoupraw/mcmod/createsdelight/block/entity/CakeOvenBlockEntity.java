package phoupraw.mcmod.createsdelight.block.entity;

import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import com.simibubi.create.AllSpecialTextures;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.outliner.Outliner;
import com.simibubi.create.foundation.utility.VecHelper;
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
import org.joml.Vector3i;
import phoupraw.mcmod.createsdelight.block.CakeOvenBlock;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.misc.BlockPosVoxelCake;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    public static void combine(World world, BlockPos origin, int edgeLen, BlockPos pos1, Direction direction) {
        BlockPos pos2 = pos1.offset(direction);
        if (world.getBlockState(pos2).isOf(CSDBlocks.IN_PROD_CAKE)) {
            world.setBlockState(pos1, CSDBlocks.IN_PROD_CAKE.getDefaultState());
            InProdCakeBlockEntity inProd1 = (InProdCakeBlockEntity) world.getBlockEntity(pos1);
            InProdCakeBlockEntity inProd2 = (InProdCakeBlockEntity) world.getBlockEntity(pos2);
            if (inProd2.getVoxelCake() instanceof BlockPosVoxelCake voxelCake2) {
                Multimap<CakeIngredient, BlockPos> map = MultimapBuilder.hashKeys().arrayListValues().build(voxelCake2.blockPosContent);
                if (inProd1.getVoxelCake() instanceof BlockPosVoxelCake voxelCake1) {
                    map.putAll(voxelCake1.blockPosContent);
                }
                inProd1.setVoxelCake(BlockPosVoxelCake.of(edgeLen, map));
            }
            world.removeBlock(pos2, false);
        }
    }
    public static <T> @UnmodifiableView Iterable<T> appended(Iterable<T> first, T second) {
        return Iterables.concat(first, List.of(second));
    }
    @ApiStatus.Internal
    protected long timeBegin = -1;
    @ApiStatus.Internal
    protected @Nullable Text customName;
    public double outline0Len;
    public int outlineLinger;
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
            //CreateClient.OUTLINER.remove(this);
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
        Direction highlightFace = null;
        outline0Len += Math.abs(getSpeed()) / 256;
        double len0 = outline0Len;
        Box outline0 = new Box(getPos().up());//扩大的框
        for (Direction direction : biDirection.values()) {
            outline0 = expanded(outline0, direction, MathHelper.clamp(len0 - 1, 0, edgeLen - 1));
        }
        outline0 = expanded(outline0, Direction.UP, Math.min(len0, edgeLen) - 1);
        if (world.isClient()) {
            CreateClient.OUTLINER
              .chaseAABB(this, outline0)
              .withFaceTexture(AllSpecialTextures.CHECKERED)
              .colored(0xffaa00)
              .lineWidth(1 / 16f);
        }
        if (len0 > 0) {
            boolean finished = false;
            for (int i = 1; i < len0 && i <= edgeLen; i++) {
                finished = true;
                double len1 = 2 * i - len0;
                if (len1 < 0) continue;
                Box outline1 = new Box(getPos().up());
                for (Direction direction : appended(biDirection.values(), Direction.UP)) {
                    outline1 = expanded(outline1, direction, Math.max(0, len1 - 1));
                }
                if (world.isClient()) {
                    CreateClient.OUTLINER
                      .chaseAABB(i, outline1)
                      .withFaceTexture(AllSpecialTextures.HIGHLIGHT_CHECKERED)
                      .colored(0xffaa00)
                      .lineWidth(1 / 16f);
                }
                finished = false;
            }
            if (finished) {
                outlineLinger--;
                if (outlineLinger <= 0) {
                    setTimeBegin(-2);
                }
            }
        }
    }
    public void tick2() {
        super.tick();
        if (isNotWorking()) return;
        World world = getWorld();
        int elapsed = (int) (world.getTime() - getTimeBegin());
        int edgeLen = getBehaviour(ScrollValueBehaviour.TYPE).getValue();
        RailShape facing = getCachedState().get(CakeOvenBlock.FACING);
        var biDirection = CakeOvenBlock.BI_DIRECTION_MAP.get(facing);
        Direction directionX = biDirection.get(Direction.Axis.X);
        Direction directionZ = biDirection.get(Direction.Axis.Z);
        BlockPos origin = getPos().up();
        BlockBox bound = new BlockBox(origin);
        Box outline0;//扩大的框
        Collection<Box> outlines;
        Direction highlightFace = null;
        if (elapsed <= edgeLen) {
            for (Direction direction : appended(biDirection.values(), Direction.UP)) {
                bound = expanded(bound, direction, Math.max(0, elapsed - 1));
            }
            for (int x = bound.getMinX(); x <= bound.getMaxX(); x++) {
                for (int y = bound.getMinY(); y <= bound.getMaxY(); y++) {
                    for (int z = bound.getMinZ(); z <= bound.getMaxZ(); z++) {
                        BlockPos pos1 = new BlockPos(x, y, z);
                        CakeIngredient cakeIngredient = CakeIngredient.LOOKUP.find(world, pos1, null);
                        if (cakeIngredient == null) continue;
                        if (world.setBlockState(pos1, CSDBlocks.IN_PROD_CAKE.getDefaultState())) {
                            InProdCakeBlockEntity inProd = (InProdCakeBlockEntity) world.getBlockEntity(pos1);
                            Vector3i relative0 = new Vector3i(
                              Math.abs(pos1.getX() - origin.getX()),
                              Math.abs(pos1.getY() - origin.getY()),
                              Math.abs(pos1.getZ() - origin.getZ()));
                            if (directionX == (Direction.WEST)) {
                                relative0.x = edgeLen - 1 - relative0.x;
                            }
                            if (directionZ == (Direction.NORTH)) {
                                relative0.z = edgeLen - 1 - relative0.z;
                            }
                            BlockPos relative = new BlockPos(relative0.x, relative0.y, relative0.z);
                            inProd.setVoxelCake(BlockPosVoxelCake.of(edgeLen, Multimaps.forMap(Map.of(cakeIngredient, relative))));
                            inProd.edgeLen = edgeLen;
                            inProd.relative = relative;
                        }
                    }
                }
            }
        } else {
            int elapsed1 = elapsed - edgeLen;
            int edgeLen1 = edgeLen - 1;
            if (edgeLen1 - elapsed1 >= 0) {
                highlightFace = Direction.UP;
                for (Direction direction : biDirection.values()) {
                    bound = expanded(bound, direction, edgeLen1);
                }
                bound = expanded(bound, Direction.UP, edgeLen1 - elapsed1);
                if (bound.getBlockCountY() < edgeLen) {
                    int y = bound.getMaxY();
                    for (int x = bound.getMinX(); x <= bound.getMaxX(); x++) {
                        for (int z = bound.getMinZ(); z <= bound.getMaxZ(); z++) {
                            combine(world, origin, edgeLen, new BlockPos(x, y, z), Direction.UP);
                        }
                    }
                }
            } else if (edgeLen1 * 2 - elapsed1 >= 0) {
                highlightFace = directionX;
                bound = expanded(bound, directionX, edgeLen1 * 2 - elapsed1);
                bound = expanded(bound, directionZ, edgeLen1);
                if (bound.getBlockCountX() < edgeLen) {
                    int x = directionX == Direction.EAST ? bound.getMaxX() : bound.getMinX();
                    int y = origin.getY();
                    for (int z = bound.getMinZ(); z <= bound.getMaxZ(); z++) {
                        combine(world, origin, edgeLen, new BlockPos(x, y, z), directionX);
                    }
                }
            } else if (edgeLen1 * 3 - elapsed1 >= 0) {
                highlightFace = directionZ;
                bound = expanded(bound, directionZ, edgeLen1 * 3 - elapsed1);
                if (bound.getBlockCountZ() < edgeLen) {
                    int z = directionZ == Direction.SOUTH ? bound.getMaxZ() : bound.getMinZ();
                    combine(world, origin, edgeLen, new BlockPos(origin.getX(), origin.getY(), z), directionZ);
                }
            } else {

                setTimeBegin(-1);
                return;
            }
        }
        if (world.isClient()) {
            CreateClient.OUTLINER
              .chaseAABB(this, Box.from(bound))
              .withFaceTextures(AllSpecialTextures.CHECKERED, AllSpecialTextures.HIGHLIGHT_CHECKERED)
              .colored(0xffaa00)
              .lineWidth(1 / 16f)
              .highlightFace(highlightFace);
            CreateClient.OUTLINER.keep(this);
        }
    }
    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        if (getCustomName() != null) {
            tag.putString("CustomName", Text.Serializer.toJson(getCustomName()));
        }
        tag.putLong("timeBegin", getTimeBegin());
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
        scroll.setValue(1);
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
            outline0Len = 0;
            outlineLinger = Outliner.OutlineEntry.FADE_TICKS * 2;
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
