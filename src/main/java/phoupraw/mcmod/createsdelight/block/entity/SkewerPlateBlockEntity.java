package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.item.SmartInventory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class SkewerPlateBlockEntity extends KineticBlockEntity {
    public final SmartInventory skewerInventory = new SmartInventory(1, this, 1, false);
    public final SmartInventory plateInventory = new SmartInventory(4, this);

    public SkewerPlateBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.SKEWER_PLATE, pos, state);}

    public SkewerPlateBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }
}
