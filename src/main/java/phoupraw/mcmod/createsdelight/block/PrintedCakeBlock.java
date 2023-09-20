package phoupraw.mcmod.createsdelight.block;

import com.google.common.collect.Multimap;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import phoupraw.mcmod.createsdelight.block.entity.PrintedCakeBlockEntity;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

import java.util.*;

public class PrintedCakeBlock extends HorizontalFacingBlock implements IBE<PrintedCakeBlockEntity>, IWrenchable {
    public static final VoxelShape MIN_SHAPE = VoxelShapes.cuboid(0.4, 0, 0.4, 0.6, 0.2, 0.6);
    public static boolean canPlaceAt(WorldView world, BlockPos pos, VoxelShape shape) {
        if (shape.isEmpty()) return false;
        return !VoxelShapes.matchesAnywhere(world.getBlockState(pos.down()).getSidesShape(world, pos.down()).getFace(Direction.UP), shape.getFace(Direction.DOWN), BooleanBiFunction.ONLY_SECOND);
    }
    public static VoxelShape getShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        var be = (PrintedCakeBlockEntity) world.getBlockEntity(pos);
        VoxelShape shape = VoxelShapes.empty();
        if (be != null) {
            Direction facing = state.get(FACING);
            shape = be.shapes.get(facing);
            if (shape == null) {
                VoxelCake cake = be.getVoxelCake();
                if (cake != null) {
                    shape = content2shape(cake.getContent(), cake.getSize(), facing);
                    be.shapes.put(facing, shape);
                } else {
                    shape = VoxelShapes.empty();
                }
            }
        }
        return shape;
    }
    public static VoxelShape content2shape(Multimap<CakeIngredient, BlockBox> content, Vec3i size, Direction facing) {
        VoxelShape shape = VoxelShapes.empty();
        Collection<BlockBox> boxes = new ArrayList<>();
        for (BlockBox box : content.values()) {
            boxes.add(rotate(box, size, facing));
        }
        for (BlockBox box : unionBoxes(boxes)) {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(block2box(box, size)));
        }
        return shape;
    }
    public static BlockBox rotate(BlockBox box, Vec3i size, Direction facing) {
        double offsetX = size.getX() / 2.0, offsetZ = size.getZ() / 2.0;
        double angle = -facing.getHorizontal() * Math.PI / 2;
        var min = new Vector3d()
          .set(box.getMinX() - offsetX, box.getMinY(), box.getMinZ() - offsetZ)
          .rotateY(angle)
          .add(offsetX, 0, offsetZ);
        var max = new Vector3d()
          .set(box.getMaxX() - offsetX, box.getMaxY(), box.getMaxZ() - offsetZ)
          .rotateY(angle)
          .add(offsetX, 0, offsetZ);
        return BlockBox.create(
          new Vec3i((int) Math.round(min.x()), (int) Math.round(min.y()), (int) Math.round(min.z())),
          new Vec3i((int) Math.round(max.x()), (int) Math.round(max.y()), (int) Math.round(max.z()))
        );
    }
    /**
     合并，简化
     @param boxes
     @return
     */
    public static Collection<BlockBox> unionBoxes(Collection<BlockBox> boxes) {
        Map<Vec3i, BlockBox> map = new HashMap<>();
        for (BlockBox box : boxes) {
            for (int i = box.getMinX(); i < box.getMaxX(); i++) {
                for (int j = box.getMinY(); j < box.getMaxY(); j++) {
                    for (int k = box.getMinZ(); k < box.getMaxZ(); k++) {
                        map.put(new Vec3i(i, j, k), box);
                    }
                }
            }
        }
        Direction[] directions = Direction.values();
        for (Map.Entry<Vec3i, BlockBox> entry : map.entrySet()) {
            Vec3i pos1 = entry.getKey();
            BlockBox box1 = entry.getValue();
            for (Direction direction : directions) {
                Vec3i pos2 = pos1.offset(direction);
                BlockBox box2 = map.get(pos2);
                if (box2 == null || box2 == box1) continue;
                BlockBox box3 = unionBox(box1, box2);
                if (box3 != null) {
                    box1 = box3;
                    for (int i = box3.getMinX(); i < box3.getMaxX(); i++) {
                        for (int j = box3.getMinY(); j < box3.getMaxY(); j++) {
                            for (int k = box3.getMinZ(); k < box3.getMaxZ(); k++) {
                                map.put(new Vec3i(i, j, k), box3);
                            }
                        }
                    }
                }
            }
        }
        return new HashSet<>(map.values());
    }
    public static @Nullable BlockBox unionBox(BlockBox box1, BlockBox box2) {
        boolean
          x = box1.getMinX() == box2.getMinX() && box1.getMaxX() == box2.getMaxX(),
          y = box1.getMinY() == box2.getMinY() && box1.getMaxY() == box2.getMaxY(),
          z = box1.getMinZ() == box2.getMinZ() && box1.getMaxZ() == box2.getMaxZ();
        if (box1.getMinX() < box2.getMaxX() && box1.getMaxX() == box2.getMinX() && y && z
            || box1.getMinY() < box2.getMaxY() && box1.getMaxY() == box2.getMinY() && x && z
            || box1.getMinZ() < box2.getMaxZ() && box1.getMaxZ() == box2.getMinZ() && x && y) {
            return BlockBox.encompass(List.of(box1, box2)).orElseThrow();
        }
        var temp = box1;
        box1 = box2;
        box2 = temp;
        if (box1.getMinX() < box2.getMaxX() && box1.getMaxX() == box2.getMinX() && y && z
            || box1.getMinY() < box2.getMaxY() && box1.getMaxY() == box2.getMinY() && x && z
            || box1.getMinZ() < box2.getMaxZ() && box1.getMaxZ() == box2.getMinZ() && x && y) {
            return BlockBox.encompass(List.of(box1, box2)).orElseThrow();
        }
        return null;
    }
    public static Box block2box(BlockBox box, Vec3i size) {
        double x = size.getX(), y = size.getY(), z = size.getZ();
        return new Box(
          box.getMinX() / x, box.getMinY() / y, box.getMinZ() / z,
          box.getMaxX() / x, box.getMaxY() / y, box.getMaxZ() / z);
    }
    public PrintedCakeBlock() {
        this(FabricBlockSettings.copyOf(Blocks.CAKE).nonOpaque().dynamicBounds());
    }
    public static @NotNull Direction defaultFacing() {
        return Direction.SOUTH;
    }
    public PrintedCakeBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, defaultFacing()));
    }
    @Override
    public Class<PrintedCakeBlockEntity> getBlockEntityClass() {
        return PrintedCakeBlockEntity.class;
    }
    @Override
    public BlockEntityType<? extends PrintedCakeBlockEntity> getBlockEntityType() {
        return CSDBlockEntityTypes.PRINTED_CAKE;
    }
    @SuppressWarnings("deprecation")
    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        //        CreateSDelight.LOGGER.info(Block.shouldDrawSide(world.getBlockState(sourcePos), world, sourcePos, Direction.fromVector(pos.subtract(sourcePos)), pos));
        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
        //        CreateSDelight.LOGGER.info(Block.shouldDrawSide(state, world, pos, Direction.fromVector(sourcePos.subtract(pos)), sourcePos));
        //        CreateSDelight.LOGGER.info(state.getOutlineShape(world,pos));
        //        CreateSDelight.LOGGER.info(world.hashCode());
    }
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        //player.getHungerManager()
        return super.onUse(state, world, pos, player, hand, hit);
    }
    @SuppressWarnings("deprecation")
    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = getShape(state, world, pos, context);
        return shape.isEmpty() ? Blocks.CAKE.getDefaultState().getOutlineShape(world, pos, context) : shape;
    }
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return getShape(state, world, pos, context);
    }
    @Override
    public ActionResult onSneakWrenched(BlockState state, ItemUsageContext context) {
        return this.onWrenched(state.rotate(BlockRotation.CLOCKWISE_180), context);
    }
    @Override
    public void playRotateSound(World world, BlockPos pos) {
        world.playSound(null, pos, this.soundGroup.getPlaceSound(), SoundCategory.BLOCKS, 1, 1);
    }
    @Override
    public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
        return originalState.rotate(BlockRotation.CLOCKWISE_90);
    }
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
    }
    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (itemStack.getNbt() == null) {
            return;
        }
        var be = (PrintedCakeBlockEntity) world.getBlockEntity(pos);
        if (be == null) return;
        if (itemStack.hasCustomName()) {
            be.setCustomName(itemStack.getName());
        }
    }
    /**
     总是如同按住ctrl键选取方块那样复制NBT。也复制自定义名称。
     */
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        ItemStack stack = super.getPickStack(world, pos, state);
        var be = (PrintedCakeBlockEntity) world.getBlockEntity(pos);
        if (be != null) {
            BlockItem.setBlockEntityNbt(stack, be.getType(), be.writeBlockEntityTag(new NbtCompound()));
            if (be.getCustomName() != null) {
                stack.setCustomName(be.getCustomName());
            }
        }
        return stack;
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
