package phoupraw.mcmod.createsdelight.block;

import com.nhoryzon.mc.farmersdelight.registry.ParticleTypesRegistry;
import com.nhoryzon.mc.farmersdelight.registry.SoundsRegistry;
import com.simibubi.create.foundation.block.ITE;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.SingleFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.api.ConstantSingleFluidStorage;
import phoupraw.mcmod.createsdelight.api.ConstantSingleItemStorage;
import phoupraw.mcmod.createsdelight.block.entity.PanBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;

import java.util.function.Predicate;
//TODO 支架
public class PanBlock extends Block implements ITE<PanBlockEntity> {
    public static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 4.0, 15.0);

    public static <T> Predicate<@Nullable T> always() {
        return t -> true;
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
            if (handStorage!=null) {
                var panStorage = pan.getTankBehaviour().getCapability();
                var tempStorage = new ConstantSingleFluidStorage(FluidConstants.BOTTLE);
                var amount = StorageUtil.move(panStorage, tempStorage, always(), Long.MAX_VALUE, transaction);
                StorageUtil.move(handStorage, panStorage, always(), Long.MAX_VALUE, transaction);
                if (StorageUtil.move(tempStorage, handStorage, always(), Long.MAX_VALUE, transaction) == amount) {
                    transaction.commit();
                    return ActionResult.SUCCESS;
                }
            }
        }
        boolean success = false;
        try (var transaction = Transaction.openOuter()) {
            var playerStorage = PlayerInventoryStorage.of(player);
            var panStorage = pan.getTransportedBehaviour().getStorage();
            var tempStorage = new ConstantSingleItemStorage(1);
            var amount = StorageUtil.move(panStorage, tempStorage, always(), Long.MAX_VALUE, transaction);
            StorageUtil.move(playerStorage.getHandSlot(hand), panStorage, always(), Long.MAX_VALUE, transaction);
            if (StorageUtil.move(tempStorage, playerStorage, always(), Long.MAX_VALUE, transaction) == amount) {
                transaction.commit();
                success = true;
            }
        }
        if (success) {
            var transported = pan.getTransportedBehaviour().getStorage().getTransported();
            transported.insertedFrom = player.getHorizontalFacing();
            transported.prevBeltPosition = .25f;
            transported.beltPosition = .25f;
            pan.sendData();
            return ActionResult.SUCCESS;
        }
        return ActionResult.FAIL;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if (world.getBlockEntity(pos) instanceof PanBlockEntity pan && pan.isProcessing()) {
            if (random.nextInt(10) == 0) {
                world.playSound(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SoundsRegistry.BLOCK_SKILLET_SIZZLE.get(), SoundCategory.BLOCKS, 0.4F, random.nextFloat() * 0.2F + 0.9F, false);
            }
//            if (random.nextInt(5) == 0) {
            world.addParticle(ParticleTypesRegistry.STEAM.get(), pos.getX() + 0.5 + (random.nextDouble() * 0.4 - 0.2), pos.getY() + 0.1, pos.getZ() + 0.5 + (random.nextDouble() * 0.4 - 0.2), 0.0, random.nextBoolean() ? 0.015 : 0.005, 0.0);
//            }
        }
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
