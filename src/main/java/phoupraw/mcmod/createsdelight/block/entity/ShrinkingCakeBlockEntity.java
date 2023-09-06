package phoupraw.mcmod.createsdelight.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;
@Deprecated
public class ShrinkingCakeBlockEntity extends BlockEntity {

    public VoxelCake voxelCake;
    public BlockPos ovenPos;
    public CakeOvenBlockEntity oven;

    public ShrinkingCakeBlockEntity(BlockPos pos, BlockState state) {this(CSDBlockEntityTypes.SHRINKING_CAKE, pos, state);}

    public ShrinkingCakeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

}
