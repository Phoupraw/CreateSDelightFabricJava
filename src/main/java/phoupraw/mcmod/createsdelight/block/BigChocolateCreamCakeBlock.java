package phoupraw.mcmod.createsdelight.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.List;

import static net.minecraft.state.property.Properties.AGE_3;
public class BigChocolateCreamCakeBlock extends BigCakeBlock {
    public static final List<VoxelShape> SHAPES = List.of(
      createCuboidShape(0, 0, 0, 16, 11, 16),
      VoxelShapes.union(
        createCuboidShape(0, 0, 8, 16, 11, 16),
        createCuboidShape(8, 0, 0, 16, 11, 8)),
      createCuboidShape(8, 0, 0, 16, 11, 16),
      createCuboidShape(8, 0, 0, 16, 11, 8)
    );

    public BigChocolateCreamCakeBlock() {
        super();
    }

    public BigChocolateCreamCakeBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(AGE_3));
    }
}
