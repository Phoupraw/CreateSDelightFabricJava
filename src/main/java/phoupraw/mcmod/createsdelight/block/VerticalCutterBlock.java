package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.contraptions.base.KineticBlock;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import phoupraw.mcmod.createsdelight.block.entity.VerticalCutterBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class VerticalCutterBlock extends KineticBlock implements ITE<VerticalCutterBlockEntity> {
    public static final BooleanProperty X = BooleanProperty.of("x");

    public VerticalCutterBlock(Settings properties) {
        super(properties);
        setDefaultState(getDefaultState().with(X, false));
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context) {
//        Direction prefferedSide = getPreferredHorizontalFacing(context);
//        if (prefferedSide != null)
//            return getDefaultState().with(HORIZONTAL_FACING, prefferedSide);
        return super.getPlacementState(context);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.get(X) ? Direction.Axis.X : Direction.Axis.Z;
    }

    @Override
    public boolean hasShaftTowards(WorldView world, BlockPos pos, BlockState state, Direction face) {
        return face.getAxis() == getRotationAxis(state);
    }

    @Override
    public Class<VerticalCutterBlockEntity> getTileEntityClass() {
        return VerticalCutterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends VerticalCutterBlockEntity> getTileEntityType() {
        return MyBlockEntityTypes.VERTICAL_CUTTER;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(X);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AllShapes.CASING_14PX.get(Direction.DOWN);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        if (rotation == BlockRotation.NONE || rotation == BlockRotation.CLOCKWISE_180) return state;
        return state.cycle(X);
    }

    /**
     * 手持扳手右键方块即可触发此方法。
     * @param originalState 原本的状态方块
     * @param targetedFace 右键的面
     * @return 旋转后的状态方块
     */
    @Override
    public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
        return originalState.cycle(X);
    }
}
