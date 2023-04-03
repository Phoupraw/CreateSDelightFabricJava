package phoupraw.mcmod.createsdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;
public class BrownieBlock extends SmallCakeBlock {
    public static final VoxelShape SHAPE = VoxelShapes.union(
      createCuboidShape(4, 0, 4, 12, 14, 12),
      createCuboidShape(5, 14, 5, 11, 16, 11));

    public BrownieBlock() {
        this(FabricBlockSettings.copyOf(Blocks.CAKE).breakInstantly());
    }

    public BrownieBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
//        if (world.isClient()) {
//            VoxelShape face = world.getBlockState(pos.down()).getCollisionShape(world, pos.down()).getFace(Direction.UP);
////            CreateSDelight.LOGGER.info(face);
//            CreateSDelight.LOGGER.info(VoxelShapes.matchesAnywhere(SHAPE,face, BooleanBiFunction.ONLY_SECOND));
//        }
        return !VoxelShapes.matchesAnywhere(world.getBlockState(pos.down()).getSidesShape(world, pos.down()).getFace(Direction.UP), SHAPE, BooleanBiFunction.ONLY_SECOND);//BlockHelper.hasBlockSolidSide(world.getBlockState(pos.down()), world, pos.down(), Direction.UP);
    }
}
