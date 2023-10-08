package phoupraw.mcmod.createsdelight.block;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
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
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.block.entity.MadeVoxelBlockEntity;
import phoupraw.mcmod.createsdelight.misc.BlockFoods;
import phoupraw.mcmod.createsdelight.misc.ConstDefaultedMap;
import phoupraw.mcmod.createsdelight.misc.FoodBehaviour;
import phoupraw.mcmod.createsdelight.misc.VoxelRecord;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MadeVoxelBlock extends HorizontalFacingBlock implements IBE<MadeVoxelBlockEntity>, IWrenchable {
    public static final Cache<VoxelRecord, Map<Direction, VoxelShape>> SHAPES = CacheBuilder.newBuilder().weakKeys().maximumSize(Long.MAX_VALUE).build();
    /**
     绕y轴旋转
     */
    @Contract(pure = true)
    public static VoxelShape rotateSequential(VoxelShape shape, Direction origin, Direction target) {
        if (target == origin) return shape;
        if (shape.isEmpty()) return shape;
        VoxelShape rotated = VoxelShapes.empty();
        for (Box box : shape.getBoundingBoxes()) {
            rotated = VoxelShapes.union(rotated, VoxelShapes.cuboid(rotate(box, origin, target)));
        }
        return rotated;
    }
    /**
     绕y轴旋转，使用并行流
     */
    @Contract(pure = true)
    public static VoxelShape rotateParallel(VoxelShape shape, Direction origin, Direction target) {
        if (target == origin) return shape;
        if (shape.isEmpty()) return shape;
        return shape.getBoundingBoxes()
          .parallelStream()
          .map(box -> VoxelShapes.cuboid(rotate(box, origin, target)))
          .reduce(VoxelShapes.empty(), VoxelShapes::union);
    }
    /**
     根据两点分布把小数取整。
     <table border="1">
     <caption>分布律</caption>
     <tbody>
     <tr><th>X</th><td align="center">⌊{@code x}⌋</td><td>⌊{@code x}⌋+1</td></tr>
     <tr><th>p</th><td>⌊{@code x}⌋+1-{@code x}</td><td align="center">{@code x}-⌊{@code x}⌋</td></tr>
     </tbody>
     </table>
     @param x 待取整的小数，包含了随机变量的取值范围和分布律信息
     @return 取得的整数，即随机变量X
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
        BlockBox ab = toBlockBox(toBox(a).union(toBox(b)));
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
    @Contract(pure = true)
    public static VoxelShape toShape(VoxelRecord voxelRecord) {
        Vec3i size = voxelRecord.size();
        int x0 = size.getX();
        int y0 = size.getY();
        int z0 = size.getZ();
        Set<BlockPos> posSet = voxelRecord.blocks()
          .keySet()
          .parallelStream()
          .sorted(Comparator
            .comparingInt(BlockPos::getY)
            .thenComparingInt(BlockPos::getX)
            .thenComparingInt(BlockPos::getZ))
          .collect(Collectors.toCollection(LinkedHashSet::new));
        CreateSDelight.LOGGER.debug("MadeVoxelBlock.toShape 总共有%d个体素".formatted(posSet.size()));
        Collection<Box> boxes = new LinkedList<>();
        BlockPos.Mutable pos = new BlockPos.Mutable();
        int loopC = 0;
        while (!posSet.isEmpty()) {
            BlockPos start = posSet.iterator().next();
            int x1 = start.getX();
            int y1 = start.getY();
            int z1 = start.getZ();
            int x2 = x1 + 1;
            int y2 = y1 + 1;
            int z2 = z1 + 1;
            pos.set(start);
            for (int i = x1 + 1; i <= x0; i++) {
                loopC++;
                pos.setX(i);
                if (!posSet.contains(pos)) {
                    x2 = i;
                    break;
                }
            }
            pos.set(start);
            outer:
            for (int i = z1 + 1; i <= z0; i++) {
                pos.setZ(i);
                for (int j = x1; j < x2; j++) {
                    loopC++;
                    pos.setX(j);
                    if (!posSet.contains(pos)) {
                        z2 = i;
                        break outer;
                    }
                }
            }
            pos.set(start);
            outer:
            for (int i = y1 + 1; i <= y0; i++) {
                pos.setY(i);
                for (int j = z1; j < z2; j++) {
                    pos.setZ(j);
                    for (int k = x1; k < x2; k++) {
                        loopC++;
                        pos.setX(k);
                        if (!posSet.contains(pos)) {
                            y2 = i;
                            break outer;
                        }
                    }
                }
            }
            for (BlockPos pos1 : BlockPos.iterate(x1, y1, z1, x2 - 1, y2 - 1, z2 - 1)) {
                posSet.remove(pos1);
            }
            boxes.add(new Box((double) x1 / x0, (double) y1 / y0, (double) z1 / z0, (double) x2 / x0, (double) y2 / y0, (double) z2 / z0));
        }
        CreateSDelight.LOGGER.debug("MadeVoxelBlock.toShape 循环了%d次".formatted(loopC));
        CreateSDelight.LOGGER.debug("MadeVoxelBlock.toShape 优化至%d个碰撞箱".formatted(boxes.size()));
        return boxes.parallelStream().map(VoxelShapes::cuboid).reduce(VoxelShapes.empty(), VoxelShapes::union);
    }
    /**
     绕y轴旋转
     */
    @Contract(pure = true)
    public static Box rotate(Box box, Direction origin, Direction target) {
        double angle = (target.getHorizontal() - origin.getHorizontal()) * Math.PI / 2;
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
    public static @NotNull Direction defaultFacing() {
        return Direction.SOUTH;
    }
    public static BlockBox toBlockBox(Box box) {
        return new BlockBox((int) box.minX, (int) box.minY, (int) box.minZ, (int) box.maxX, (int) box.maxY, (int) box.maxZ);
    }
    public static Box toBox(BlockBox box) {
        return new Box(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ());
    }
    public MadeVoxelBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, defaultFacing()));
    }
    @Override
    public Class<MadeVoxelBlockEntity> getBlockEntityClass() {
        return MadeVoxelBlockEntity.class;
    }
    @Override
    public BlockEntityType<? extends MadeVoxelBlockEntity> getBlockEntityType() {
        return CSDBlockEntityTypes.MADE_VOXEL;
    }
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = getCollisionShape(state, world, pos, context);
        return !shape.isEmpty() ? shape : Blocks.CAKE.getDefaultState().getOutlineShape(world, pos, context);
    }
    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.empty();
        MadeVoxelBlockEntity blockEntity = getBlockEntity(world, pos);
        if (blockEntity != null) {
            VoxelRecord voxelRecord = blockEntity.getVoxelRecord();
            if (voxelRecord != null) {
                Direction facing = state.get(FACING);
                var shapes = SHAPES.getIfPresent(voxelRecord);
                if (shapes == null) {
                    shapes = new EnumMap<>(Direction.class);
                    shapes.put(defaultFacing(), toShape(voxelRecord));
                    SHAPES.put(voxelRecord, shapes);
                    CreateSDelight.LOGGER.debug("MadeVoxelBlock.getCollisionShape pos=" + pos);
                    CreateSDelight.LOGGER.debug("MadeVoxelBlock.getCollisionShape FACING_SHAPES.size()=" + SHAPES.size());
                }
                shape = shapes.get(facing);
                if (shape == null) {
                    shape = rotateParallel(shapes.get(defaultFacing()), defaultFacing(), facing);
                    shapes.put(facing, shape);
                }
            }
        }
        return shape;
    }
    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        HungerManager hungerManager = player.getHungerManager();
        int playerHunger = hungerManager.getFoodLevel();
        if (playerHunger >= 20) return ActionResult.CONSUME;//本来是要返回FAIL的，但是返回FAIL竟然不会阻止后续右键，我服了，这是什么时候有的bug？
        if (world.isClient()) return ActionResult.CONSUME;
        MadeVoxelBlockEntity blockEntity = getBlockEntity(world, pos);
        if (blockEntity == null) return ActionResult.FAIL;
        VoxelRecord voxelRecord = blockEntity.getVoxelRecord();
        if (voxelRecord == null) return ActionResult.FAIL;
        var size = voxelRecord.size();
        Direction facing = state.get(FACING);
        Comparator<BlockPos> comparator;
        if (player.isSneaking()) {
            Vec3d relative = hit.getPos().subtract(Vec3d.of(pos));
            relative = relative.subtract(0.5, 0, 0.5).rotateY((float) (-facing.getHorizontal() * Math.PI / 2)).add(0.5, 0, 0.5);
            var voxelHitPos = BlockPos.fromPosition(relative.multiply(size.getX(), size.getY(), size.getZ()));
            comparator = Comparator.comparingDouble(voxelHitPos::getSquaredDistance);
        } else {
            var center = BlockPos.create(0.5 * size.getX(), 0, 0.5 * size.getZ());
            comparator = Comparator.comparingDouble(center::getSquaredDistance);
            comparator = Comparator.comparingInt(BlockPos::getY).reversed().thenComparing(comparator.reversed());
        }
        Map<BlockPos, Block> newBlocks = new HashMap<>(voxelRecord.blocks());
        var sortedBlocks = newBlocks.entrySet().stream().sorted(Entry.comparingByKey(comparator)).toList();
        double voxelCubicMeters = 1.0 / (size.getX() * size.getY() * size.getZ());
        boolean looped = false;
        Set<Block> eatenBlocks = new HashSet<>();
        Map<FoodBehaviour, Double> eatenFoods = new ConstDefaultedMap<>(new HashMap<>(), 0.0);
        for (var entry : sortedBlocks) {
            looped = true;
            Block block = entry.getValue();
            FoodBehaviour foodBehaviour = BlockFoods.BLOCK.get(block);
            eatenBlocks.add(block);
            eatenFoods.put(foodBehaviour, eatenFoods.get(foodBehaviour) + voxelCubicMeters);
            newBlocks.remove(entry.getKey());
            double hunger = 0;
            for (Entry<FoodBehaviour, Double> e : eatenFoods.entrySet()) {
                FoodBehaviour food = e.getKey();
                Double cubicMeters = e.getValue();
                hunger += food.getHunger(voxelRecord, cubicMeters);
            }
            if (hunger >= 1) break;
        }
        if (!looped) return ActionResult.CONSUME;
        double hunger = 0;
        double saturation = 0;
        double hungerSurpass = 0;
        for (Entry<FoodBehaviour, Double> e : eatenFoods.entrySet()) {
            FoodBehaviour food = e.getKey();
            Double cubicMeters = e.getValue();
            hunger += food.getHunger(voxelRecord, cubicMeters);
            saturation += food.getSaturation(voxelRecord, cubicMeters);
            hungerSurpass += food.getHungerSurpass(voxelRecord, cubicMeters);
        }
        double newHunger = Math.min(20, hungerManager.getFoodLevel() + hunger);
        double surpassHunger = hungerSurpass - (Math.min(20, newHunger + hungerSurpass) - newHunger);
        newHunger += surpassHunger;
        hungerSurpass -= surpassHunger;
        hungerManager.setFoodLevel((int) twoPoint(newHunger));
        double newSaturation = Math.min(newHunger, hungerManager.getSaturationLevel() + saturation + hungerSurpass);
        hungerManager.setSaturationLevel((float) newSaturation);
        world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.PLAYERS, 0.5f, 1);
        boolean burp = !hungerManager.isNotFull();
        if (newBlocks.isEmpty()) {
            world.removeBlock(pos, false);
            burp = true;
        } else {
            blockEntity.setVoxelRecord(VoxelRecord.of(newBlocks, size));
            blockEntity.sendData();
        }
        if (burp) {
            world.playSound(null, pos, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5f, 1);
        }
        int particleCount = (int) Math.ceil(5.0 / eatenBlocks.size());
        for (Block block : eatenBlocks) {
            ((ServerWorld) world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, block.getDefaultState()), hit.getPos().getX(), hit.getPos().getY(), hit.getPos().getZ(), particleCount, 0.1, 0.1, 0.1, 0);
        }
        return ActionResult.SUCCESS;
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerFacing());
    }
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        ItemStack itemStack = super.getPickStack(world, pos, state);
        BlockEntity blockEntity = getBlockEntity(world, pos);
        //noinspection ConstantConditions
        BlockItem.writeBlockEntityNbtToStack(itemStack, blockEntity.getType(), blockEntity.toNbt());
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
