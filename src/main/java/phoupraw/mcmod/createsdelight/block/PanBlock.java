package phoupraw.mcmod.createsdelight.block;

import com.google.common.base.Predicates;
import com.nhoryzon.mc.farmersdelight.registry.ParticleTypesRegistry;
import com.nhoryzon.mc.farmersdelight.registry.SoundsRegistry;
import com.simibubi.create.foundation.block.ITE;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import phoupraw.mcmod.common.api.LivingEntityStorage;
import phoupraw.mcmod.createsdelight.block.entity.MyBlockEntity1;
import phoupraw.mcmod.createsdelight.block.entity.PanBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
import phoupraw.mcmod.createsdelight.storage.BlockingTransportedStorage;
import phoupraw.mcmod.createsdelight.storage.ConstantSingleFluidStorage;
import phoupraw.mcmod.createsdelight.storage.ConstantSingleItemStorage;

public class PanBlock extends Block implements ITE<PanBlockEntity> {
    public static final VoxelShape SHAPE = VoxelShapes.combine(Block.createCuboidShape(1, 0, 1, 15, 4, 15), Block.createCuboidShape(2, 1, 2, 14, 4, 14), BooleanBiFunction.ONLY_FIRST);

    public static boolean swapItem(LivingEntity living, Hand hand, BlockingTransportedStorage blockStorage) {
        boolean success = false;
        long toBlock;
        try (var transaction = Transaction.openOuter()) {
            var livingStorage = LivingEntityStorage.of(living);
            long capacity = blockStorage.getCapacity();
            var tempStorage = new ConstantSingleItemStorage(capacity);
            long toLiving = StorageUtil.move(blockStorage, tempStorage, Predicates.alwaysTrue(), capacity, transaction);
            toBlock = StorageUtil.move(livingStorage.get(hand), blockStorage, Predicates.alwaysTrue(), capacity, transaction);
            if (StorageUtil.move(tempStorage, livingStorage, Predicates.alwaysTrue(), capacity, transaction) == toLiving) {
                transaction.commit();
                success = toLiving > 0 || toBlock > 0;
            }
        }
        if (toBlock > 0) {
            var transported = blockStorage.getTransported();
            transported.insertedFrom = living.getHorizontalFacing();
            transported.prevBeltPosition = .25f;
            transported.beltPosition = .25f;
        }
        return success;
    }

    public static void addSteam(World world, BlockPos pos, Random random) {
        world.addParticle(ParticleTypesRegistry.STEAM.get(), pos.getX() + 0.5 + (random.nextDouble() * 0.4 - 0.2), pos.getY() + 0.1, pos.getZ() + 0.5 + (random.nextDouble() * 0.4 - 0.2), 0.0, random.nextBoolean() ? 0.015 : 0.005, 0.0);
    }

    public PanBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) return ActionResult.CONSUME;
        var pan = world.getBlockEntity(pos, MyBlockEntityTypes.PAN).orElseThrow();
        try (var transaction = Transaction.openOuter()) {
            var handStorage = FluidStorage.ITEM.find(player.getStackInHand(hand), ContainerItemContext.ofPlayerHand(player, hand));
            if (handStorage != null) {
                var panStorage = pan.getTank().getCapability();
                var tempStorage = new ConstantSingleFluidStorage(FluidConstants.BOTTLE);
                var amount = StorageUtil.move(panStorage, tempStorage, Predicates.alwaysTrue(), Long.MAX_VALUE, transaction);
                StorageUtil.move(handStorage, panStorage, Predicates.alwaysTrue(), Long.MAX_VALUE, transaction);
                if (StorageUtil.move(tempStorage, handStorage, Predicates.alwaysTrue(), Long.MAX_VALUE, transaction) == amount) {
                    transaction.commit();
                    return ActionResult.SUCCESS;
                }
            }
        }
        return swapItem(player, hand, pan.getItem().getStorage()) ? ActionResult.SUCCESS : ActionResult.FAIL;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (world.getBlockEntity(pos) instanceof MyBlockEntity1 pan && pan.isProcessing()) {
            if (random.nextInt(10) == 0) {
                world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundsRegistry.BLOCK_SKILLET_SIZZLE.get(), SoundCategory.BLOCKS, 0.4F, random.nextFloat() * 0.2F + 0.9F, false);
            }
            addSteam(world, pos, random);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (world.getBlockEntity(pos) instanceof PanBlockEntity pan) {
            ItemScatterer.spawn(world, pos, new SimpleInventory(pan.getItem().getStorage().getStack()));
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @Override
    public Class<PanBlockEntity> getTileEntityClass() {
        return PanBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends PanBlockEntity> getTileEntityType() {
        return MyBlockEntityTypes.PAN;
    }
}
