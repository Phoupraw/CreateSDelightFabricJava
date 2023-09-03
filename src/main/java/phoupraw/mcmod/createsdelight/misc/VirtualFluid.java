package phoupraw.mcmod.createsdelight.misc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.ApiStatus;

/**
 * @since 0.1.0-pre7
 */
public class VirtualFluid extends FlowableFluid {

    @ApiStatus.Internal
    public VirtualFluid() {

    }

    @Override
    public Fluid getFlowing() {
        return this;
    }

    @Override
    public Fluid getStill() {
        return this;
    }

    @Override
    protected boolean isInfinite(World world) {
        return false;
    }

    /**
     * Perform actions when the fluid flows into a replaceable block. Water drops
     * the block's loot table. Lava plays the "block.lava.extinguish" sound.
     */
    @Override
    public void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        final BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }

    /**
     * Possibly related to the distance checks for flowing into nearby holes?
     * Water returns 4. Lava returns 2 in the Overworld and 4 in the Nether.
     */
    @Override
    public int getFlowSpeed(WorldView worldView) {
        return 4;
    }

    /**
     * Water returns 1. Lava returns 2 in the Overworld and 1 in the Nether.
     */
    @Override
    public int getLevelDecreasePerBlock(WorldView worldView) {
        return 1;
    }

    @Override
    public int getLevel(FluidState state) {
        return 0;
    }

    /**
     * 默认返回空气。
     * @since 0.1.0-pre8
     */
    @Override
    public Item getBucketItem() {
        return Items.AIR;
    }

    /**
     * Lava returns true if it's FluidState is above a certain height and the
     * Fluid is Water.
     * @return whether the given Fluid can flow into this FluidState
     */
    @Override
    public boolean canBeReplacedWith(FluidState fluidState, BlockView blockView, BlockPos blockPos, Fluid fluid, Direction direction) {
        return false;
    }

    /**
     * Water returns 5. Lava returns 30 in the Overworld and 10 in the Nether.
     */
    @Override
    public int getTickRate(WorldView worldView) {
        return 5;
    }

    /**
     * Water and Lava both return 100.0F.
     */
    @Override
    public float getBlastResistance() {
        return 100.0F;
    }

    @Override
    public BlockState toBlockState(FluidState state) {
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public boolean isStill(FluidState state) {
        return true;
    }

    /**
     * @return whether the given fluid an instance of this fluid
     */
    @Override
    public boolean matchesType(Fluid fluid) {
        return fluid == getStill() || fluid == getFlowing();
    }

}
