package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.contraptions.base.KineticBlock;
import com.simibubi.create.content.contraptions.relays.elementary.ICogWheel;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import phoupraw.mcmod.createsdelight.block.entity.MincerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class MincerBlock extends KineticBlock implements ITE<MincerBlockEntity>, ICogWheel {
    public static final VoxelShape EXTENDED = VoxelShapes.union(AllShapes.CASING_14PX.get(Direction.DOWN), createCuboidShape(5, -14, 5, 11, 2, 11), createCuboidShape(0, -16, 0, 16, -14, 16));

    public MincerBlock(Settings properties) {
        super(properties);
        setDefaultState(getDefaultState().with(Properties.EXTENDED, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.EXTENDED);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public Class<MincerBlockEntity> getTileEntityClass() {
        return MincerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends MincerBlockEntity> getTileEntityType() {
        return MyBlockEntityTypes.MINCER;
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.FAST;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(Properties.EXTENDED) ? EXTENDED : AllShapes.CASING_14PX.get(Direction.DOWN);
    }
}
