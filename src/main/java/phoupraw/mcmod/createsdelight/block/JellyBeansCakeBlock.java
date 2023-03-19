package phoupraw.mcmod.createsdelight.block;

import com.mojang.datafixers.util.Pair;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
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
        createCuboidShape(2, 0, 8, 14, 10, 14)),
      VoxelShapes.union(
        createCuboidShape(4, 0, 4, 12, 1, 8),
        createCuboidShape(2, 0, 8, 14, 10, 14)),
      VoxelShapes.union(
        createCuboidShape(4, 0, 4, 12, 1, 8),
        createCuboidShape(4, 0, 8, 8, 1, 12),
        createCuboidShape(8, 0, 8, 14, 10, 14))
    );
    public static final List<VoxelShape> COLLISION_SHAPES = List.of(
      createCuboidShape(2, 0, 2, 14, 10, 14),
      VoxelShapes.union(
        createCuboidShape(8, 0, 2, 14, 10, 8),
        createCuboidShape(2, 0, 8, 14, 10, 14)),
      createCuboidShape(2, 0, 8, 14, 10, 14),
      createCuboidShape(8, 0, 8, 14, 10, 14)
    );

    public static boolean apply(LivingEntity object, StatusEffectInstance effect) {
        StatusEffect type = effect.getEffectType();
        if (!type.isInstant()) return object.addStatusEffect(effect);
        int duration = effect.getDuration();
        int amplifier = effect.getAmplifier();
        for (int i = 0; i < duration; i++) type.applyInstantEffect(null, null, object, amplifier, 1);
        return true;
    }

    public static void eat(World world, BlockPos blockPos, BlockState eaten, PlayerEntity subject, int food, float saturationModifier, List<Pair<StatusEffectInstance, Float>> effects) {
        subject.getHungerManager().add(food, saturationModifier);
        for (Pair<StatusEffectInstance, Float> pair : effects) {
            if (world.getRandom().nextFloat() < pair.getSecond()) {
                apply(subject, pair.getFirst());
            }
        }
        subject.incrementStat(Stats.BROKEN.getOrCreateStat(eaten.getBlock().asItem()));
        subject.emitGameEvent(GameEvent.EAT);
//        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F);
        world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 1.0F, 1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F);
        var centerPos = Vec3d.ofCenter(blockPos);
        world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, eaten), centerPos.getX(), centerPos.getY(), centerPos.getZ(), 0, 0, 0);//TODO 吃东西的粒子
        if (subject instanceof ServerPlayerEntity serverPlayer) {
            Criteria.CONSUME_ITEM.trigger(serverPlayer, eaten.getBlock().asItem().getDefaultStack());
        }
    }

    public JellyBeansCakeBlock() {
        this(FabricBlockSettings.copyOf(Blocks.CAKE).breakInstantly());
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
        eat(world, pos, state, player, 2, 0.5f, List.of(Pair.of(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 4), 1f)));
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
