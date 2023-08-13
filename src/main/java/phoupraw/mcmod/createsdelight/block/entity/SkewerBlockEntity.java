package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class SkewerBlockEntity extends KineticBlockEntity {
    public SkewerBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.SKEWER, pos, state);}

    public SkewerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }
}
