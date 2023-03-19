package phoupraw.mcmod.createsdelight.block;

import com.mojang.datafixers.util.Pair;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import phoupraw.mcmod.createsdelight.registry.MyStatusEffects;

import java.util.List;

import static net.minecraft.state.property.Properties.AGE_3;
public class BasqueCakeBlock extends Block {
    public static final List<VoxelShape> OUTLINE_SHAPES = List.of(
      createCuboidShape(0, 0, 0, 16, 6, 16),
      VoxelShapes.union(
        createCuboidShape(8, 0, 0, 16, 6, 8),
        createCuboidShape(0, 0, 8, 16, 6, 16)),
      createCuboidShape(0, 0, 8, 16, 6, 16),
      createCuboidShape(8, 0, 8, 16, 6, 16)
    );

    public BasqueCakeBlock() {
        this(FabricBlockSettings.copyOf(Blocks.CAKE).breakInstantly());
    }

    public BasqueCakeBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(AGE_3, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(AGE_3);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        JellyBeansCakeBlock.eat(world, pos, state, player, 2, 0.5f, List.of(Pair.of(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 4), 1f)));
        int bites = state.get(AGE_3);
        if (bites < 3) {
            world.setBlockState(pos, state.cycle(AGE_3));
        } else {
            world.removeBlock(pos, false);
        }
        return ActionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return OUTLINE_SHAPES.get(state.get(AGE_3));
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return BlockHelper.hasBlockSolidSide(world.getBlockState(pos.down()), world, pos.down(), Direction.UP);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
}
