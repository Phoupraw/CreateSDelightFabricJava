package phoupraw.mcmod.createsdelight.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class GrillBlockEntity extends MyBlockEntity1 {

    public GrillBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.GRILL, pos, state);}

    public GrillBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void tick() {
        super.tick();

    }
}
