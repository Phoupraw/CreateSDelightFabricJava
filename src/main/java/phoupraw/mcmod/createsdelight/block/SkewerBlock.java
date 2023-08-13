package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import phoupraw.mcmod.createsdelight.block.entity.SkewerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class SkewerBlock extends RotatedPillarKineticBlock implements IBE<SkewerBlockEntity> {
    public SkewerBlock() {this(FabricBlockSettings.copyOf(AllBlocks.SHAFT.get()));}

    public SkewerBlock(Settings properties) {
        super(properties);
        setDefaultState(getDefaultState());
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.get(AXIS);
    }

    @Override
    public Class<SkewerBlockEntity> getBlockEntityClass() {
        return SkewerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SkewerBlockEntity> getBlockEntityType() {
        return MyBlockEntityTypes.SKEWER;
    }

    @Override
    public boolean hasShaftTowards(WorldView world, BlockPos pos, BlockState state, Direction face) {
        return getRotationAxis(state) == face.getAxis();
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }
}
