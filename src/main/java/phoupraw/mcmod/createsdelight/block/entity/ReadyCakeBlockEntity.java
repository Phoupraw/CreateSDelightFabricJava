package phoupraw.mcmod.createsdelight.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

public class ReadyCakeBlockEntity extends BlockEntity {

    public CakeIngredient cakeIngredient;
    public VoxelCake voxelCake;
    public BlockPos ovenPos;
    public CakeOvenBlockEntity oven;

    public ReadyCakeBlockEntity(BlockPos pos, BlockState state) {this(CSDBlockEntityTypes.READY_CAKE, pos, state);}

    public ReadyCakeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

}
