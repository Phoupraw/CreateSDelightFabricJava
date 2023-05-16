package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.contraptions.base.HorizontalAxisKineticBlock;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import phoupraw.mcmod.createsdelight.block.entity.VerticalCutterBlockEntity;
import phoupraw.mcmod.createsdelight.registry.CDBlockEntityTypes;
/**
 * 纵切机
 */
public class VerticalCutterBlock extends HorizontalAxisKineticBlock implements ITE<VerticalCutterBlockEntity> {

    public VerticalCutterBlock(Settings properties) {
        super(properties);
    }

    @Override
    public Class<VerticalCutterBlockEntity> getTileEntityClass() {
        return VerticalCutterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends VerticalCutterBlockEntity> getTileEntityType() {
        return CDBlockEntityTypes.VERTICAL_CUTTER;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AllShapes.CASING_14PX.get(Direction.DOWN);
    }

}
