package phoupraw.mcmod.createsdelight.block.entity;

import com.google.common.collect.Iterables;
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
import phoupraw.mcmod.createsdelight.misc.BlocksVoxelCake;
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
    @Override
    public void tick() {
        super.tick();
        if (isNotWorking()) return;
        World world = getWorld();
        @SuppressWarnings("ConstantConditions") int elapsed = (int) (world.getTime() - timeBegin);
        int edgeLen = getBehaviour(ScrollValueBehaviour.TYPE).getValue();
        Set<Direction> biDirection = CakeOvenBlock.BI_DIRECTION.get(getCachedState().get(CakeOvenBlock.FACING));
        BlockPos origin = getPos().up();
        BlockBox bound = new BlockBox(origin);
        Direction highlightFace = null;
        int elapsed1 = elapsed - 1;
        if (elapsed1 <= 0) {

        } else if (elapsed1 <= edgeLen) {
            for (Direction direction : appended(biDirection, Direction.UP)) {
                bound = expanded(bound, direction, elapsed1 - 1);
            }
            for (int x = bound.getMinX(); x <= bound.getMaxX(); x++) {
                for (int y = bound.getMinY(); y <= bound.getMaxY(); y++) {
                    for (int z = bound.getMinZ(); z <= bound.getMaxZ(); z++) {
                        BlockPos pos1 = new BlockPos(x, y, z);
                        CakeIngredient cakeIngredient = CakeIngredient.LOOKUP.find(world, pos1, null);
                        if (cakeIngredient == null) {
                            continue;
                        }
                        boolean toInit = world.setBlockState(pos1, CSDBlocks.IN_PROD_CAKE.getDefaultState());
                        if (!(world.getBlockEntity(pos1) instanceof InProdCakeBlockEntity inProd)) return;
                        if (toInit) {
                            Vector3i pos20 = new Vector3i(
                              Math.abs(pos1.getX() - origin.getX()),
                              Math.abs(pos1.getY() - origin.getY()),
                              Math.abs(pos1.getZ() - origin.getZ()));
                            if (biDirection.contains(Direction.WEST)) {
                                pos20.x = edgeLen - 1 - pos20.x;
                            }
                            if (biDirection.contains(Direction.NORTH)) {
                                pos20.z = edgeLen - 1 - pos20.z;
                            }
                            BlockPos pos2 = new BlockPos(pos20.x, pos20.y, pos20.z);
                            inProd.setVoxelCake(new BlocksVoxelCake(edgeLen, Multimaps.forMap(Map.of(cakeIngredient, pos2))));
                            inProd.edgeLen = edgeLen;
                            inProd.relative = pos2;
                        } else {
                            inProd.setProgress(inProd.getProgress() + InProdCakeBlockEntity.STEP);
                        }
                    }
                }
            }
        } else {
            int elapsed2 = (elapsed1 - edgeLen) / 5;
            int edgeLen1 = edgeLen - 1;
            if (edgeLen1 - elapsed2 >= 0) {
                highlightFace = Direction.UP;
                for (Direction direction : biDirection) {
                    bound = expanded(bound, direction, edgeLen1);
                }
                bound = expanded(bound, Direction.UP, edgeLen1 - elapsed2);
            } else if (edgeLen1 * 2 - elapsed2 >= 0) {
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
            } else {
                timeBegin = -1;
                return;
            }
        }
        CreateClient.OUTLINER
          .chaseAABB(this, Box.from(bound))
          .withFaceTextures(AllSpecialTextures.CHECKERED, AllSpecialTextures.HIGHLIGHT_CHECKERED)
          .colored(0xffaa00)
          .lineWidth(1 / 16f)
          .highlightFace(highlightFace);
        CreateClient.OUTLINER.keep(this);
    }

    public static <T> @UnmodifiableView Iterable<T> appended(Iterable<T> first, T second) {
        return Iterables.concat(first, List.of(second));
    }
    private @Nullable Text customName;
    public long timeBegin = -1;

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
            tag.putLong("timeBegin", timeBegin);
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
            timeBegin = tag.getLong("timeBegin");
        } else {
            timeBegin = -1;
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
    public boolean isNotWorking() {return timeBegin == -1;}

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
