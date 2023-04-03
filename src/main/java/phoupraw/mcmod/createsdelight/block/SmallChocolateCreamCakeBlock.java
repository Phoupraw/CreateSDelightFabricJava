package phoupraw.mcmod.createsdelight.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
public class SmallChocolateCreamCakeBlock extends SmallCakeBlock {
    public static final VoxelShape SHAPE = VoxelShapes.union(
      createCuboidShape(4, 0, 4, 12, 14, 12),
      createCuboidShape(5, 14, 5, 11, 16, 11));//TODO

    public SmallChocolateCreamCakeBlock() {
        super();
    }

    public SmallChocolateCreamCakeBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return !VoxelShapes.matchesAnywhere(world.getBlockState(pos.down()).getSidesShape(world, pos.down()).getFace(Direction.UP), SHAPE, BooleanBiFunction.ONLY_SECOND);
    }
}
