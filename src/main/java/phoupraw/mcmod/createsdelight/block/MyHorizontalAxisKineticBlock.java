package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.content.contraptions.base.KineticBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.client.*;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

import static net.minecraft.data.client.VariantSettings.MODEL;
import static net.minecraft.data.client.VariantSettings.Y;
public abstract class MyHorizontalAxisKineticBlock extends KineticBlock {
    public static final BooleanProperty X = BooleanProperty.of("x");

    public static void addBlockStates(BlockStateModelGenerator generator, Block block, Identifier modelId) {
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
          .coordinate(BlockStateVariantMap.create(X)
            .register(false, BlockStateVariant.create()
              .put(MODEL, modelId))
            .register(true, BlockStateVariant.create()
              .put(MODEL, modelId)
              .put(Y, VariantSettings.Rotation.R90))));
    }

    public MyHorizontalAxisKineticBlock(Settings properties) {
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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(X);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        if (rotation == BlockRotation.NONE || rotation == BlockRotation.CLOCKWISE_180) return state;
        return state.cycle(X);
    }

    /**
     * 手持扳手右键方块即可触发此方法。
     *
     * @param originalState 原本的状态方块
     * @param targetedFace  右键的面
     * @return 旋转后的状态方块
     */
    @Override
    public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
        return originalState.cycle(X);
    }
}
