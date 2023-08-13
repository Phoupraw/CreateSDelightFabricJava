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
import phoupraw.mcmod.createsdelight.block.entity.PressureCookerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class PressureCookerBlock extends HorizontalAxisKineticBlock implements IBE<PressureCookerBlockEntity> {
    public PressureCookerBlock(Settings properties) {
        super(properties);
    }

    @Override
    public Class<PressureCookerBlockEntity> getBlockEntityClass() {
        return PressureCookerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends PressureCookerBlockEntity> getBlockEntityType() {
        return MyBlockEntityTypes.PRESSURE_COOKER;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AllShapes.CASING_12PX.get(Direction.DOWN);
    }

}
