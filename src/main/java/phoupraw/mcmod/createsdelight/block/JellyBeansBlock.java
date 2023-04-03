package phoupraw.mcmod.createsdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import phoupraw.mcmod.createsdelight.item.StatusEffectsBlockItem;
public class JellyBeansBlock extends SmallCakeBlock {
    public static final VoxelShape SHAPE = createCuboidShape(4.0, 0.0, 4.0, 12.0, 1.0, 12.0);

    public JellyBeansBlock() {
        this(FabricBlockSettings.copyOf(Blocks.REDSTONE_WIRE).sounds(BlockSoundGroup.WOOL));
    }

    public JellyBeansBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return !VoxelShapes.matchesAnywhere(world.getBlockState(pos.down()).getSidesShape(world, pos.down()).getFace(Direction.UP), SHAPE, BooleanBiFunction.ONLY_SECOND);
    }

    @Override
    public void onEachEat(World world, BlockPos blockPos, BlockState blockState, PlayerEntity player, BlockHitResult hitResult) {
        var food = asItem().getFoodComponent();
        //noinspection ConstantConditions
        StatusEffectsBlockItem.eat(world, blockPos, blockState, hitResult.getPos(), player, food.getHunger(), food.getSaturationModifier(), food.getStatusEffects());
    }
}
