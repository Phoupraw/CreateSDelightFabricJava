package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.foundation.block.ITE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.block.entity.GrillBlockEntity;
import phoupraw.mcmod.createsdelight.block.entity.MyBlockEntity1;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class GrillBlock extends Block implements ITE<GrillBlockEntity> {
    public static final VoxelShape SHAPE = Block.createCuboidShape(0,0,0,16,4,16);
    public GrillBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Class<GrillBlockEntity> getTileEntityClass() {
        return GrillBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends GrillBlockEntity> getTileEntityType() {
        return MyBlockEntityTypes.GRILL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) return ActionResult.CONSUME;
        var grill = world.getBlockEntity(pos, MyBlockEntityTypes.GRILL).orElseThrow();
        return  PanBlock.swapItem(player,hand,grill.getItem().getStorage())?ActionResult.SUCCESS:ActionResult.FAIL;
    }
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (world.getBlockEntity(pos) instanceof MyBlockEntity1 grill && grill.isProcessing()) {
           PanBlock.addSteam(world, pos, random);
        }
    }
}
