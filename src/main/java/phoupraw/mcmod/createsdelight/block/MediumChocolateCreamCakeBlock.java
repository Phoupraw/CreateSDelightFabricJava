package phoupraw.mcmod.createsdelight.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

import java.util.List;

import static net.minecraft.state.property.Properties.AGE_3;
public class MediumChocolateCreamCakeBlock extends BigCakeBlock {
    public static final VoxelShape GROUND = createCuboidShape(2, 0, 2, 14, 16, 14);
    public static final List<VoxelShape> SHAPES = List.of(
      VoxelShapes.union(
        createCuboidShape(2, 0, 2, 14, 8, 14),
        createCuboidShape(2.5, 8, 4, 13.5, 9, 12),
        createCuboidShape(4.5, 9, 4, 11.5, 10, 12),
        createCuboidShape(6.5, 10, 4, 9.5, 11, 12)),
      VoxelShapes.union(
        createCuboidShape(2, 0, 2, 14, 8, 14),
        createCuboidShape(6.5, 8, 4, 13.5, 9, 12)),
      VoxelShapes.union(
        createCuboidShape(2, 0, 2, 14, 2, 14),
        createCuboidShape(6, 2, 2, 14, 6, 14),
        createCuboidShape(10, 6, 2, 14, 8, 14),
        createCuboidShape(10.5, 8, 4, 13.5, 9, 12)),
      VoxelShapes.union(
        createCuboidShape(6, 0, 2, 14, 2, 14),
        createCuboidShape(10, 2, 2, 14, 6, 14))
    );

    public MediumChocolateCreamCakeBlock() {
        super();
    }

    public MediumChocolateCreamCakeBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(AGE_3));
    }

    @Override
    public VoxelShape getGround(WorldView world, BlockPos blockPos, BlockState blockState) {
        return GROUND;
    }
}
