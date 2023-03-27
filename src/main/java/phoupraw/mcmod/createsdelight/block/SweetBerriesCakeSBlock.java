package phoupraw.mcmod.createsdelight.block;

import com.mojang.datafixers.util.Pair;
import com.nhoryzon.mc.farmersdelight.registry.EffectsRegistry;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
import phoupraw.mcmod.createsdelight.item.StatusEffectsBlockItem;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
import phoupraw.mcmod.createsdelight.registry.MyStatusEffects;

import java.util.List;

import static net.minecraft.state.property.Properties.AGE_3;
public class SweetBerriesCakeSBlock extends Block {
    public static final VoxelShape BASE = createCuboidShape(0, 0, 0, 16, 12, 16);
    public static final VoxelShape CENTER = VoxelShapes.union(
      createCuboidShape(4, 12, 4, 12, 15, 12),
      createCuboidShape(8 - 1.5, 15, 8 - 1.5, 8 + 1.5, 18, 8 + 1.5));
    public static final VoxelShape CREAM = createCuboidShape(1, 12, 1, 15, 14, 15);
    public static final VoxelShape CORNERS = VoxelShapes.union(
      createCuboidShape(0, 12, 0, 3, 15, 3),
      createCuboidShape(0, 12, 13, 3, 15, 16),
      createCuboidShape(13, 12, 0, 16, 15, 3),
      createCuboidShape(13, 12, 13, 16, 15, 16));
    public static final List<VoxelShape> SHAPES = List.of(
      VoxelShapes.union(BASE, CENTER, CREAM, CORNERS),
      VoxelShapes.union(BASE, CENTER, CREAM),
      VoxelShapes.union(BASE, CENTER),
      BASE
    );

    public SweetBerriesCakeSBlock() {
        this(FabricBlockSettings.copyOf(Blocks.CAKE).breakInstantly());
    }

    public SweetBerriesCakeSBlock(Settings settings) {
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
        int bites = state.get(AGE_3);
        StatusEffectsBlockItem.eat(world, pos, state, hit.getPos(), player, 5, 0.5f, List.of(Pair.of(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 3), 1f), Pair.of(new StatusEffectInstance(StatusEffects.SATURATION, 2, 0), 1f), Pair.of(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 20 * 60 * 2, 0), 1f)));
        if (bites < 3) {
            world.setBlockState(pos, state.cycle(AGE_3));
        } else {
            world.setBlockState(pos, MyBlocks.SWEET_BERRIES_CAKE.getDefaultState().with(AGE_3, 2));
        }
        return ActionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(AGE_3));
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
