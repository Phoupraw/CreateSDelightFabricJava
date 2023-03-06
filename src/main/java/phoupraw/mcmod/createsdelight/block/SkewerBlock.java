package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.ITE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;
import phoupraw.mcmod.createsdelight.block.entity.SkewerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class SkewerBlock extends RotatedPillarKineticBlock implements ITE<SkewerBlockEntity> {
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
    public Class<SkewerBlockEntity> getTileEntityClass() {
        return SkewerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SkewerBlockEntity> getTileEntityType() {
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
