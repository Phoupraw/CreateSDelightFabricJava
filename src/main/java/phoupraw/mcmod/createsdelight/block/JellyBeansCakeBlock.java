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
import net.minecraft.world.WorldView;
import phoupraw.mcmod.createsdelight.registry.CDBlocks;

import java.util.List;

import static net.minecraft.state.property.Properties.AGE_3;
public class JellyBeansCakeBlock extends BigCakeBlock {
    public static final List<VoxelShape> OUTLINE_SHAPES = List.of(
      createCuboidShape(2, 0, 2, 14, 10, 14),
      VoxelShapes.union(
        JellyBeansBlock.SHAPE,
        createCuboidShape(8, 0, 2, 14, 10, 8),
        createCuboidShape(2, 0, 8, 14, 10, 14)),
      VoxelShapes.union(
        JellyBeansBlock.SHAPE,
        createCuboidShape(2, 0, 8, 14, 10, 14)),
      VoxelShapes.union(
        JellyBeansBlock.SHAPE,
        createCuboidShape(8, 0, 8, 14, 10, 14))
    );
    public static final List<VoxelShape> COLLISION_SHAPES = List.of(
      createCuboidShape(2, 0, 2, 14, 10, 14),
      VoxelShapes.union(
        createCuboidShape(8, 0, 2, 14, 10, 8),
        createCuboidShape(2, 0, 8, 14, 10, 14)),
      createCuboidShape(2, 0, 8, 14, 10, 14),
      createCuboidShape(8, 0, 8, 14, 10, 14)
    );

    public JellyBeansCakeBlock() {
        this(FabricBlockSettings.copyOf(Blocks.CAKE).breakInstantly());
    }

    public JellyBeansCakeBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPES.get(state.get(AGE_3));
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPES.get(state.get(AGE_3));
    }

    @Override
    public void onFinalEat(World world, BlockPos blockPos, BlockState blockState) {
        world.setBlockState(blockPos, CDBlocks.JELLY_BEANS.getDefaultState());
    }

    @Override
    public VoxelShape getGround(WorldView world, BlockPos blockPos, BlockState blockState) {
        return COLLISION_SHAPES.get(0);
    }
}
