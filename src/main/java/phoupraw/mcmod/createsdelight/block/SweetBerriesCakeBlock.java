package phoupraw.mcmod.createsdelight.block;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.MyStatusEffects;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.state.property.Properties.AGE_3;
public class SweetBerriesCakeBlock extends Block {
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
        setDefaultState(getDefaultState().with(Properties.AGE_3, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.AGE_3);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(Properties.AGE_3));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @SuppressWarnings("deprecation")
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
        JellyBeansCakeBlock.eat(world, pos, state, player, 1, 0.5f, List.of(Pair.of(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 0), 1f)));
        int layers = state.get(AGE_3) + 1;
        if (layers == 1) {
            world.removeBlock(pos, false);
        } else {
            world.setBlockState(pos, state.with(AGE_3, layers - 2));
        }
        return ActionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return !VoxelShapes.matchesAnywhere(world.getBlockState(pos.down()).getSidesShape(world, pos.down()).getFace(Direction.UP), SHAPES.get(Properties.AGE_3_MAX), BooleanBiFunction.ONLY_SECOND);
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
}
