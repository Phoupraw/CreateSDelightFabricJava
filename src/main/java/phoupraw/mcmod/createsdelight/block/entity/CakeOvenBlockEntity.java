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
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.joml.Vector3i;
import phoupraw.mcmod.createsdelight.block.CakeOvenBlock;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.misc.BlockPosVoxelCake;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

//TODO 像结构方块那样，用两个名称相同的方块作为长方体的体对角线端点，以此只要两个方块就能确定一个长方体。内部可储存燃料，但是不会自动燃烧，而是在接收到红石信号后开始燃烧。有GUI，用于编辑名称，名称在挖掘后会保留，放置后会保留。可以被扳手潜行右键拆卸，会掉落内部的燃料。燃料的消耗是瞬间的，概率的，一次燃烧开始时，蛋糕就已经烘焙完毕，燃料也消耗完毕，只是动画效果持续。手持扳手右键任一角点时，会出现一个类似于蓝图的框（但是是黄色的）来指示烘焙范围，如果同名角点数量不为2，则不会显示框，而是会用红色框高亮自身，并在消息栏报告错误。可以用剪贴板复制粘贴名字。动画效果：两个角点向中心喷撒火焰粒子和蒸汽粒子，粒子数量与持续时间与框的大小成正相关。
public class CakeOvenBlockEntity extends KineticBlockEntity implements Nameable {
    public static BlockBox toBlockBox(Box box) {
        return new BlockBox((int) box.minX, (int) box.minY, (int) box.minZ, (int) box.maxX, (int) box.maxY, (int) box.maxZ);
    }
    public static Box toBox(BlockBox box) {
        return new Box(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ());
    }
    public static Box expanded(Box box, Direction direction, double value) {
        return switch (direction) {
            case WEST -> box.withMinX(box.minX - value);
            case EAST -> box.withMaxX(box.maxX + value);
            case DOWN -> box.withMinY(box.minY - value);
            case UP -> box.withMaxY(box.maxY + value);
            case NORTH -> box.withMinZ(box.minZ - value);
            case SOUTH -> box.withMaxZ(box.maxZ + value);
        };
    }
    public static BlockBox expanded(BlockBox box, Direction direction, double value) {
        return toBlockBox(expanded(toBox(box), direction, value));
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
                inProd1.setVoxelCake(new BlockPosVoxelCake(edgeLen, map));
            }
            world.removeBlock(pos2, false);
        }
    }
    @Override
    public void tick() {
        super.tick();
        if (isNotWorking()) return;
        World world = getWorld();
        int elapsed = (int) (world.getTime() - getTimeBegin());
        int edgeLen = getBehaviour(ScrollValueBehaviour.TYPE).getValue();
        Set<Direction> biDirection = CakeOvenBlock.BI_DIRECTION.get(getCachedState().get(CakeOvenBlock.FACING));
        Direction directionX = biDirection.contains(Direction.EAST) ? Direction.EAST : Direction.WEST;
        Direction directionZ = biDirection.contains(Direction.SOUTH) ? Direction.SOUTH : Direction.NORTH;
        BlockPos origin = getPos().up();
        BlockBox bound = new BlockBox(origin);
        Direction highlightFace = null;
        if (elapsed <= edgeLen) {
            for (Direction direction : appended(biDirection, Direction.UP)) {
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
                            inProd.setVoxelCake(new BlockPosVoxelCake(edgeLen, Multimaps.forMap(Map.of(cakeIngredient, relative))));
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
                for (Direction direction : biDirection) {
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
    /**
     * 总是抖动，写不出想要的效果，还掉帧，唉，不写了。
     */
    public void tick2() {
        super.tick();
        if (isNotWorking()) return;
        World world = getWorld();
        int elapsed = (int) (world.getTime() - getTimeBegin());
        int edgeLen = getBehaviour(ScrollValueBehaviour.TYPE).getValue();
        Set<Direction> biDirection = CakeOvenBlock.BI_DIRECTION.get(getCachedState().get(CakeOvenBlock.FACING));
        BlockPos origin = getPos().up();
        BlockBox bound = new BlockBox(origin);
        Box outline;
        Direction highlightFace = null;
        int elapsed1 = elapsed - 1;
        if (elapsed1 <= 0) {
            outline = Box.from(bound);
        } else {
            int shrinkingTicks = (int) (InProdCakeBlockEntity.SHRINKING_TICKS * Math.abs(getSpeed()) / 32);
            if (edgeLen - (elapsed1 - shrinkingTicks) >= 0) {
                for (Direction direction : appended(biDirection, Direction.UP)) {
                    bound = expanded(bound, direction, Math.min(edgeLen, elapsed1) - 1);
                }
                outline = Box.from(bound);
                for (int x = bound.getMinX(); x <= bound.getMaxX(); x++) {
                    for (int y = bound.getMinY(); y <= bound.getMaxY(); y++) {
                        for (int z = bound.getMinZ(); z <= bound.getMaxZ(); z++) {
                            BlockPos pos1 = new BlockPos(x, y, z);
                            if (world.getBlockState(pos1).isOf(CSDBlocks.IN_PROD_CAKE)) {
                                InProdCakeBlockEntity inProd = (InProdCakeBlockEntity) world.getBlockEntity(pos1);
                                if (inProd.timeBegin == -1) {
                                    inProd.timeBegin = world.getTime();
                                } else if (world.getTime() - inProd.timeBegin >= shrinkingTicks) {
                                    inProd.direction = null;
                                }
                                continue;
                            }
                            CakeIngredient cakeIngredient = CakeIngredient.LOOKUP.find(world, pos1, null);
                            if (cakeIngredient == null) {
                                continue;
                            }
                            if (world.setBlockState(pos1, CSDBlocks.IN_PROD_CAKE.getDefaultState())) {
                                InProdCakeBlockEntity inProd = (InProdCakeBlockEntity) world.getBlockEntity(pos1);
                                Vector3i relative0 = new Vector3i(
                                  Math.abs(pos1.getX() - origin.getX()),
                                  Math.abs(pos1.getY() - origin.getY()),
                                  Math.abs(pos1.getZ() - origin.getZ()));
                                if (biDirection.contains(Direction.WEST)) {
                                    relative0.x = edgeLen - 1 - relative0.x;
                                }
                                if (biDirection.contains(Direction.NORTH)) {
                                    relative0.z = edgeLen - 1 - relative0.z;
                                }
                                BlockPos relative = new BlockPos(relative0.x, relative0.y, relative0.z);
                                inProd.setVoxelCake(new BlockPosVoxelCake(edgeLen, Multimaps.forMap(Map.of(cakeIngredient, relative))));
                                inProd.edgeLen = edgeLen;
                                inProd.relative = relative;
                                inProd.direction = Direction.UP;
                                inProd.timeBegin = world.getTime();
                            }
                        }
                    }
                }
            } else {
                double elapsed2d = ((double) elapsed1 - shrinkingTicks - edgeLen) / InProdCakeBlockEntity.MOVING_TICKS;
                int elapsed2 = (int) elapsed2d;
                int edgeLen1 = edgeLen - 1;
                if (edgeLen1 - elapsed2 >= 0) {
                    highlightFace = Direction.UP;
                    for (Direction direction : biDirection) {
                        bound = expanded(bound, direction, edgeLen1);
                    }
                    outline = expanded(Box.from(bound), Direction.UP, Math.max(0, edgeLen1 - elapsed2d));
                    bound = expanded(bound, Direction.UP, edgeLen1 - elapsed2);
                    int y = bound.getMaxY();
                    for (int x = bound.getMinX(); x <= bound.getMaxX(); x++) {
                        for (int z = bound.getMinZ(); z <= bound.getMaxZ(); z++) {
                            BlockPos pos1 = new BlockPos(x, y, z);
                            BlockPos pos2 = pos1.up();
                            if (world.getBlockState(pos2).isOf(CSDBlocks.IN_PROD_CAKE)) {
                                world.setBlockState(pos1, CSDBlocks.IN_PROD_CAKE.getDefaultState());
                                InProdCakeBlockEntity inProd1 = (InProdCakeBlockEntity) world.getBlockEntity(pos1);
                                InProdCakeBlockEntity inProd2 = (InProdCakeBlockEntity) world.getBlockEntity(pos2);
                                //inProd1.setProgress(0);
                                if (inProd2.getVoxelCake() instanceof BlockPosVoxelCake voxelCake2) {
                                    Multimap<CakeIngredient, BlockPos> map = MultimapBuilder.hashKeys().arrayListValues().build(voxelCake2.blockPosContent);
                                    if (inProd1.getVoxelCake() instanceof BlockPosVoxelCake voxelCake1) {
                                        map.putAll(voxelCake1.blockPosContent);
                                    }
                                    inProd1.setVoxelCake(new BlockPosVoxelCake(edgeLen, map));
                                    inProd1.sendData();
                                }
                                //inProd2.setVoxelCake(VoxelCake.empty());
                                //world.scheduleBlockTick(pos2, CSDBlocks.IN_PROD_CAKE, 2);
                                world.removeBlock(pos2, false);
                                inProd1.direction = null;
                                inProd1.timeBegin = -1;
                            }
                            if (world.getBlockState(pos1).isOf(CSDBlocks.IN_PROD_CAKE)) {
                                InProdCakeBlockEntity inProd1 = (InProdCakeBlockEntity) world.getBlockEntity(pos1);
                                if (y == bound.getMinY()) {
                                    inProd1.direction = null;
                                } else if (inProd1.direction == null || Math.abs(elapsed2d - elapsed2) < 0.0001) {
                                    inProd1.direction = Direction.DOWN;
                                    inProd1.timeBegin = world.getTime();
                                }
                            }
                        }
                    }
                } /*else if (edgeLen1 * 2 - elapsed2 >= 0) {
                    for (Direction direction : biDirection) {
                        if (direction.getAxis() == Direction.Axis.X) {
                            bound = expanded(bound, direction, edgeLen1 * 2 - elapsed2);
                            highlightFace = direction;
                        } else {
                            bound = expanded(bound, direction, edgeLen1);
                        }
                    }
                } else if (edgeLen1 * 3 - elapsed2 >= 0) {
                    for (Direction direction : biDirection) {
                        if (direction.getAxis() == Direction.Axis.Z) {
                            bound = expanded(bound, direction, edgeLen1 * 3 - elapsed2);
                            highlightFace = direction;
                        }
                    }
                }*/ else {
                    setTimeBegin(-1);
                    return;
                }
            }
        }
        if (world.isClient()) {
            CreateClient.OUTLINER
              .chaseAABB(this, outline)
              .withFaceTextures(AllSpecialTextures.CHECKERED, AllSpecialTextures.HIGHLIGHT_CHECKERED)
              .colored(0xffaa00)
              .lineWidth(1 / 16f)
              .highlightFace(highlightFace);
            CreateClient.OUTLINER.keep(this);
        }
    }
    public static <T> @UnmodifiableView Iterable<T> appended(Iterable<T> first, T second) {
        return Iterables.concat(first, List.of(second));
    }
    private @Nullable Text customName;
    protected long timeBegin = -1;
    public CakeOvenBlockEntity(BlockPos pos, BlockState state) {
        this(CSDBlockEntityTypes.CAKE_OVEN, pos, state);
    }
    public CakeOvenBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        if (getCustomName() != null) {
            tag.putString("CustomName", Text.Serializer.toJson(getCustomName()));
        }
        if (!isNotWorking()) {
            tag.putLong("timeBegin", getTimeBegin());
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
          .onlyActiveWhen(this::isNotWorking);
        behaviours.add(scroll);
        scroll.setValue(1);
    }
    public boolean isNotWorking() {return getTimeBegin() == -1;}
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
    public long getTimeBegin() {
        return timeBegin;
    }
    public void setTimeBegin(long timeBegin) {
        this.timeBegin = timeBegin;
        sendData();
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
