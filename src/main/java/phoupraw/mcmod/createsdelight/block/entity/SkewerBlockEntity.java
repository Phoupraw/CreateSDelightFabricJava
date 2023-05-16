package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import phoupraw.mcmod.createsdelight.registry.CDBlockEntityTypes;
public class SkewerBlockEntity extends KineticTileEntity {
    public SkewerBlockEntity(BlockPos pos, BlockState state) {
        this(CDBlockEntityTypes.SKEWER, pos, state);
    }

    public SkewerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }
}
