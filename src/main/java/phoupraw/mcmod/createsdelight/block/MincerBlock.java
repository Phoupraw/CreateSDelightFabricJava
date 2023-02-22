package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.content.contraptions.base.KineticBlock;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.Direction;
import phoupraw.mcmod.createsdelight.block.entity.MincerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class MincerBlock extends KineticBlock implements ITE<MincerBlockEntity> {
    public MincerBlock(Settings properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public Class<MincerBlockEntity> getTileEntityClass() {
        return MincerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends MincerBlockEntity> getTileEntityType() {
        return MyBlockEntityTypes.MINCER;
    }

    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.FAST;
    }

}
