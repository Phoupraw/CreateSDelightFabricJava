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
public class BasqueCakeBlock extends BigCakeBlock {
    public static final List<VoxelShape> SHAPES = List.of(
      createCuboidShape(0, 0, 0, 16, 6, 16),
      VoxelShapes.union(
        createCuboidShape(8, 0, 0, 16, 6, 8),
        createCuboidShape(0, 0, 8, 16, 6, 16)),
      createCuboidShape(0, 0, 8, 16, 6, 16),
      createCuboidShape(8, 0, 8, 16, 6, 16)
    );

    public BasqueCakeBlock() {
        this(FabricBlockSettings.copyOf(Blocks.CAKE).breakInstantly());
    }

    public BasqueCakeBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(AGE_3));
    }
}
