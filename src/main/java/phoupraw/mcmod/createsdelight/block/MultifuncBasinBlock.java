package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import phoupraw.mcmod.createsdelight.block.entity.MultifuncBasinBlockEntity;
import phoupraw.mcmod.createsdelight.registry.CDBlockEntityTypes;
public class MultifuncBasinBlock extends Block implements ITE<MultifuncBasinBlockEntity> {
    public MultifuncBasinBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Class<MultifuncBasinBlockEntity> getTileEntityClass() {
        return MultifuncBasinBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends MultifuncBasinBlockEntity> getTileEntityType() {
        return CDBlockEntityTypes.MULTIFUNC_BASIN;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AllShapes.BASIN_BLOCK_SHAPE;
    }
}
