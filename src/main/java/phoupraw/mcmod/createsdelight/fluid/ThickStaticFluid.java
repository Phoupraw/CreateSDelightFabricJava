package phoupraw.mcmod.createsdelight.fluid;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

/**
 不会流动、不可被替换、碰撞箱和模型都是完整方块，类似细雪（不过细雪本身不是流体） */
public class ThickStaticFluid extends FlowableFluid {
    protected final Item bucketItem;
    protected final Block fluidBlock;
    public ThickStaticFluid(Item bucketItem, Block fluidBlock) {
        this.bucketItem = bucketItem;
        this.fluidBlock = fluidBlock;
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
    @Override
    protected void beforeBreakingBlock(WorldAccess world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world, pos, blockEntity);
    }
    @Override
    protected int getFlowSpeed(WorldView world) {
        return 0;
    }
    @Override
    protected int getLevelDecreasePerBlock(WorldView world) {
        return 0;
    }
    @Override
    public Item getBucketItem() {
        return bucketItem;
    }
    @Override
    protected boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return false;
    }
    @Override
    public int getTickRate(WorldView world) {
        return Integer.MAX_VALUE;
    }
    @Override
    protected float getBlastResistance() {
        return 100;
    }
    @Override
    protected BlockState toBlockState(FluidState state) {
        return fluidBlock.getDefaultState();
    }
    @Override
    public boolean isStill(FluidState state) {
        return true;
    }
    @Override
    public int getLevel(FluidState state) {
        return 8;
    }
}
