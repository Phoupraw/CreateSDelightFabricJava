package phoupraw.mcmod.createsdelight.block;

import com.mojang.datafixers.util.Pair;
import com.nhoryzon.mc.farmersdelight.registry.EffectsRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.createsdelight.item.StatusEffectsBlockItem;
import phoupraw.mcmod.createsdelight.registry.CDStatusEffects;

import java.util.List;
public class SmallCakeBlock extends Block {
    public SmallCakeBlock() {
        this(FabricBlockSettings.copyOf(Blocks.CAKE).breakInstantly());
    }

    public SmallCakeBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        onEachEat(world, pos, state, player, hit);
        onFinalEat(world, pos, state);
        return ActionResult.SUCCESS;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        var ground = getGround(world, pos, state);
        return !VoxelShapes.matchesAnywhere(world.getBlockState(pos.down()).getSidesShape(world, pos.down()).getFace(Direction.UP), ground, BooleanBiFunction.ONLY_SECOND);
    }
    @SuppressWarnings("deprecation")
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @ApiStatus.OverrideOnly
    public void onEachEat(World world, BlockPos blockPos, BlockState blockState, PlayerEntity player, BlockHitResult hitResult) {
        StatusEffectsBlockItem.eat(world, blockPos, blockState, hitResult.getPos(), player, 5, 0.5f, List.of(Pair.of(new StatusEffectInstance(CDStatusEffects.SATIATION, 1, 3), 1f), Pair.of(new StatusEffectInstance(StatusEffects.SATURATION, 2, 0), 1f), Pair.of(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 20 * 60 * 2, 0), 1f)));
    }

    @ApiStatus.OverrideOnly
    public void onFinalEat(World world, BlockPos blockPos, BlockState blockState) {
        world.removeBlock(blockPos, false);
    }

    @ApiStatus.OverrideOnly
    public VoxelShape getGround(WorldView world, BlockPos blockPos, BlockState blockState) {
        return VoxelShapes.fullCube();
    }
}
