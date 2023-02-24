package phoupraw.mcmod.createsdelight.block;

import com.nhoryzon.mc.farmersdelight.registry.ParticleTypesRegistry;
import com.nhoryzon.mc.farmersdelight.registry.SoundsRegistry;
import com.simibubi.create.foundation.block.ITE;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import phoupraw.mcmod.common.storage.LivingEntityStorage;
import phoupraw.mcmod.createsdelight.block.entity.GrillBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
import phoupraw.mcmod.createsdelight.storage.BlockingTransportedStorage;
import phoupraw.mcmod.createsdelight.storage.ConstantSingleItemStorage;

import static phoupraw.mcmod.common.misc.Lambdas.always;

public class GrillBlock extends Block implements ITE<GrillBlockEntity> {
    public static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 4, 16);

    public GrillBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Class<GrillBlockEntity> getTileEntityClass() {
        return GrillBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends GrillBlockEntity> getTileEntityType() {
        return MyBlockEntityTypes.GRILL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    //    @SuppressWarnings("deprecation")
//    @Override
//    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
//        if (world.isClient()) return ActionResult.CONSUME;
//        var grill = world.getBlockEntity(pos, MyBlockEntityTypes.GRILL).orElseThrow();
//        return  PanBlock.swapItem(player,hand,grill.getItem().getStorage())?ActionResult.SUCCESS:ActionResult.FAIL;
//    }
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos blockPos, Random random) {
        super.randomDisplayTick(state, world, blockPos, random);
        if (world.getBlockEntity(blockPos) instanceof GrillBlockEntity grill) {
            var nonEmpties = grill.getNonEmpties();
            for (int j = 0; j < nonEmpties.size(); j++) {
                int i = nonEmpties.get(j).getLeft();
                if (grill.processings[i] == 0) continue;
                Vec3d pos = Vec3d.of(blockPos).add(0.5 + (random.nextDouble() * 0.4 - 0.2), 4 / 16.0, 0.5 + (random.nextDouble() * 0.4 - 0.2)).add(GrillBlockEntity.getHorizontalOffset(j, nonEmpties.size()));
                world.addParticle(ParticleTypesRegistry.STEAM.get(), pos.getX(), pos.getY(), pos.getZ(), 0.0, random.nextBoolean() ? 0.015 : 0.005, 0.0);
                if (random.nextInt(5) == 0) {
                    world.addParticle(ParticleTypes.SMOKE, pos.getX(), pos.getY(), pos.getZ(), 0.0, random.nextBoolean() ? 0.015 : 0.005, 0.0);
                }
                world.playSound(null, blockPos, SoundsRegistry.BLOCK_SKILLET_SIZZLE.get(), SoundCategory.BLOCKS, 1, 1);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (world.getBlockEntity(pos) instanceof GrillBlockEntity grill) {
            DefaultedList<ItemStack> list = DefaultedList.of();
            for (Pair<Integer, BlockingTransportedStorage> p : grill.getNonEmpties()) {
                ItemStack stack = p.getRight().getStack();
                list.add(stack);
            }
            ItemScatterer.spawn(world, pos, list);
        }
        super.onStateReplaced(state, world, pos, newState, moved);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient()) return ActionResult.CONSUME;
        var blockStorage = world.getBlockEntity(pos, MyBlockEntityTypes.GRILL).orElseThrow().storage;
        long toBlock;
        try (var transaction = Transaction.openOuter()) {
            var livingStorage = LivingEntityStorage.of(player);
            long capacity = 4;
            var tempStorage = new ConstantSingleItemStorage(capacity);
            long toLiving = StorageUtil.move(blockStorage, tempStorage, always(), capacity, transaction);
            toBlock = StorageUtil.move(livingStorage.get(hand), blockStorage, always(), capacity, transaction);
            if (StorageUtil.move(tempStorage, livingStorage, always(), capacity, transaction) == toLiving && (toLiving > 0 || toBlock > 0)) {
                transaction.commit();
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.FAIL;
    }
}
