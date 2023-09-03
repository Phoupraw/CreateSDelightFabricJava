package phoupraw.mcmod.createsdelight.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.createsdelight.block.entity.ReadyCakeBlockEntity;
import phoupraw.mcmod.createsdelight.block.entity.ShrinkingCakeBlockEntity;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;

public class ReadyCakeBlock extends Block implements BlockEntityProvider {

    public ReadyCakeBlock(Settings settings) {
        super(settings);
    }

    @NotNull
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ReadyCakeBlockEntity(pos, state);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.isCreative()) {
            world.scheduleBlockTick(pos, this, 0);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    //@Override
    //public BlockRenderType getRenderType(BlockState state) {
    //    return BlockRenderType.INVISIBLE;
    //}
    @SuppressWarnings("deprecation")
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        ReadyCakeBlockEntity ready = (ReadyCakeBlockEntity) world.getBlockEntity(pos);
        for (Direction direction : Direction.values()) {
            BlockPos pos1 = pos.offset(direction);
            if (world.getBlockEntity(pos1) instanceof ReadyCakeBlockEntity ready1 && ready.oven == ready1.oven) {
                world.scheduleBlockTick(pos1, this, 3);
            }
        }
        world.setBlockState(pos, CSDBlocks.SHRINKING_CAKE.getDefaultState());
        ShrinkingCakeBlockEntity shrinking = (ShrinkingCakeBlockEntity) world.getBlockEntity(pos);
        //shrinking
    }

}
