package phoupraw.mcmod.createsdelight.block.entity;

import com.nhoryzon.mc.farmersdelight.registry.SoundsRegistry;
import com.simibubi.create.content.contraptions.particle.RotationIndicatorParticleData;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.DirectBeltInputBehaviour;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiCache;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.api.HeatSources;
import phoupraw.mcmod.createsdelight.api.Stage;
import phoupraw.mcmod.createsdelight.behaviour.BlockingTransportedBehaviour;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
public abstract class MyBlockEntity1 extends SmartTileEntity implements SidedStorageBlockEntity, Predicate<ProcessingRecipe<?>> {
    public static final int FLIPPING_DURATION = 10;
    private int processedTicks;
    //    public static void renderRotaryParticle() {
//
//    }
    private BlockApiCache<Double, Direction> heatCache;
    private int flippingTicks = -1;

    public MyBlockEntity1(BlockEntityType<?> type, BlockPos pos, BlockState state) {super(type, pos, state);}

    @NotNull
    @Override
    public World getWorld() {
        return Objects.requireNonNull(super.getWorld());
    }

    @MustBeInvokedByOverriders
    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
        var b = new BlockingTransportedBehaviour(this) {
            @Override
            public void onFinalCommit() {
                super.onFinalCommit();
                if (!getStorage().getStack().isEmpty()) {
                    getWorld().playSound(null, getPos(), SoundEvents.BLOCK_LANTERN_PLACE, SoundCategory.BLOCKS, 0.7F, 1.0F);
                }
            }
        };
        behaviours.add(b);
        behaviours.add(new DirectBeltInputBehaviour(this).setInsertionHandler(b.getStorage()));
    }

    @MustBeInvokedByOverriders
    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.putInt("processedTicks", getProcessedTicks());
        tag.putInt("flippingTicks", getFlippingTicks());
    }

    @MustBeInvokedByOverriders
    @Override
    protected void read(NbtCompound tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        setProcessedTicks(tag.getInt("processedTicks"));
        setFlippingTicks(tag.getInt("flippingTicks"));
    }

    @Override
    public @Nullable Storage<ItemVariant> getItemStorage(@Nullable Direction side) {
        return getItem().getStorage();
    }

    public int getProcessedTicks() {
        return processedTicks;
    }

    public void setProcessedTicks(int processedTicks) {
        var previous = this.processedTicks;
        this.processedTicks = processedTicks;
        if ((previous == 0) ^ (processedTicks == 0)) sendData();
    }

    public BlockingTransportedBehaviour getItem() {
        return getBehaviour(BlockingTransportedBehaviour.TYPE);
    }

    public boolean isProcessing() {
        return getProcessedTicks() > 0;
    }

    public @NotNull BlockApiCache<Double, Direction> getHeatCache() {
        if (heatCache == null) {
            if (getWorld() instanceof ServerWorld serverWorld) {
                heatCache = BlockApiCache.create(HeatSources.SIDED, serverWorld, pos.down());
            } else {
                throw new UnsupportedOperationException("cannot invoke this at client");
            }
        }
        return heatCache;
    }

    public double getHeat() {
        var heat = getHeatCache().find(Direction.UP);
        return heat == null ? 0 : heat;
    }

    public int getFlippingTicks() {
        return flippingTicks;
    }

    public void setFlippingTicks(int flippingTicks) {
        var previous = getFlippingStage();
        this.flippingTicks = flippingTicks;
        if (previous != Stage.DONE && getFlippingStage() == Stage.DONE) playSizzleSound();
    }

    public Stage getFlippingStage() {
        return getFlippingTicks() < 0 ? Stage.NOT_DOING : getFlippingTicks() < FLIPPING_DURATION ? Stage.DOING : Stage.DONE;
    }

    public void resetTicks() {
        setProcessedTicks(0);
        setFlippingTicks(-1);
    }

    public void playSizzleSound() {
        getWorld().playSound(null, getPos(), SoundsRegistry.BLOCK_SKILLET_ADD_FOOD.get(), SoundCategory.BLOCKS, 0.8F, 1.0F);
    }

    public abstract @Nullable ProcessingRecipe<?> getRecipe();

    @MustBeInvokedByOverriders
    @Override
    public void tick() {
        super.tick();
        if (getFlippingStage() == Stage.DOING) {
            setFlippingTicks(getFlippingTicks() + 1);
            var pos = Vec3d.ofCenter(getPos());
            var particle = new RotationIndicatorParticleData(MapColor.TERRACOTTA_YELLOW.color, 24f, 0.3f, 0.3f, 20, 'Y');
            getWorld().addParticle(particle, pos.getX(), pos.getY(), pos.getZ(), 0, 0, 0);
            if (getFlippingStage() == Stage.DONE) {
//                getItem().getStorage().getTransported().angle += 180;
            }
        }
    }
}
