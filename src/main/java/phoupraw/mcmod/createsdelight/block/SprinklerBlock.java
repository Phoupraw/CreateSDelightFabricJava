package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.content.contraptions.base.KineticBlock;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
import phoupraw.mcmod.createsdelight.block.entity.SprinklerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.CDBlockEntityTypes;
public class SprinklerBlock extends KineticBlock implements ITE<SprinklerBlockEntity> {
    public static final VoxelShape SHAPE = VoxelShapes.union(createCuboidShape(0, 2, 0, 16, 16, 16), createCuboidShape(2.5, 0, 2.5, 13.5, 2, 13.5));

    public SprinklerBlock(Settings properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public Class<SprinklerBlockEntity> getTileEntityClass() {
        return SprinklerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SprinklerBlockEntity> getTileEntityType() {
        return CDBlockEntityTypes.SPRINKLER;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean hasShaftTowards(WorldView world, BlockPos pos, BlockState state, Direction face) {
        return face == Direction.UP;
    }
}
