package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.block.entity.CakeOvenBlockEntity;
import phoupraw.mcmod.createsdelight.block.entity.MadeVoxelBlockEntity;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;

public class MadeVoxelBlock extends HorizontalFacingBlock implements IBE<MadeVoxelBlockEntity>, IWrenchable {
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
    @Deprecated
    public static <E> Collection<E> reduceFailable(Collection<E> source, BinaryOperator<@Nullable E> combiner) {
        Queue<E> queue = new ConcurrentLinkedQueue<>(source);
        AtomicInteger breaker = new AtomicInteger();
        while (breaker.get() <= queue.size() * 2) {
            E e1 = queue.poll();
            if (e1 == null) continue;
            breaker.incrementAndGet();
            E e2 = queue.poll();
            if (e2 == null) continue;
            breaker.incrementAndGet();
            E e12 = combiner.apply(e1, e2);
            if (e12 == null) continue;
            queue.offer(e12);
            breaker.addAndGet(-2);
        }
        return queue;
    }
    public static Collection<Box> simplify(Collection<Box> boxes) {
        return boxes.parallelStream().reduce(new ArrayList<>(), (boxes1, box) -> boxes1, (boxes1, boxes2) -> boxes1);
    }
    public MadeVoxelBlock(Settings settings) {
        super(settings);
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
        return super.getOutlineShape(state, world, pos, context);//TODO
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return super.getCollisionShape(state, world, pos, context);//TODO
    }
    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return super.getPlacementState(ctx);//TODO
    }
    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        ItemStack itemStack = super.getPickStack(world, pos, state);
        BlockEntity blockEntity = world.getBlockEntity(pos);
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
}
