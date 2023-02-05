package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.block.entity.SmartDrainBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class SmartDrainBlock extends Block implements ITE<SmartDrainBlockEntity> {
    public SmartDrainBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Class<SmartDrainBlockEntity> getTileEntityClass() {
        return SmartDrainBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SmartDrainBlockEntity> getTileEntityType() {
        return MyBlockEntityTypes.SMART_DRAIN;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AllShapes.CASING_13PX.get(Direction.UP);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        ITE.onRemove(state,world,pos,newState);
        super.onStateReplaced(state, world, pos, newState, moved);
    }
    //TODO
}
