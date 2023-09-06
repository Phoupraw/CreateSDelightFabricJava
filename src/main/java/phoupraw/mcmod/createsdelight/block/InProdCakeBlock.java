package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.foundation.block.IBE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.block.entity.InProdCakeBlockEntity;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

public class InProdCakeBlock extends Block implements IBE<InProdCakeBlockEntity> {
    public InProdCakeBlock(Settings settings) {
        super(settings);
    }
    @Override
    public Class<InProdCakeBlockEntity> getBlockEntityClass() {
        return InProdCakeBlockEntity.class;
    }
    @Override
    public BlockEntityType<? extends InProdCakeBlockEntity> getBlockEntityType() {
        return CSDBlockEntityTypes.IN_PROD_CAKE;
    }
    @SuppressWarnings("deprecation")
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        //if (player.isCreative()) {
        //    world.scheduleBlockTick(pos, this, 0);
        //    return ActionResult.SUCCESS;
        //}
        return ActionResult.PASS;
    }

    //@Override
    //public BlockRenderType getRenderType(BlockState state) {
    //    return BlockRenderType.INVISIBLE;
    //}
    @SuppressWarnings("deprecation")
    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        InProdCakeBlockEntity inProd = (InProdCakeBlockEntity) world.getBlockEntity(pos);
        if (inProd.getVoxelCake() != null && inProd.getVoxelCake().getContent().isEmpty()) {
            world.removeBlock(pos, false);
        }
        //for (Direction direction : Direction.values()) {
        //    BlockPos pos1 = pos.offset(direction);
        //    if (world.getBlockEntity(pos1) instanceof InProdBlockEntity ready1 && ready.oven == ready1.oven) {
        //        world.scheduleBlockTick(pos1, this, 3);
        //    }
        //}
        //world.setBlockState(pos, CSDBlocks.SHRINKING_CAKE.getDefaultState());
        //ShrinkingCakeBlockEntity shrinking = (ShrinkingCakeBlockEntity) world.getBlockEntity(pos);
        //shrinking
    }
}
