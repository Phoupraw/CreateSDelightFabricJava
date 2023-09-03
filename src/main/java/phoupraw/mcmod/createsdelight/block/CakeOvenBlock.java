package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.RailShape;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.block.entity.CakeOvenBlockEntity;
import phoupraw.mcmod.createsdelight.block.entity.PrintedCakeBlockEntity;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;

import java.util.Objects;

public class CakeOvenBlock extends Block implements IBE<CakeOvenBlockEntity>, IWrenchable {

    public static final EnumProperty<RailShape> FACING = EnumProperty.of("facing", RailShape.class, RailShape.NORTH_EAST, RailShape.NORTH_WEST, RailShape.SOUTH_EAST, RailShape.SOUTH_WEST);

    public CakeOvenBlock() {
        this(FabricBlockSettings.copyOf(Blocks.COPPER_BLOCK));
    }

    public CakeOvenBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, RailShape.NORTH_WEST));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public Class<CakeOvenBlockEntity> getBlockEntityClass() {
        return CakeOvenBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CakeOvenBlockEntity> getBlockEntityType() {
        return CSDBlockEntityTypes.CAKE_OVEN;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        neighborUpdate2(state, world, pos, sourceBlock, sourcePos, notify);
    }

    public static void neighborUpdate2(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        var be = Objects.requireNonNull((CakeOvenBlockEntity) world.getBlockEntity(pos), world + " " + pos);
        if (!world.isReceivingRedstonePower(pos)) {
            if (be.powered) {
                be.powered = false;
            }
            return;
        }
        if (be.powered) return;
        be.powered = true;
        final int len = 16;
        VoxelCake cake = VoxelCake.of(world, BlockBox.create(pos.add(1, 1, 1), pos.add(len + 1, len + 1, len + 1)));
        if (cake.getContent().isEmpty()) return;
        BlockPos pos1 = pos.up();
        if (world.setBlockState(pos1, CSDBlocks.PRINTED_CAKE.getDefaultState())) {
            PrintedCakeBlockEntity blockEntity = Objects.requireNonNull((PrintedCakeBlockEntity) world.getBlockEntity(pos1), pos.toString());
            blockEntity.setVoxelCake(cake);
            blockEntity.sendData();
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

    public static RailShape rotate(RailShape facing, BlockRotation rotation) {
        int offset = RailShape.SOUTH_EAST.ordinal();
        if (facing.ordinal() < offset) throw new IllegalArgumentException(facing.toString());
        int addend = switch (rotation) {
            case NONE -> 0;
            case CLOCKWISE_90 -> 1;
            case CLOCKWISE_180 -> 2;
            case COUNTERCLOCKWISE_90 -> 3;
        };
        return RailShape.values()[(facing.ordinal() - offset + addend) % 4 + offset];
    }

    public static RailShape mirror(RailShape facing, BlockMirror mirror) {
        return switch (mirror) {
            case NONE -> facing;
            case FRONT_BACK -> switch (facing) {
                case SOUTH_EAST -> RailShape.SOUTH_WEST;
                case SOUTH_WEST -> RailShape.SOUTH_EAST;
                case NORTH_EAST -> RailShape.NORTH_WEST;
                case NORTH_WEST -> RailShape.NORTH_EAST;
                default -> throw new IllegalArgumentException(facing.toString());
            };
            case LEFT_RIGHT -> switch (facing) {
                case SOUTH_EAST -> RailShape.NORTH_EAST;
                case SOUTH_WEST -> RailShape.NORTH_WEST;
                case NORTH_EAST -> RailShape.SOUTH_EAST;
                case NORTH_WEST -> RailShape.SOUTH_WEST;
                default -> throw new IllegalArgumentException(facing.toString());
            };
        };
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(FACING, mirror(state.get(FACING), mirror));
    }

}
