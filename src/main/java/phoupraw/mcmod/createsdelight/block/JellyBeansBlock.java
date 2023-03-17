package phoupraw.mcmod.createsdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
public class JellyBeansBlock extends Block {
    public static final VoxelShape SHAPE = createCuboidShape(4.0, 0.0, 4.0, 12.0, 1.0, 12.0);

    public JellyBeansBlock() {
        this(FabricBlockSettings.copyOf(Blocks.REDSTONE_WIRE));
    }

    public JellyBeansBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}
