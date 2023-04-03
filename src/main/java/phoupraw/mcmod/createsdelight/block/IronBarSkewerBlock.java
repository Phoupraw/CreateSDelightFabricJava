package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.content.contraptions.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.ITE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import phoupraw.mcmod.createsdelight.block.entity.IronBarSkewerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;

import java.util.Map;
public class IronBarSkewerBlock extends RotatedPillarKineticBlock implements ITE<IronBarSkewerBlockEntity> {
    public static final Map<Direction.Axis, VoxelShape> SHAPES = Map.of(
      Direction.Axis.Y, createCuboidShape(7, 0, 7, 9, 16, 9),
      Direction.Axis.X, createCuboidShape(0, 7, 7, 16, 9, 9),
      Direction.Axis.Z, createCuboidShape(7, 7, 0, 9, 9, 16)
    );

    public IronBarSkewerBlock() {
        this(FabricBlockSettings.copyOf(Blocks.IRON_BARS));
    }

    public IronBarSkewerBlock(Settings properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.get(Properties.AXIS);
    }

    @Override
    public Class<IronBarSkewerBlockEntity> getTileEntityClass() {
        return IronBarSkewerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends IronBarSkewerBlockEntity> getTileEntityType() {
        return MyBlockEntityTypes.IRON_BAR_SKEWER;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(Properties.AXIS));
    }

    @Override
    public boolean hasShaftTowards(WorldView world, BlockPos pos, BlockState state, Direction face) {
        return state.get(Properties.AXIS) == face.getAxis();
    }

    @Override
    public void onStateReplaced(BlockState pState, World pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        ITE.onRemove(pState, pLevel, pPos, pNewState);
        super.onStateReplaced(pState, pLevel, pPos, pNewState, pIsMoving);
    }
}
