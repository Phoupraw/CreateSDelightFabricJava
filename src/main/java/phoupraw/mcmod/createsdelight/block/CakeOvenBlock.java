package phoupraw.mcmod.createsdelight.block;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.block.entity.CakeOvenBlockEntity;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CakeOvenBlock extends KineticBlock implements IBE<CakeOvenBlockEntity> {
    public static final BiMap<RailShape, Set<Direction>> BI_DIRECTION = EnumHashBiMap.create(Map.of(
      RailShape.EAST_WEST, EnumSet.of(Direction.EAST, Direction.WEST),
      RailShape.NORTH_SOUTH, EnumSet.of(Direction.NORTH, Direction.SOUTH),
      RailShape.SOUTH_EAST, EnumSet.of(Direction.SOUTH, Direction.EAST),
      RailShape.SOUTH_WEST, EnumSet.of(Direction.SOUTH, Direction.WEST),
      RailShape.NORTH_WEST, EnumSet.of(Direction.NORTH, Direction.WEST),
      RailShape.NORTH_EAST, EnumSet.of(Direction.NORTH, Direction.EAST)
    ));
    public static final BiMap<RailShape, Map<Direction.Axis, Direction>> BI_DIRECTION_MAP = EnumHashBiMap.create(Map.of(
      RailShape.SOUTH_EAST, Map.of(Direction.Axis.Z, Direction.SOUTH, Direction.Axis.X, Direction.EAST),
      RailShape.SOUTH_WEST, Map.of(Direction.Axis.Z, Direction.SOUTH, Direction.Axis.X, Direction.WEST),
      RailShape.NORTH_WEST, Map.of(Direction.Axis.Z, Direction.NORTH, Direction.Axis.X, Direction.WEST),
      RailShape.NORTH_EAST, Map.of(Direction.Axis.Z, Direction.NORTH, Direction.Axis.X, Direction.EAST)
    ));
    static {
        var BI_DIRECTION_MAP_ = BI_DIRECTION.entrySet().stream().collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, entry -> entry
          .getValue()
          .stream()
          .collect(Collectors.groupingBy(Direction::getAxis))
          .entrySet()
          .stream()
          .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, entry1 -> entry1.getValue().get(0)))));
    }
    public static final EnumProperty<RailShape> FACING = EnumProperty.of("facing", RailShape.class, RailShape.NORTH_EAST, RailShape.NORTH_WEST, RailShape.SOUTH_EAST, RailShape.SOUTH_WEST);
    public static RailShape rotate(RailShape facing, BlockRotation rotation) {
        return BI_DIRECTION.inverse().get(BI_DIRECTION.get(facing).stream().map(rotation::rotate).collect(Collectors.toSet()));
    }
    public static RailShape mirror(RailShape facing, BlockMirror mirror) {
        return BI_DIRECTION.inverse().get(BI_DIRECTION.get(facing).stream().map(mirror::apply).collect(Collectors.toSet()));
    }
    public CakeOvenBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, RailShape.NORTH_WEST));
    }
    public CakeOvenBlock() {
        this(FabricBlockSettings.copyOf(Blocks.COPPER_BLOCK));
    }
    @Override
    public SpeedLevel getMinimumRequiredSpeedLevel() {
        return SpeedLevel.NONE;
    }
    @Override
    public Class<CakeOvenBlockEntity> getBlockEntityClass() {
        return CakeOvenBlockEntity.class;
    }
    @Override
    public BlockEntityType<? extends CakeOvenBlockEntity> getBlockEntityType() {
        return CSDBlockEntityTypes.CAKE_OVEN;
    }
    @SuppressWarnings({"deprecation"})
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        neighborUpdate3(state, world, pos, sourceBlock, sourcePos, notify);
    }
    public void neighborUpdate3(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!(world.getBlockEntity(pos) instanceof CakeOvenBlockEntity be)) return;
        if (world.isReceivingRedstonePower(pos)) {
            if (be.getTimeBegin() == -1) {
                be.setTimeBegin(world.getTime());
                be.sendData();
            }
        } else if (be.getTimeBegin() != -1) {
            be.setTimeBegin(-1);
            be.sendData();
        }
    }
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack handStack = player.getStackInHand(hand);
        if (handStack.isOf(Items.NAME_TAG)) {
            var be = (CakeOvenBlockEntity) world.getBlockEntity(pos);
            if (be == null) return ActionResult.FAIL;
            if (handStack.hasCustomName()) {
                be.setCustomName(handStack.getName());
            } else {
                be.setCustomName(null);
            }
            return ActionResult.SUCCESS;
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
    @SuppressWarnings("deprecation")
    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotate(state.get(FACING), rotation));
    }
    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(FACING, mirror(state.get(FACING), mirror));
    }
    @Override
    public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
        return originalState.rotate(BlockRotation.CLOCKWISE_90);
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().rotate(BlockRotation.values()[Direction.fromRotation(ctx.getPlayerYaw() + 225).getHorizontal()]);
    }
    @Override
    public boolean hasShaftTowards(WorldView world, BlockPos pos, BlockState state, Direction face) {
        return false;
    }
    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return Direction.Axis.Y;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
