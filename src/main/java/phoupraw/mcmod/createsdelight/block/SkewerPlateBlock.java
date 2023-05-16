package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.KineticBlock;
import com.simibubi.create.foundation.block.ITE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.Direction;
import phoupraw.mcmod.createsdelight.block.entity.SkewerPlateBlockEntity;
import phoupraw.mcmod.createsdelight.registry.CDBlockEntityTypes;
public class SkewerPlateBlock extends KineticBlock implements ITE<SkewerPlateBlockEntity> {
    public SkewerPlateBlock() {this(FabricBlockSettings.copyOf(AllBlocks.TURNTABLE.get()));}

    public SkewerPlateBlock(Settings properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }

    @Override
    public Class<SkewerPlateBlockEntity> getTileEntityClass() {
        return SkewerPlateBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SkewerPlateBlockEntity> getTileEntityType() {
        return CDBlockEntityTypes.SKEWER_PLATE;
    }
}
