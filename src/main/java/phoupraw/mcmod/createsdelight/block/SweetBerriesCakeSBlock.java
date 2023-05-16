package phoupraw.mcmod.createsdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.registry.CDBlocks;

import java.util.List;

import static net.minecraft.state.property.Properties.AGE_3;
public class SweetBerriesCakeSBlock extends BigCakeBlock {
    public static final VoxelShape BASE = createCuboidShape(0, 0, 0, 16, 12, 16);
    public static final VoxelShape CENTER = VoxelShapes.union(
      createCuboidShape(4, 12, 4, 12, 15, 12),
      createCuboidShape(8 - 1.5, 15, 8 - 1.5, 8 + 1.5, 18, 8 + 1.5));
    public static final VoxelShape CREAM = createCuboidShape(1, 12, 1, 15, 14, 15);
    public static final VoxelShape CORNERS = VoxelShapes.union(
      createCuboidShape(0, 12, 0, 3, 15, 3),
      createCuboidShape(0, 12, 13, 3, 15, 16),
      createCuboidShape(13, 12, 0, 16, 15, 3),
      createCuboidShape(13, 12, 13, 16, 15, 16));
    public static final List<VoxelShape> SHAPES = List.of(
      VoxelShapes.union(BASE, CENTER, CREAM, CORNERS),
      VoxelShapes.union(BASE, CENTER, CREAM),
      VoxelShapes.union(BASE, CENTER),
      BASE
    );

    public SweetBerriesCakeSBlock() {
        this(FabricBlockSettings.copyOf(Blocks.CAKE).breakInstantly());
    }

    public SweetBerriesCakeSBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(AGE_3));
    }

    @Override
    public void onFinalEat(World world, BlockPos blockPos, BlockState blockState) {
        world.setBlockState(blockPos, CDBlocks.SWEET_BERRIES_CAKE.getDefaultState().with(AGE_3, 2));
    }
}
