package phoupraw.mcmod.createsdelight.block;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.RailShape;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.block.entity.VoxelMakerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static net.minecraft.block.enums.RailShape.*;
import static net.minecraft.util.math.Direction.*;
import static net.minecraft.util.math.Direction.Axis.X;
import static net.minecraft.util.math.Direction.Axis.Z;

public class VoxelMakerBlock extends KineticBlock implements IBE<VoxelMakerBlockEntity>, ICogWheel {
    public static final EnumProperty<RailShape> FACING = EnumProperty.of("facing", RailShape.class, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST);
    public static final Map<RailShape, Map<Axis, Direction>> BI_DIRECTION_MAP = /*EnumHashBiMap.create*/(Map.of(
      SOUTH_EAST, Map.of(Z, SOUTH, X, EAST),
      SOUTH_WEST, Map.of(Z, SOUTH, X, WEST),
      NORTH_WEST, Map.of(Z, NORTH, X, WEST),
      NORTH_EAST, Map.of(Z, NORTH, X, EAST)
    ));
    public static final BiMap<RailShape, Set<Direction>> BI_DIRECTION = EnumHashBiMap.create(Map.of(
      EAST_WEST, EnumSet.of(EAST, WEST),
      NORTH_SOUTH, EnumSet.of(NORTH, SOUTH),
      SOUTH_EAST, EnumSet.of(SOUTH, EAST),
      SOUTH_WEST, EnumSet.of(SOUTH, WEST),
      NORTH_WEST, EnumSet.of(NORTH, WEST),
      NORTH_EAST, EnumSet.of(NORTH, EAST)
    ));
    public static RailShape rotate(RailShape facing, BlockRotation rotation) {
        return BI_DIRECTION.inverse().get(BI_DIRECTION.get(facing).stream().map(rotation::rotate).collect(Collectors.toSet()));
    }
    public static RailShape mirror(RailShape facing, BlockMirror mirror) {
        return BI_DIRECTION.inverse().get(BI_DIRECTION.get(facing).stream().map(mirror::apply).collect(Collectors.toSet()));
    }
    public VoxelMakerBlock(Settings properties) {
        super(properties);
    }
    @Override
    public Axis getRotationAxis(BlockState state) {
        return Axis.Y;
    }
    @Override
    public Class<VoxelMakerBlockEntity> getBlockEntityClass() {
        return VoxelMakerBlockEntity.class;
    }
    @Override
    public BlockEntityType<? extends VoxelMakerBlockEntity> getBlockEntityType() {
        return CSDBlockEntityTypes.VOXEL_MAKER;
    }
    @SuppressWarnings("deprecation")
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        VoxelMakerBlockEntity be = getBlockEntity(world, pos);
        if (be == null) return;
        if (world.isReceivingRedstonePower(pos)) {
            if (be.getWorking() == TriState.DEFAULT) {
                be.setWorking(TriState.TRUE);
                be.sendData();
            }
        } else {
            be.setWorking(TriState.DEFAULT);
            be.sendData();
        }
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
        return getDefaultState().rotate(BlockRotation.values()[Direction.fromRotation(ctx.getPlayerYaw() + 135).getHorizontal()]);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    @Override
    public ActionResult onWrenched(BlockState state, ItemUsageContext context) {
        var be = getBlockEntity(context.getWorld(), context.getBlockPos());
        if (be == null) return ActionResult.FAIL;
        if (!be.getWorking().get()) {
            return super.onWrenched(state, context);
        }
        return ActionResult.FAIL;
    }
}
