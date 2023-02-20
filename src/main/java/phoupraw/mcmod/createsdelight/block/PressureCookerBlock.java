package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import phoupraw.mcmod.createsdelight.block.entity.PressureCookerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class PressureCookerBlock extends HorizontalAxisKineticBlock implements ITE<PressureCookerBlockEntity> {
    public PressureCookerBlock(Settings properties) {
        super(properties);
    }

    @Override
    public Class<PressureCookerBlockEntity> getTileEntityClass() {
        return PressureCookerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends PressureCookerBlockEntity> getTileEntityType() {
        return MyBlockEntityTypes.PRESSURE_COOKER;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AllShapes.CASING_14PX.get(Direction.DOWN);
    }

}
