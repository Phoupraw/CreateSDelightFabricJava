package phoupraw.mcmod.createsdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

import java.util.List;

import static net.minecraft.state.property.Properties.AGE_3;
public class MediumChocolateCreamCakeBlock extends BigCakeBlock {
    public static final List<VoxelShape> SHAPES = List.of(
      VoxelShapes.union(
        createCuboidShape(0, 0, 0, 16, 13, 16),
        createCuboidShape(1, 13, 1, 15, 16, 15),
        createCuboidShape(3, 16, 3, 13, 19, 13)),
      VoxelShapes.union(
        createCuboidShape(0, 0, 4, 16, 13, 16),
        createCuboidShape(1, 13, 5, 15, 16, 15),
        createCuboidShape(3, 16, 5, 13, 19, 13)),
      VoxelShapes.union(
        createCuboidShape(4, 0, 4, 12, 13, 11),
        createCuboidShape(0, 0, 11, 16, 13, 16),
        createCuboidShape(1, 16, 11, 11, 16, 15),
        createCuboidShape(5, 13, 5, 11, 19, 13)),
      VoxelShapes.union(
        createCuboidShape(0, 0, 11, 16, 13, 16),
        createCuboidShape(1, 13, 12, 4, 15, 15),
        createCuboidShape(12, 13, 12, 15, 15, 15))
    );//TODO

    public MediumChocolateCreamCakeBlock() {
        this(FabricBlockSettings.copyOf(Blocks.CAKE).breakInstantly());
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
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return super.canPlaceAt(state, world, pos);
    }//TODO
}
