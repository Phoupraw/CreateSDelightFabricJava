package phoupraw.mcmod.createsdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.state.property.Properties.AGE_3;
public class SweetBerriesCakeBlock extends BigCakeBlock {
    public static final List<VoxelShape> SHAPES;
    static {
        SHAPES = new ArrayList<>(Properties.AGE_3_MAX + 1);
        for (int i = 0; i <= Properties.AGE_3_MAX; i++) {
            SHAPES.add(createCuboidShape(1, 0, 1, 15, (i + 1) * (16.0 / (Properties.AGE_3_MAX + 1)), 15));
        }
    }
    public SweetBerriesCakeBlock() {
        this(FabricBlockSettings.copyOf(Blocks.CAKE).breakInstantly());
    }

    public SweetBerriesCakeBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(Properties.AGE_3));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack handStack = player.getStackInHand(hand);
        if (handStack.isOf(asItem())) {
            return ActionResult.PASS;
        }
        while (true) {
            BlockPos pos1 = pos.up();
            BlockState state1 = world.getBlockState(pos1);
            if (!state1.isOf(this)) break;
            pos = pos1;
            state = state1;
        }
        onEachEat(world, pos, state, player, hit);
        int layers = state.get(AGE_3) + 1;
        if (layers == 1) {
            onFinalEat(world, pos, state);
        } else {
            world.setBlockState(pos, state.with(AGE_3, layers - 2));
        }
        return ActionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return state.get(AGE_3) < Properties.AGE_3_MAX && context.getStack().isOf(asItem());
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(this) && ctx.canReplaceExisting()) {
            return blockState.cycle(AGE_3);
        }
        return super.getPlacementState(ctx);
    }

    @Override
    public VoxelShape getGround(WorldView world, BlockPos blockPos, BlockState blockState) {
        return SHAPES.get(Properties.AGE_3_MAX);
    }
}
