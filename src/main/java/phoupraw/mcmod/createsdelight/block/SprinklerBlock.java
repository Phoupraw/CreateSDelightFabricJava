package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
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
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class SprinklerBlock extends KineticBlock implements IBE<SprinklerBlockEntity> {
    public static final VoxelShape SHAPE = VoxelShapes.union(createCuboidShape(0, 2, 0, 16, 16, 16), createCuboidShape(2.5, 0, 2.5, 13.5, 2, 13.5));

    public SprinklerBlock(Settings properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public Class<SprinklerBlockEntity> getBlockEntityClass() {
        return SprinklerBlockEntity.class;
    }

    public BlockEntityType<? extends SprinklerBlockEntity> getBlockEntityType() {
        return MyBlockEntityTypes.SPRINKLER;
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
