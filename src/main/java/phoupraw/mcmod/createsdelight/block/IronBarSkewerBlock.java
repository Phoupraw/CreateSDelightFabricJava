package phoupraw.mcmod.createsdelight.block;

import com.google.common.base.Predicates;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.recipe.RecipeConditions;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeType;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import phoupraw.mcmod.createsdelight.block.entity.IronBarSkewerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;

import java.util.Map;
public class IronBarSkewerBlock extends RotatedPillarKineticBlock implements IBE<IronBarSkewerBlockEntity> {
    public static final Map<Direction.Axis, VoxelShape> SHAPES = Map.of(
      Direction.Axis.Y, createCuboidShape(7, 0, 7, 9, 16, 9),
      Direction.Axis.X, createCuboidShape(0, 7, 7, 16, 9, 9),
      Direction.Axis.Z, createCuboidShape(7, 7, 0, 9, 9, 16)
    );

    public IronBarSkewerBlock() {
        this(FabricBlockSettings.copyOf(Blocks.IRON_BARS));
    }

    public IronBarSkewerBlock(Settings properties) {
        super(properties);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.get(Properties.AXIS);
    }

    @Override
    public Class<IronBarSkewerBlockEntity> getBlockEntityClass() {
        return IronBarSkewerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends IronBarSkewerBlockEntity> getBlockEntityType() {
        return MyBlockEntityTypes.IRON_BAR_SKEWER;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES.get(state.get(Properties.AXIS));
    }

    @Override
    public boolean hasShaftTowards(WorldView world, BlockPos pos, BlockState state, Direction face) {
        return state.get(Properties.AXIS) == face.getAxis();
    }

    @Override
    public void onStateReplaced(BlockState oldState, World world, BlockPos pos, BlockState newState, boolean moving) {
        //        if (newState.isOf(this) && newState.get(Properties.AXIS).isVertical() && !TransferUtil.getNonEmpty(((IronBarSkewerBlockEntity) world.getBlockEntity(pos)).storage).iterator().hasNext()) {
//            world.setBlockState(pos, Blocks.IRON_BARS.getDefaultState());
//        }

        IBE.onRemove(oldState, world, pos, newState);
        super.onStateReplaced(oldState, world, pos, newState, moving);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack stackInHand = player.getStackInHand(hand);
        var skewer = ((IronBarSkewerBlockEntity) world.getBlockEntity(pos));
        @SuppressWarnings("ConstantConditions") var skewerS = skewer.storage;
        if (stackInHand.isEmpty()) {
            return StorageUtil.move(skewerS, PlayerInventoryStorage.of(player), Predicates.alwaysTrue(), Long.MAX_VALUE, null) != 0 ? ActionResult.SUCCESS : ActionResult.FAIL;
        }
        var recipe = world.getRecipeManager().listAllOfType(RecipeType.CAMPFIRE_COOKING).stream().filter(RecipeConditions.firstIngredientMatches(stackInHand)).findFirst().orElse(null);
        if (recipe != null) {
            return StorageUtil.move(PlayerInventoryStorage.of(player).getHandSlot(hand), skewerS, Predicates.alwaysTrue(), Long.MAX_VALUE, null) != 0 ? ActionResult.SUCCESS : ActionResult.FAIL;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return Items.IRON_BARS.getDefaultStack();
    }
}
