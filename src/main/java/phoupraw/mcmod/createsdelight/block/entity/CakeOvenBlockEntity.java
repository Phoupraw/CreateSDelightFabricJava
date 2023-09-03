package phoupraw.mcmod.createsdelight.block.entity;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.RailShape;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.block.CakeOvenBlock;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

import java.util.List;
import java.util.Objects;

//TODO 像结构方块那样，用两个名称相同的方块作为长方体的体对角线端点，以此只要两个方块就能确定一个长方体。内部可储存燃料，但是不会自动燃烧，而是在接收到红石信号后开始燃烧。有GUI，用于编辑名称，名称在挖掘后会保留，放置后会保留。可以被扳手潜行右键拆卸，会掉落内部的燃料。燃料的消耗是瞬间的，概率的，一次燃烧开始时，蛋糕就已经烘焙完毕，燃料也消耗完毕，只是动画效果持续。手持扳手右键任一角点时，会出现一个类似于蓝图的框（但是是黄色的）来指示烘焙范围，如果同名角点数量不为2，则不会显示框，而是会用红色框高亮自身，并在消息栏报告错误。可以用剪贴板复制粘贴名字。动画效果：两个角点向中心喷撒火焰粒子和蒸汽粒子，粒子数量与持续时间与框的大小成正相关。
public class CakeOvenBlockEntity extends SmartBlockEntity implements Nameable {

    public static final Multimap<RailShape, Direction> TWO_SHAPE = MultimapBuilder.hashKeys().linkedListValues().build();
    static {
        TWO_SHAPE.put(RailShape.NORTH_WEST, Direction.NORTH);
        TWO_SHAPE.put(RailShape.NORTH_WEST, Direction.WEST);
        TWO_SHAPE.put(RailShape.NORTH_EAST, Direction.NORTH);
        TWO_SHAPE.put(RailShape.NORTH_EAST, Direction.EAST);
        TWO_SHAPE.put(RailShape.SOUTH_WEST, Direction.SOUTH);
        TWO_SHAPE.put(RailShape.SOUTH_WEST, Direction.WEST);
        TWO_SHAPE.put(RailShape.SOUTH_EAST, Direction.SOUTH);
        TWO_SHAPE.put(RailShape.SOUTH_EAST, Direction.EAST);
    }
    private @Nullable Text customName;
    public boolean powered;

    public CakeOvenBlockEntity(BlockPos pos, BlockState state) {
        this(CSDBlockEntityTypes.CAKE_OVEN, pos, state);
    }

    public CakeOvenBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(new ScrollValueBehaviour(Text.of("label"), this, new InWorldSlot()).between(1, 64));
    }

    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        if (getCustomName() != null) {
            tag.putString("CustomName", Text.Serializer.toJson(getCustomName()));
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

    public static class InWorldSlot extends ValueBoxTransform.Sided {

        @Override
        protected Vec3d getSouthLocation() {
            return VecHelper.voxelSpace(8, 8, 16.05);
        }

        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            return TWO_SHAPE.get(state.get(CakeOvenBlock.FACING)).contains(direction.getOpposite());
        }

    }

}
