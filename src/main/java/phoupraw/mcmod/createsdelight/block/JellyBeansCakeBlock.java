package phoupraw.mcmod.createsdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
import phoupraw.mcmod.createsdelight.registry.MyStatusEffects;

import java.util.List;

import static net.minecraft.state.property.Properties.AGE_3;
public class JellyBeansCakeBlock extends Block {
    public static final List<VoxelShape> OUTLINE_SHAPES = List.of(
      createCuboidShape(2, 0, 2, 14, 10, 14),
      VoxelShapes.union(
        createCuboidShape(4, 0, 4, 8, 1, 8),
        createCuboidShape(8, 0, 2, 14, 10, 8),
        createCuboidShape(2, 0, 14, 8, 10, 14)),
      VoxelShapes.union(
        createCuboidShape(4, 0, 4, 12, 1, 8),
        createCuboidShape(2, 0, 14, 8, 10, 14)),
      VoxelShapes.union(
        createCuboidShape(4, 0, 4, 12, 1, 8),
        createCuboidShape(4, 0, 8, 8, 1, 12),
        createCuboidShape(8, 0, 8, 14, 10, 14))
    );
    public static final List<VoxelShape> COLLISION_SHAPES = List.of(
      createCuboidShape(2, 0, 2, 14, 10, 14),
      VoxelShapes.union(
        createCuboidShape(8, 0, 2, 14, 10, 8),
        createCuboidShape(2, 0, 14, 8, 10, 14)),
      createCuboidShape(2, 0, 14, 8, 10, 14),
      createCuboidShape(8, 0, 8, 14, 10, 14)
    );

    //    public static void eat(PlayerEntity player, )
    public JellyBeansCakeBlock() {
        this(FabricBlockSettings.copyOf(Blocks.CAKE));
    }

    public JellyBeansCakeBlock(Settings settings) {
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
        player.getHungerManager().add(2, 1f);
        player.addStatusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 2), player);
        player.incrementStat(Stats.BROKEN.getOrCreateStat(asItem()));
        player.emitGameEvent(GameEvent.EAT);
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0F, 1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F);
        if (player instanceof ServerPlayerEntity serverPlayer) {
            Criteria.CONSUME_ITEM.trigger(serverPlayer, asItem().getDefaultStack());
        }
        int bites = state.get(AGE_3);
        if (bites < 3) {
            world.setBlockState(pos, state.cycle(AGE_3));
        } else {
            world.setBlockState(pos, MyBlocks.JELLY_BEANS.getDefaultState());
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
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return COLLISION_SHAPES.get(state.get(AGE_3));
    }
}
