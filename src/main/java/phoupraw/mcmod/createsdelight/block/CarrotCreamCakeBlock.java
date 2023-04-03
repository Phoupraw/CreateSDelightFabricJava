package phoupraw.mcmod.createsdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

import java.util.List;

import static net.minecraft.state.property.Properties.AGE_3;
public class CarrotCreamCakeBlock extends BigCakeBlock {
    public static final List<VoxelShape> SHAPES = List.of(
      VoxelShapes.union(
        createCuboidShape(0, 0, 0, 16, 13, 16),
        createCuboidShape(7, 13, 1, 9, 15, 15),
        createCuboidShape(6, 13, 4, 10, 16, 12),
        createCuboidShape(5, 13, 7, 11, 16, 9)),
      VoxelShapes.union(
        createCuboidShape(4, 0, 0, 16, 13, 16),
        createCuboidShape(7, 13, 1, 9, 15, 4),
        createCuboidShape(6, 13, 4, 10, 16, 12),
        createCuboidShape(5, 13, 7, 11, 16, 9)),
      VoxelShapes.union(
        createCuboidShape(4, 0, 0, 12, 13, 16),
        createCuboidShape(7, 13, 1, 9, 15, 4),
        createCuboidShape(6, 13, 4, 10, 16, 7),
        createCuboidShape(5, 13, 7, 11, 16, 9)),
      VoxelShapes.union(
        createCuboidShape(4, 0, 0, 12, 13, 8),
        createCuboidShape(7, 13, 1, 9, 15, 4),
        createCuboidShape(6, 13, 4, 10, 16, 6))
    );

    public CarrotCreamCakeBlock() {
        this(FabricBlockSettings.copyOf(Blocks.CAKE).breakInstantly());
    }

    public CarrotCreamCakeBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(AGE_3));
    }
}
