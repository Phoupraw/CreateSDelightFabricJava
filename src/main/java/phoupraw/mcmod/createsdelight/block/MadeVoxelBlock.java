package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import phoupraw.mcmod.createsdelight.block.entity.CakeOvenBlockEntity;
import phoupraw.mcmod.createsdelight.block.entity.MadeVoxelBlockEntity;
import phoupraw.mcmod.createsdelight.misc.*;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

import java.util.*;

public class MadeVoxelBlock extends HorizontalFacingBlock implements IBE<MadeVoxelBlockEntity>, IWrenchable {
    public static final DefaultedMap<VoxelRecord, VoxelShape> SHAPE_CACHE = DefaultedMap.loadingCache(MadeVoxelBlock::toShape);
    public static final DefaultedMap<VoxelRecord, DefaultedMap<Direction, VoxelShape>> FACING_SHAPE_CACHE = DefaultedMap.loadingCache(voxelRecord -> new FunctionDefaultedMap<>(new EnumMap<>(Direction.class), facing -> {
        VoxelShape shape = MadeVoxelBlock.SHAPE_CACHE.get(voxelRecord);
        VoxelShape rotated = VoxelShapes.empty();
        for (Box box : shape.getBoundingBoxes()) {
            rotated = VoxelShapes.union(rotated, VoxelShapes.cuboid(rotate(box, facing)));
        }
        return rotated;
    }));
    /**
     两点分布
     @param x
     @return
     */
    public static long twoPoint(double x) {
        long integer = (long) x;
        double decimal = x - integer;
        return Math.random() > decimal ? integer : integer + 1;
    }
    /**
     {@code a}和{@code b}不能重叠。如果可以合并，则返回合并后的结果，否则返回{@code null}。
     */
    public static @Nullable BlockBox union(BlockBox a, BlockBox b) {
        BlockBox ab = CakeOvenBlockEntity.toBlockBox(CakeOvenBlockEntity.toBox(a).union(CakeOvenBlockEntity.toBox(b)));
        if (getVolumn(a) + getVolumn(b) == getVolumn(ab)) {
            return ab;
        }
        return null;
    }
    /**
     计算体积。可能会溢出，所以别传入太大的箱。
     */
    public static int getVolumn(BlockBox a) {
        return a.getBlockCountX() * a.getBlockCountY() * a.getBlockCountZ();
    }
    public static VoxelShape toShape(VoxelRecord voxelRecord) {
        Vec3i size = voxelRecord.size();
        int x0 = size.getX();
        int y0 = size.getY();
        int z0 = size.getZ();
        SortedSet<BlockPos> posSet = new TreeSet<>(Comparator.comparingInt(BlockPos::getX).thenComparingInt(BlockPos::getZ).thenComparingInt(BlockPos::getY));
        posSet.addAll(voxelRecord.blocks().keySet());
        Collection<Box> boxes = new ArrayList<>();
        BlockPos.Mutable pos = new BlockPos.Mutable();
        while (!posSet.isEmpty()) {
            BlockPos start = posSet.first();
            posSet.remove(start);
            pos.set(start);
            int x1 = start.getX();
            int y1 = start.getY();
            int z1 = start.getZ();
            int x2 = x1 + 1;
            int y2 = y1 + 1;
            int z2 = z1 + 1;
            for (int i = x1 + 1; i < x2; i++) {
                pos.setX(i);
                if (!posSet.contains(pos)) {
                    x2 = i;
                    break;
                }
                posSet.remove(pos);
            }
            pos.set(start);
            outer:
            for (int i = z1 + 1; i < z2; i++) {
                pos.setZ(i);
                for (int j = x1; j < x2; j++) {
                    pos.setX(j);
                    if (!posSet.contains(pos)) {
                        z2 = i;
                        break outer;
                    }
                    posSet.remove(pos);
                }
            }
            pos.set(start);
            outer:
            for (int i = y1 + 1; i < y2; i++) {
                pos.setY(i);
                for (int j = z1; j < z2; j++) {
                    pos.setZ(j);
                    for (int k = x1; k < x2; k++) {
                        pos.setX(k);
                        if (!posSet.contains(pos)) {
                            y2 = i;
                            break outer;
                        }
                        posSet.remove(pos);
                    }
                }
            }
            boxes.add(new Box((double) x1 / x0, (double) y1 / y0, (double) z1 / z0, (double) x2 / x0, (double) y2 / y0, (double) z2 / z0));
        }
        return boxes.stream().map(VoxelShapes::cuboid).reduce(VoxelShapes.empty(), VoxelShapes::union);
    }
    public static Box rotate(Box box, Direction facing) {
        double angle = (facing.getHorizontal() - PrintedCakeBlock.defaultFacing().getHorizontal()) * Math.PI / 2;
        var min = new Vector3d()
          .set(box.minX - 0.5, box.minY, box.minZ - 0.5)
          .rotateY(angle)
          .add(0.5, 0, 0.5);
        var max = new Vector3d()
          .set(box.maxX - 0.5, box.maxY, box.maxZ - 0.5)
          .rotateY(angle)
          .add(0.5, 0, 0.5);
        return new Box(min.x(), min.y(), min.z(), max.x(), max.y(), max.z());
    }
    public MadeVoxelBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, PrintedCakeBlock.defaultFacing()));
    }
    @Override
    public Class<MadeVoxelBlockEntity> getBlockEntityClass() {
        return MadeVoxelBlockEntity.class;
    }
    @Override
    public BlockEntityType<? extends MadeVoxelBlockEntity> getBlockEntityType() {
        return CSDBlockEntityTypes.MADE_VOXEL;
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = getCollisionShape(state, world, pos, context);
        return !shape.isEmpty() ? shape : Blocks.CAKE.getDefaultState().getOutlineShape(world, pos, context);
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();
        MadeVoxelBlockEntity blockEntity = getBlockEntity(world, pos);
        if (blockEntity != null) {
            VoxelRecord voxelRecord = blockEntity.getVoxelRecord();
            if (voxelRecord != null) {
                shape = FACING_SHAPE_CACHE.get(voxelRecord).get(state.get(FACING));
            }
        }
        return shape;
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) return ActionResult.CONSUME;
        MadeVoxelBlockEntity blockEntity = getBlockEntity(world, pos);
        VoxelRecord voxelRecord = blockEntity.getVoxelRecord();
        if (voxelRecord != null) {
            var size = voxelRecord.size();
            Comparator<BlockPos> comparator;
            if (player.isSneaking()) {
                var voxelHitPos = BlockPos.ofFloored(hit.getPos().subtract(Vec3d.of(pos)).multiply(size.getX(), size.getY(), size.getZ()));
                comparator = Comparator.comparingDouble(voxelHitPos::getSquaredDistance);
            } else {
                var center = BlockPos.ofFloored(0.5 * size.getX(), 0, 0.5 * size.getZ());
                comparator = Comparator.comparingDouble(center::getSquaredDistance);
                comparator = Comparator.comparingInt(BlockPos::getY).reversed().thenComparing(comparator.reversed());
            }
            List<Map.Entry<BlockPos, BlockState>> sortedBlocks = voxelRecord.blocks().entrySet().stream().sorted(Map.Entry.comparingByKey(comparator)).toList();
            Map<BlockPos, BlockState> newBlocks = new HashMap<>(voxelRecord.blocks());
            double hunger = 0;
            double saturation = 0;
            HungerManager hungerManager = player.getHungerManager();
            int playerHunger = hungerManager.getFoodLevel();
            double playerSaturation = hungerManager.getSaturationLevel();
            double cubicMeters = 1.0 / (size.getX() * size.getY() * size.getZ());
            boolean removed = false;
            double scale = 32;
            for (Map.Entry<BlockPos, BlockState> entry : sortedBlocks) {
                double nowHunger = hunger + playerHunger;
                if (nowHunger >= 20 /*|| saturation + playerSaturation >= nowHunger*/) break;
                FoodBehaviour foodBehaviour = BlockFoods.BLOCK_STATE.get(entry.getValue());
                if (foodBehaviour == null) continue;
                hunger += foodBehaviour.getHunger(cubicMeters) * scale;
                saturation += foodBehaviour.getSaturation(cubicMeters) * scale;
                newBlocks.remove(entry.getKey());
                removed = true;
            }
            if (!removed) return ActionResult.CONSUME;
            hungerManager.add((int) twoPoint(hunger), (float) (saturation / hunger / 2));
            if (newBlocks.isEmpty()) {
                world.removeBlock(pos, false);
            } else {
                blockEntity.setVoxelRecord(new VoxelRecord(newBlocks, size));
                blockEntity.sendData();
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.CONSUME;
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx);//TODO
    }
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        ItemStack itemStack = super.getPickStack(world, pos, state);
        BlockEntity blockEntity = getBlockEntity(world, pos);
        BlockItem.setBlockEntityNbt(itemStack, blockEntity.getType(), blockEntity.createNbt());
        return itemStack;
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
    public ActionResult onSneakWrenched(BlockState state, ItemUsageContext context) {
        return this.onWrenched(state.rotate(BlockRotation.CLOCKWISE_180), context);
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING);
    }
}
