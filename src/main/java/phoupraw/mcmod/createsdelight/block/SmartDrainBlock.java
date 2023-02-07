package phoupraw.mcmod.createsdelight.block;

import com.nhoryzon.mc.farmersdelight.registry.SoundsRegistry;
import com.simibubi.create.AllShapes;
import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.api.Lambdas;
import phoupraw.mcmod.createsdelight.behaviour.BurnerBehaviour;
import phoupraw.mcmod.createsdelight.block.entity.SmartDrainBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
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
        ItemStack handStack = player.getStackInHand(hand);
        if (hit.getSide() == Direction.UP && (handStack.isOf(Items.FLINT_AND_STEEL) || handStack.isOf(Items.FIRE_CHARGE))) {
            var drain = world.getBlockEntity(pos, MyBlockEntityTypes.SMART_DRAIN).orElseThrow();
            if (drain.getBehaviour(BurnerBehaviour.TYPE).tryIgnite()>0) {
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
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        var drain = world.getBlockEntity(pos, MyBlockEntityTypes.SMART_DRAIN).orElseThrow();
        if (state.get(Properties.LIT) && random.nextInt(24) == 0) {
            world.playSound(null, pos, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1, 1);
            world.addParticle(ParticleTypes.LARGE_SMOKE, pos.getX() + 0.2 + random.nextDouble() * 0.6, pos.getY() + 0.5, pos.getZ() + 0.2 + random.nextDouble() * 0.6, 0, 0, 0);
        }
        if (drain.getBehaviour(SmartFluidTankBehaviour.TYPE).getPrimaryHandler().getResource().isOf(Fluids.LAVA)) {
            ((LavaFluid) Fluids.LAVA).randomDisplayTick(world, pos, Fluids.LAVA.getDefaultState(), random);
        }
//        if (drain.grillTicks > 0 && random.nextInt(5)==0) {
//            world.playSound(null, pos, SoundsRegistry.BLOCK_STOVE_CRACKLE.get(), SoundCategory.BLOCKS, 1, 1);
//        }
    }
}
