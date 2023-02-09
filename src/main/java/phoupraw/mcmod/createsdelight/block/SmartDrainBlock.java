package phoupraw.mcmod.createsdelight.block;

import com.nhoryzon.mc.farmersdelight.registry.ParticleTypesRegistry;
import com.nhoryzon.mc.farmersdelight.registry.SoundsRegistry;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.curiosities.girder.GirderCTBehaviour;
import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.api.ConstantSingleFluidStorage;
import phoupraw.mcmod.createsdelight.api.ConstantSingleItemStorage;
import phoupraw.mcmod.createsdelight.api.Lambdas;
import phoupraw.mcmod.createsdelight.api.LivingEntityStorage;
import phoupraw.mcmod.createsdelight.behaviour.BurnerBehaviour;
import phoupraw.mcmod.createsdelight.behaviour.GrillerBehaviour;
import phoupraw.mcmod.createsdelight.behaviour.SteamerBehaviour;
import phoupraw.mcmod.createsdelight.block.entity.SmartDrainBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
import phoupraw.mcmod.createsdelight.storage.BlockingTransportedStorage;
public class SmartDrainBlock extends Block implements ITE<SmartDrainBlockEntity> {

    public SmartDrainBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(Properties.LIT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(Properties.LIT);
    }

    @Override
    public Class<SmartDrainBlockEntity> getTileEntityClass() {
        return SmartDrainBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends SmartDrainBlockEntity> getTileEntityType() {
        return MyBlockEntityTypes.SMART_DRAIN;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AllShapes.CASING_13PX.get(Direction.UP);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        ITE.onRemove(state, world, pos, newState);
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hit.getSide() != Direction.UP) return super.onUse(state, world, pos, player, hand, hit);
        if (world.isClient()) return ActionResult.CONSUME;
        ItemStack handStack = player.getStackInHand(hand);
        var drain = world.getBlockEntity(pos, MyBlockEntityTypes.SMART_DRAIN).orElseThrow();
        if (handStack.isOf(Items.FLINT_AND_STEEL) || handStack.isOf(Items.FIRE_CHARGE)) {
            if (drain.getBurner().tryIgnite() > 0) {
                if (handStack.isOf(Items.FLINT_AND_STEEL)) {
                    handStack.damage(1, player, Lambdas.nothing());
                    world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1, 1);
                } else if (handStack.isOf(Items.FIRE_CHARGE)) {
                    handStack.decrement(1);
                    world.playSound(null, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1, 1);
                }
                return ActionResult.SUCCESS;
            }
        }
        try (var transaction = Transaction.openOuter()) {
            var handStorage = FluidStorage.ITEM.find(player.getStackInHand(hand), ContainerItemContext.ofPlayerHand(player, hand));
            if (handStorage != null) {
                var panStorage = drain.getTank().getPrimaryHandler();
                if (StorageUtil.move(handStorage, panStorage, Lambdas.always(), Long.MAX_VALUE, transaction) > 0 || StorageUtil.move(panStorage, handStorage, Lambdas.always(), Long.MAX_VALUE, transaction) > 0) {
                    transaction.commit();
                    return ActionResult.SUCCESS;
                }
            }
        }
        var facing = Direction.getEntityFacingOrder(player)[0];
//        if (facing == Direction.UP) facing = Direction.DOWN;
        var blockStorage = drain.getRolling().get(facing);
        try (var transaction = Transaction.openOuter()) {
            var livingStorage = PlayerInventoryStorage.of(player);
            long capacity = blockStorage.getCapacity();
            var tempStorage = new ConstantSingleItemStorage(capacity);
            long toLiving = StorageUtil.move(blockStorage, tempStorage, Lambdas.always(), capacity, transaction);
            StorageUtil.move(livingStorage.getHandSlot(hand), blockStorage, Lambdas.always(), capacity, transaction);
            if (StorageUtil.move(tempStorage, livingStorage, Lambdas.always(), capacity, transaction) == toLiving) {
                transaction.commit();
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.FAIL;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        var drain = world.getBlockEntity(pos, MyBlockEntityTypes.SMART_DRAIN).orElseThrow();
        if (state.get(Properties.LIT) && random.nextInt(24) == 0) {
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1, 1);
            world.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.2 + random.nextDouble() * 0.6, pos.getY() + 0.5, pos.getZ() + 0.2 + random.nextDouble() * 0.6, 0, 0, 0);
        }
        if (drain.getTank().getPrimaryHandler().getResource().isOf(Fluids.LAVA)) {
            ((LavaFluid) Fluids.LAVA).randomDisplayTick(world, pos, Fluids.LAVA.getDefaultState(), random);
        }
        var gb = drain.getBehaviour(GrillerBehaviour.TYPE);
        if (gb.getHeat() >= 1 && gb.getTicksS().get(0) > 0) {
            if (random.nextInt(3) == 0) {
                var p = Vec3d.of(pos).add(BlockingTransportedStorage.getHorizontalOffset(drain.getRolling().transp, 1));
                world.addParticle(ParticleTypesRegistry.STEAM.get(), p.getX(), pos.getY() + 13 / 16.0, p.getZ(), 0, 0.01, 0);
                world.addParticle(ParticleTypes.SMOKE, p.getX(), pos.getY() + 10 / 16.0, p.getZ(), 0, 0, 0);
                world.playSound(null, pos, SoundsRegistry.BLOCK_STOVE_CRACKLE.get(), SoundCategory.BLOCKS, 1, 1);
            }
        }
        var sb = drain.getBehaviour(SteamerBehaviour.TYPE);
        if (sb.getHeat() >= 1 && sb.getTicksS().get(0) > 0) {
            if (random.nextInt(3) == 0) {
                var p = Vec3d.of(pos).add(BlockingTransportedStorage.getHorizontalOffset(drain.getRolling().transp, 1));
                world.addParticle(ParticleTypesRegistry.STEAM.get(), p.getX(), pos.getY() + 13 / 16.0, p.getZ(), 0, 0.01, 0);
            }
        }
    }
}
