package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.block.entity.VoxelMakerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

import static phoupraw.mcmod.createsdelight.block.CakeOvenBlock.FACING;

public class VoxelMakerBlock extends KineticBlock implements IBE<VoxelMakerBlockEntity> {
    public VoxelMakerBlock(Settings properties) {
        super(properties);
    }
    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }
    @Override
    public Class<VoxelMakerBlockEntity> getBlockEntityClass() {
        return VoxelMakerBlockEntity.class;
    }
    @Override
    public BlockEntityType<? extends VoxelMakerBlockEntity> getBlockEntityType() {
        return CSDBlockEntityTypes.VOXEL_MAKER;
    }
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        if (!(world.getBlockEntity(pos) instanceof VoxelMakerBlockEntity be)) return;
        if (be.getWorking() == TriState.DEFAULT) {
            if (world.isReceivingRedstonePower(pos)) {
                be.setWorking(TriState.TRUE);
                be.sendData();
            }
        } else {
            be.setWorking(TriState.DEFAULT);
            be.sendData();
        }
    }
    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, CakeOvenBlock.rotate(state.get(FACING), rotation));
    }
    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(FACING, CakeOvenBlock.mirror(state.get(FACING), mirror));
    }
    @Override
    public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
        return originalState.rotate(BlockRotation.CLOCKWISE_90);
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().rotate(BlockRotation.values()[Direction.fromRotation(ctx.getPlayerYaw() + 225).getHorizontal()]);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    @Override
    public ActionResult onWrenched(BlockState state, ItemUsageContext context) {
        if (!((VoxelMakerBlockEntity) context.getWorld().getBlockEntity(context.getBlockPos())).getWorking().get()) {
            return super.onWrenched(state, context);
        }
        return ActionResult.FAIL;
    }
}
