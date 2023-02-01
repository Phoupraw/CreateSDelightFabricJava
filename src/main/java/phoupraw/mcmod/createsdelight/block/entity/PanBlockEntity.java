package phoupraw.mcmod.createsdelight.block.entity;

import com.nhoryzon.mc.farmersdelight.registry.SoundsRegistry;
import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.contraptions.particle.RotationIndicatorParticleData;
import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;

import com.simibubi.create.foundation.tileEntity.behaviour.belt.DirectBeltInputBehaviour;

import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;

import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiCache;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

import net.minecraft.util.math.Direction;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import phoupraw.mcmod.createsdelight.api.HeatSources;
import phoupraw.mcmod.createsdelight.behaviour.BlockingTransportedBehaviour;
import phoupraw.mcmod.createsdelight.recipe.PanFryingRecipe;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class PanBlockEntity extends SmartTileEntity implements SidedStorageBlockEntity, IHaveGoggleInformation, Predicate<PanFryingRecipe>, Runnable {
    public static final int FLIPPING_DURATION = 10;
    //    public static void renderRotaryParticle() {
//
//    }
    private BlockApiCache<Double, Direction> heatCache;
    private int processedTicks;
    private int flippingTicks = -1;

    public PanBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.PAN, pos, state);}

    public PanBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        resetTicks();
    }

    @NotNull
    @Override
    public World getWorld() {
        return Objects.requireNonNull(super.getWorld());
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
        behaviours.add(new SmartFluidTankBehaviour(SmartFluidTankBehaviour.TYPE, this, 1, FluidConstants.BOTTLE, false).whenFluidUpdates(this));
        var b1 = new BlockingTransportedBehaviour(this) {
            @Override
            public void onFinalCommit() {
                super.onFinalCommit();
                if (!getStorage().getStack().isEmpty()) {
                    getWorld().playSound(null, getPos(), SoundEvents.BLOCK_LANTERN_PLACE, SoundCategory.BLOCKS, 0.7F, 1.0F);
                }
            }
        };
        behaviours.add(b1);
        behaviours.add(new DirectBeltInputBehaviour(this).setInsertionHandler(b1.getStorage()));
//        behaviours.add(new PanBehaviour(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (getFlippingStage() == Stage.DOING) {
            setFlippingTicks(getFlippingTicks() + 1);
            var pos = Vec3d.ofCenter(getPos());
            var particle = new RotationIndicatorParticleData(MapColor.TERRACOTTA_YELLOW.color, 16f, 0.3f, 0.3f, 20, 'Y');
            getWorld().addParticle(particle, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
            if (getFlippingStage() == Stage.DONE) {
                getTransportedBehaviour().getStorage().getTransported().angle += 180;
            }
        }
        if (!getWorld().isClient()) {
            if (getHeat() < 1) return;
            var recipe = getRecipe();
            if (recipe == null) {
                resetTicks();
                return;
            }
            setProcessedTicks(getProcessedTicks() + 1);
            if (getProcessedTicks() == 1) {
                playSizzleSound();
            }
            if (getProcessedTicks() >= recipe.getProcessingDuration() / 2) {
                switch (getFlippingStage()) {
                    case NOT_DONE -> {
                        setFlippingTicks(0);
                        sendData();
                    }
                    case DOING -> {}
                    case DONE -> {
                        if (getProcessedTicks() >= recipe.getProcessingDuration()) {
                            resetTicks();
                            getTransportedBehaviour().getStorage().setTransported(TransportedItemStack.EMPTY);
                            if (!recipe.getFluidIngredients().isEmpty()) {
                                try (var transaction = Transaction.openOuter()) {
                                    getTankBehaviour().getCapability().extract(getTankBehaviour().getPrimaryTank().getRenderedFluid().getType(), recipe.getFluidIngredients().get(0).getRequiredAmount(), transaction);
                                    transaction.commit();
                                }
                            }
                            var result = recipe.rollResults().get(0);
                            var pos = Vec3d.ofCenter(getPos());
                            var itemEntity = new ItemEntity(getWorld(), pos.getX(), pos.getY(), pos.getZ(), result, 0, 0.3, 0);
                            getWorld().spawnEntity(itemEntity);
                            notifyUpdate();
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.putInt("processedTicks", getProcessedTicks());
        tag.putInt("flippingTicks", getFlippingTicks());
    }

    @Override
    protected void read(NbtCompound tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        setProcessedTicks(tag.getInt("processedTicks"));
        setFlippingTicks(tag.getInt("flippingTicks"));
    }

    @Override
    public @Nullable Storage<ItemVariant> getItemStorage(@Nullable Direction side) {
        return getTransportedBehaviour().getStorage();
    }

    @Override
    public @NotNull Storage<FluidVariant> getFluidStorage(@Nullable Direction side) {
        return getTankBehaviour().getCapability();
    }

    @Override
    public boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(tooltip, isPlayerSneaking, getTankBehaviour().getCapability());
    }

    /**
     * @param r the input argument
     * @return
     * @see #addBehaviours(List)
     * @see PanFryingRecipe#test(PanBlockEntity)
     */
    @Override
    public boolean test(PanFryingRecipe r) {
        return r.test(this);
    }

    /**
     * @see SmartFluidTankBehaviour#whenFluidUpdates(Runnable)
     * @see #addBehaviours(List)
     */
    @Override
    public void run() {
    }

    public BlockApiCache<Double, Direction> getHeatCache() {
        if (heatCache == null) {
            if (getWorld() instanceof ServerWorld serverWorld) {
                heatCache = BlockApiCache.create(HeatSources.SIDED, serverWorld, pos.down());
            } else {throw new UnsupportedOperationException("cannot invoke this at client");}
        }
        return heatCache;
    }

    public int getProcessedTicks() {
        return processedTicks;
    }

    public void setProcessedTicks(int processedTicks) {
        var previous = this.processedTicks;
        this.processedTicks = processedTicks;
        if ((previous == 0) ^ (processedTicks == 0)) sendData();
    }

    public int getFlippingTicks() {
        return flippingTicks;
    }

    public void setFlippingTicks(int flippingTicks) {
        var previous = getFlippingStage();
        this.flippingTicks = flippingTicks;
        if (previous != Stage.DONE && getFlippingStage() == Stage.DONE) playSizzleSound();
    }

    public @NotNull SmartFluidTankBehaviour getTankBehaviour() {
        return getBehaviour(SmartFluidTankBehaviour.TYPE);
    }

    public BlockingTransportedBehaviour getTransportedBehaviour() {
        return getBehaviour(BlockingTransportedBehaviour.TYPE);
    }

    public @Nullable PanFryingRecipe getRecipe() {
        return getWorld().getRecipeManager().listAllOfType(MyRecipeTypes.PAN_FRYING.getRecipeType()).parallelStream().filter(this).findFirst().orElse(null);
    }

    public double getHeat() {
        var heat = getHeatCache().find(Direction.UP);
        return heat == null ? 0 : heat;
    }

    public boolean isProcessing() {
        return getProcessedTicks() > 0;
    }

    public Stage getFlippingStage() {
        return getFlippingTicks() < 0 ? Stage.NOT_DONE : getFlippingTicks() < FLIPPING_DURATION ? Stage.DOING : Stage.DONE;
    }

    public void resetTicks() {
        setProcessedTicks(0);
        setFlippingTicks(-1);
    }

    public void playSizzleSound() {
        getWorld().playSound(null, getPos(), SoundsRegistry.BLOCK_SKILLET_ADD_FOOD.get(), SoundCategory.BLOCKS, 0.8F, 1.0F);
    }

    public enum Stage {
        NOT_DONE, DOING, DONE
    }
}
