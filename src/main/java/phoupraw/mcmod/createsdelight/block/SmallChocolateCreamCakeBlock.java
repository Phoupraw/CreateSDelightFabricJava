package phoupraw.mcmod.createsdelight.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
public class SmallChocolateCreamCakeBlock extends SmallCakeBlock {
    public static final VoxelShape GROUND = createCuboidShape(2, 0, 4, 14, 16, 12);
    public static final VoxelShape SHAPE = VoxelShapes.union(
      createCuboidShape(2, 0, 4, 14, 10, 12),
      createCuboidShape(5, 10, 5, 11, 12, 11),
      createCuboidShape(6.5, 12, 6.5, 9.5, 15, 9.5));

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
    public VoxelShape getGround(WorldView world, BlockPos blockPos, BlockState blockState) {
        return GROUND;
    }
}
