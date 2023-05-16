package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.content.contraptions.components.mixer.MechanicalMixerTileEntity;
import com.simibubi.create.content.contraptions.fluids.FluidFX;
import com.simibubi.create.content.contraptions.processing.BasinOperatingTileEntity;
import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Recipe;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.recipe.MincingRecipe;
import phoupraw.mcmod.createsdelight.registry.CDBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.CDRecipeTypes;

import java.util.Objects;
/**
 大部分都是从{@link PressureCookerBlockEntity}抄的。 */
public class MincerBlockEntity extends BasinOperatingTileEntity implements InstanceOffset {
    /**
     刀片伸出的程度，范围为[0,1]。当动力正转时增加，反转时减小。
     */
    private double extension;
    /**
     {@link #extension}的渲染配套。无论有无动力，都与{@link #extension}在1游戏刻前的值保持一致。
     */
    private double prevExtention;
    public double countdown = Integer.MIN_VALUE;

    public MincerBlockEntity(BlockPos pos, BlockState state) {
        this(CDBlockEntityTypes.MINCER, pos, state);
    }

    public MincerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    protected void write(NbtCompound compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putDouble("extension", getExtension());
        compound.putDouble("countdown", countdown);
    }

    @Override
    protected void read(NbtCompound compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        setExtension(compound.getDouble("extension"));
        countdown = compound.getDouble("countdown");
    }

    @Override
    protected boolean isRunning() {
        return false;
    }

    @Override
    protected void onBasinRemoved() {
        countdown = Integer.MIN_VALUE;
    }

    @Override
    public void startProcessingBasin() {
        super.startProcessingBasin();
        if (getCurrentRecipe() != null) {
            countdown = getCurrentRecipe().getProcessingDuration();
            sendData();
        }
    }

    @Override
    public boolean continueWithPreviousRecipe() {
        if (getCurrentRecipe() == null) return false;
        countdown = getCurrentRecipe().getProcessingDuration();
        return true;
    }

    @Override
    protected <C extends Inventory> boolean matchStaticFilters(Recipe<C> recipe) {
        return recipe.getType() == CDRecipeTypes.MINCING.getRecipeType();
    }

    @Override
    protected Object getRecipeCacheKey() {
        return MincingRecipe.class;
    }

    /**
     @see #extension
     */
    public double getExtension() {
        return extension;
    }

    /**
     @see #extension
     */
    public void setExtension(double extension) {
        if ((extension >= 0.5) ^ (getExtension() >= 0.5)) {
            getWorld().setBlockState(getPos(), getCachedState().with(Properties.EXTENDED, extension >= 0.5));
        }
        this.extension = MathHelper.clamp(extension, 0, 1);
    }

    /**
     @see #prevExtention
     */
    public double getPrevExtention() {
        return prevExtention;
    }

    /**
     @see #prevExtention
     */
    public void setPrevExtention(double prevExtention) {
        this.prevExtention = prevExtention;
    }

    @Override
    public void tick() {
        super.tick();
        setPrevExtention(getExtension());
        double step = (countdown >= -2 ? 1 : -1) * Math.abs(getSpeed()) / 2000;
        setExtension(getExtension() + step);
        if (countdown < 0) countdown--;
        if (isFullyExtended()) {
            var basin = getBasin().orElse(null);
            if (basin != null) {
                basin.setAreFluidsMoving(true);
                spillParticles(basin, getSpeed());
            }
            if (countdown > 0) {
                countdown -= Math.abs(getSpeed()) / 20;
                if (countdown < 0) countdown = 0;
            } else if (countdown == 0) {
                applyBasinRecipe();
                countdown = -1;
            }
        } else {
            var basin = getBasin().orElse(null);
            if (basin != null) {
                basin.setAreFluidsMoving(false);
            }
        }
    }

    public @Nullable ProcessingRecipe<?> getCurrentRecipe() {
        return (ProcessingRecipe<?>) currentRecipe;
    }

    public boolean isFullyExtended() {
        return getExtension() >= 1;
    }

    @NotNull
    @Override
    public World getWorld() {
        return Objects.requireNonNull(super.getWorld());
    }

    @Override
    public double getOffset(float partialTicks) {
        return -MathHelper.lerp(partialTicks, getPrevExtention(), getExtension());
    }

    /**
     copy of {@link MechanicalMixerTileEntity#spillParticle}
     */
    public static void spillParticle(World world, BlockPos pos, float speed, ParticleEffect data) {
        float angle = world.random.nextFloat() * 360;
        Vec3d offset = new Vec3d(0, 0, 0.25f);
        offset = VecHelper.rotate(offset, angle, Direction.Axis.Y);
        Vec3d target = VecHelper.rotate(offset, speed > 0 ? 25 : -25, Direction.Axis.Y)
          .add(0, .25f, 0);
        Vec3d center = offset.add(VecHelper.getCenterOf(pos));
        target = VecHelper.offsetRandomly(target.subtract(offset), world.random, 1 / 128f);
        world.addParticle(data, center.x, center.y + 0.25, center.z, target.x, target.y, target.z);
    }

    /**
     copy of {@link MechanicalMixerTileEntity#renderParticles()}
     */
    public static void spillParticles(BasinTileEntity basin, float speed) {
        World world = Objects.requireNonNull(basin.getWorld());
        for (SmartInventory inv : basin.getInvs()) {
            for (int slot = 0; slot < inv.getSlots(); slot++) {
                ItemStack stackInSlot = inv.getStack(slot);
                if (stackInSlot.isEmpty())
                    continue;
                ItemStackParticleEffect data = new ItemStackParticleEffect(ParticleTypes.ITEM, stackInSlot);

                spillParticle(world, basin.getPos(), speed, data);
            }
        }

        for (SmartFluidTankBehaviour behaviour : basin.getTanks()) {
            if (behaviour == null)
                continue;
            for (SmartFluidTankBehaviour.TankSegment tankSegment : behaviour.getTanks()) {
                if (tankSegment.isEmpty(0))
                    continue;
                spillParticle(world, basin.getPos(), speed, FluidFX.getFluidParticle(tankSegment.getRenderedFluid()));
            }
        }
    }
}
