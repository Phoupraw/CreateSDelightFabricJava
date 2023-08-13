package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.HorizontalAxisKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import phoupraw.mcmod.createsdelight.block.entity.VerticalCutterBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
/**
 * 纵切机
 */
public class VerticalCutterBlock extends HorizontalAxisKineticBlock implements IBE<VerticalCutterBlockEntity> {

    public VerticalCutterBlock(Settings properties) {
        super(properties);
    }

    @Override
    public Class<VerticalCutterBlockEntity> getBlockEntityClass() {
        return VerticalCutterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends VerticalCutterBlockEntity> getBlockEntityType() {
        return MyBlockEntityTypes.VERTICAL_CUTTER;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AllShapes.CASING_14PX.get(Direction.DOWN);
    }

}
