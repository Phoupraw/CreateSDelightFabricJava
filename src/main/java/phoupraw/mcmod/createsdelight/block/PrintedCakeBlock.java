package phoupraw.mcmod.createsdelight.block;

import com.google.common.collect.Multimap;
import com.simibubi.create.foundation.block.IBE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.block.entity.PrintedCakeBE;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.registry.CDBETypes;
import phoupraw.mcmod.createsdelight.registry.CDBlocks;

import java.util.*;

public class PrintedCakeBlock extends Block implements IBE<PrintedCakeBE> {

    public static final VoxelShape MIN_SHAPE = VoxelShapes.cuboid(0.4, 0, 0.4, 0.6, 0.2, 0.6);

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

    public static Collection<BlockBox> unionBoxes(Collection<BlockBox> boxes) {
        Queue<BlockBox> boxes1 = new ArrayDeque<>(boxes);
        int none = 0;
        int i = 0;
        while (boxes1.size() > 1 && none < boxes1.size() * 2) {
            none++;
            BlockBox face = boxes1.poll();
            for (var iterator = boxes1.iterator(); iterator.hasNext(); ) {
                i++;
                BlockBox face1 = iterator.next();
                BlockBox face0 = unionBox(face, face1);
                if (face0 != null) {
                    iterator.remove();
                    boxes1.offer(face0);
                    none = 0;
                    break;
                }
            }
            if (none > 0) {
                boxes1.offer(face);
            }
        }
        CreateSDelight.LOGGER.info("unionBoxes size=" + boxes.size() + " c=" + i);
        return boxes1;
    }

    public static Collection<BlockBox> unionBoxes2(Collection<BlockBox> boxes) {
        Map<Vec3i, BlockBox> map = new HashMap<>();
        int c = 0;
        for (BlockBox box : boxes) {
            for (int i = box.getMinX(); i < box.getMaxX(); i++) {
                for (int j = box.getMinY(); j < box.getMaxY(); j++) {
                    for (int k = box.getMinZ(); k < box.getMaxZ(); k++) {
                        map.put(new Vec3i(i, j, k), box);
                        c++;
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
                                c++;
                            }
                        }
                    }
                }
            }
        }
        return new HashSet<>(map.values());
    }

    public static Box block2box(BlockBox box, Vec3i size) {
        double x = size.getX(), y = size.getY(), z = size.getZ();
        return new Box(
          box.getMinX() / x, box.getMinY() / y, box.getMinZ() / z,
          box.getMaxX() / x, box.getMaxY() / y, box.getMaxZ() / z);
    }

    public static VoxelShape getShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        var be = (PrintedCakeBE) world.getBlockEntity(pos);
        VoxelShape shape = VoxelShapes.empty();
        if (be != null) {
            shape = be.getShape();
            if (shape == null) {
                var cake = be.getVoxelCake();
                if (cake != null) {
                    shape = content2shape(cake.getContent(), cake.getSize());
                    be.setShape(shape);
                } else {
                    shape = VoxelShapes.empty();
                }
            }
        }
        return shape;
    }

    public static VoxelShape content2shape(Multimap<CakeIngredient, BlockBox> content, Vec3i size) {
        VoxelShape shape = VoxelShapes.empty();
        for (BlockBox box : unionBoxes2(content.values())) {
            shape = VoxelShapes.union(shape, VoxelShapes.cuboid(block2box(box, size)));
        }
        return shape;
    }

    public static boolean canPlaceAt(WorldView world, BlockPos pos, VoxelShape shape) {
        if (shape.isEmpty()) return false;
        return !VoxelShapes.matchesAnywhere(world.getBlockState(pos.down()).getSidesShape(world, pos.down()).getFace(Direction.UP), shape.getFace(Direction.DOWN), BooleanBiFunction.ONLY_SECOND);
    }

    public PrintedCakeBlock() {
        this(FabricBlockSettings.copyOf(CDBlocks.JELLY_BEANS_CAKE).dynamicBounds());
    }

    public PrintedCakeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Class<PrintedCakeBE> getBlockEntityClass() {
        return PrintedCakeBE.class;
    }

    @Override
    public BlockEntityType<? extends PrintedCakeBE> getBlockEntityType() {
        return CDBETypes.PRINTED_CAKE;
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
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        VoxelShape shape = getShape(state, world, pos, ShapeContext.absent());
        return canPlaceAt(world, pos, shape);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = getShape(state, world, pos, context);
        return shape.isEmpty() ? MIN_SHAPE : shape;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        var pair = PrintedCakeBE.nbt2content(ctx.getStack());
        if (pair == null) return null;
        VoxelShape shape = content2shape(pair.getContent(), pair.getSize());
        BlockState state = super.getPlacementState(ctx);
        return state != null && canPlaceAt(ctx.getWorld(), ctx.getBlockPos(), shape) ? state : null;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (itemStack.getNbt() == null) {
            return;
        }
        var be = (PrintedCakeBE) world.getBlockEntity(pos);
        if (be == null) return;
        if (itemStack.hasCustomName()) {
            be.setCustomName(itemStack.getName());
        }
    }

    /**
     * 总是如同按住ctrl键选取方块那样复制NBT。也复制自定义名称。
     */
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        ItemStack stack = super.getPickStack(world, pos, state);
        var be = (PrintedCakeBE) world.getBlockEntity(pos);
        if (be != null) {//从MinecraftClient.addBlockEntityNbt复制的
            NbtCompound nbtCompound = be.createNbtWithIdentifyingData();
            BlockItem.setBlockEntityNbt(stack, be.getType(), nbtCompound);
            stack.setCustomName(be.getCustomName());
        }
        return stack;
    }

}
