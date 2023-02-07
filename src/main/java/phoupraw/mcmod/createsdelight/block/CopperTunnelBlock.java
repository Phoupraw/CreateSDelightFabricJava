package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.content.contraptions.wrench.IWrenchable;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.block.entity.CopperTunnelBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;

import java.util.*;
public class CopperTunnelBlock extends Block implements ITE<CopperTunnelBlockEntity>, IWrenchable {
    public static final VoxelShape FRAME_SHAPE = VoxelShapes.union(
      VoxelShapes.combine(
        createCuboidShape(0, 10, 0, 16, 16, 16),
        createCuboidShape(2, 10, 2, 14, 14, 14),
        BooleanBiFunction.ONLY_FIRST),
      createCuboidShape(0, -3, 0, 2, 10, 2),
      createCuboidShape(0, -3, 14, 2, 10, 16),
      createCuboidShape(14, -3, 0, 16, 10, 2),
      createCuboidShape(14, -3, 14, 16, 10, 16));
    public static final Map<Direction, @NotNull VoxelShape> SIDE_SHAPES = new EnumMap<>(Direction.class);
    public static final Map<BlockState, @NotNull VoxelShape> ALL_SHAPES = new HashMap<>();
    static {
        SIDE_SHAPES.put(Direction.WEST, createCuboidShape(0, -3, 2, 2, 10, 14));
        SIDE_SHAPES.put(Direction.EAST, createCuboidShape(14, -3, 2, 16, 10, 14));
        SIDE_SHAPES.put(Direction.NORTH, createCuboidShape(2, -3, 0, 14, 10, 2));
        SIDE_SHAPES.put(Direction.SOUTH, createCuboidShape(2, -3, 14, 14, 10, 16));
    }
    public CopperTunnelBlock(Settings settings) {
        super(settings);
        var state = getDefaultState();
        for (EnumProperty<Model> value : Model.HORIZONTALS.values()) {
            state = state.with(value, Model.GLASS);
        }
        setDefaultState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        for (EnumProperty<Model> value : Model.HORIZONTALS.values()) builder.add(value);
    }

    @Override
    public Class<CopperTunnelBlockEntity> getTileEntityClass() {
        return CopperTunnelBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CopperTunnelBlockEntity> getTileEntityType() {
        return MyBlockEntityTypes.COPPER_TUNNEL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        var s = ALL_SHAPES.get(state);
        if (s == null) {
            s = FRAME_SHAPE;
            for (Direction side : Direction.Type.HORIZONTAL) {
                var property = Model.HORIZONTALS.get(side);
                if (!state.get(property).isOpen()) {
                    s = VoxelShapes.union(s, SIDE_SHAPES.get(side));
                }
            }
            ALL_SHAPES.put(state, s);
        }
        return s;
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, @NotNull BlockRotation rotation) {
        return switch (rotation) {
            case NONE -> state;
            case CLOCKWISE_90 -> state
              .with(Model.SOUTH, state.get(Model.EAST))
              .with(Model.EAST, state.get(Model.NORTH))
              .with(Model.NORTH, state.get(Model.WEST))
              .with(Model.WEST, state.get(Model.SOUTH));
            case CLOCKWISE_180 -> state.mirror(BlockMirror.LEFT_RIGHT).mirror(BlockMirror.FRONT_BACK);
            case COUNTERCLOCKWISE_90 -> state
              .with(Model.SOUTH, state.get(Model.WEST))
              .with(Model.WEST, state.get(Model.NORTH))
              .with(Model.NORTH, state.get(Model.EAST))
              .with(Model.EAST, state.get(Model.SOUTH));
        };
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, @NotNull BlockMirror mirror) {
        return switch (mirror) {
            case NONE -> state;
            case LEFT_RIGHT -> state
              .with(Model.NORTH, state.get(Model.SOUTH))
              .with(Model.SOUTH, state.get(Model.NORTH));
            case FRONT_BACK -> state
              .with(Model.WEST, state.get(Model.EAST))
              .with(Model.EAST, state.get(Model.WEST));
        };
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return world.getBlockState(pos.down()).isOf(MyBlocks.SMART_DRAIN) && super.canPlaceAt(state, world, pos);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        var state0 = Objects.requireNonNullElse(super.getPlacementState(ctx), getDefaultState());
        var world = ctx.getWorld();
        var pos0 = ctx.getBlockPos();
        for (Direction side : Direction.Type.HORIZONTAL) {
            var pos1 = pos0.offset(side);
            var state1 = world.getBlockState(pos1);
            if (state1.isOf(MyBlocks.COPPER_TUNNEL)) {
                var model = state1.get(Model.HORIZONTALS.get(side.getOpposite()));
                if (model != Model.COPPER) {
                    state0 = state0.with(Model.HORIZONTALS.get(side), Model.NONE);
                }
            } else {
                var pos2 = pos1.down();
                var state2 = world.getBlockState(pos2);
                if (state2.isOf(MyBlocks.SMART_DRAIN)) {
                    state0 = state0.with(Model.HORIZONTALS.get(side), Model.CURTAIN);
                }
            }
        }
        return state0;
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        var state0 = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        if (direction == Direction.DOWN && !neighborState.isOf(MyBlocks.SMART_DRAIN)) return Blocks.AIR.getDefaultState();
        if (direction.getAxis().isHorizontal()) {
            EnumProperty<Model> property = Model.HORIZONTALS.get(direction);
            if (neighborState.isOf(MyBlocks.COPPER_TUNNEL)) {
                state0 = state0.with(property, neighborState.get(Model.HORIZONTALS.get(direction.getOpposite())));
            } else if (world.getBlockState(neighborPos.down()).isOf(MyBlocks.SMART_DRAIN)) {
                state0 = state0.with(property, Model.CURTAIN);
            } else {
                state0 = state0.with(property, Model.GLASS);
            }
        }
        return state0;
    }

    @Override
    public ActionResult onWrenched(BlockState state, ItemUsageContext context) {
        if (context.getSide().getAxis().isVertical()) return IWrenchable.super.onWrenched(state, context);
        context.getWorld().setBlockState(context.getBlockPos(), state.cycle(Model.HORIZONTALS.get(context.getSide())));
        return ActionResult.SUCCESS;
    }

    public enum Model implements StringIdentifiable {
        NONE(true), CURTAIN(true), GLASS(false), COPPER(false);
        public static final EnumProperty<Model> SOUTH = EnumProperty.of(Direction.SOUTH.getName(), Model.class);
        public static final EnumProperty<Model> WEST = EnumProperty.of(Direction.WEST.getName(), Model.class);
        public static final EnumProperty<Model> EAST = EnumProperty.of(Direction.EAST.getName(), Model.class);
        public static final EnumProperty<Model> NORTH = EnumProperty.of(Direction.NORTH.getName(), Model.class);
        public static final Map<Direction, EnumProperty<Model>> HORIZONTALS = new EnumMap<>(Map.of(
          Direction.SOUTH, SOUTH,
          Direction.WEST, WEST,
          Direction.EAST, EAST,
          Direction.NORTH, NORTH));

        private final boolean open;

        Model(boolean open) {this.open = open;}

        @Override
        public String asString() {
            return name().toLowerCase(Locale.ROOT);
        }

        public boolean isOpen() {
            return open;
        }
    }
}
