package phoupraw.mcmod.createsdelight.block.entity;

import com.nhoryzon.mc.farmersdelight.registry.ParticleTypesRegistry;
import com.simibubi.create.content.contraptions.processing.BasinOperatingTileEntity;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.nbt.NbtCompound;
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
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;

import java.util.Objects;
/**
 * 大部分都是从{@link PressureCookerBlockEntity}抄的。
 */
public class MincerBlockEntity extends BasinOperatingTileEntity implements InstanceOffset {
    /**
     * 刀片伸出的程度，范围为[0,1]。当动力正转时增加，反转时减小。
     */
    private double extension;
    /**
     * {@link #extension}的渲染配套。无论有无动力，都与{@link #extension}在1游戏刻前的值保持一致。
     */
    private double prevExtention;
    public double countdown;

    public MincerBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.MINCER, pos, state);}

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
        countdown = -1;
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
        return recipe.getType() == MyRecipeTypes.MINCING.getRecipeType();
    }

    @Override
    protected Object getRecipeCacheKey() {
        return MincingRecipe.class;
    }

    /**
     * @see #extension
     */
    public double getExtension() {
        return extension;
    }

    /**
     * @see #extension
     */
    public void setExtension(double extension) {
        this.extension = MathHelper.clamp(extension, 0, 1);
    }

    /**
     * @see #prevExtention
     */
    public double getPrevExtention() {
        return prevExtention;
    }

    /**
     * @see #prevExtention
     */
    public void setPrevExtention(double prevExtention) {
        this.prevExtention = prevExtention;
    }

    @Override
    public void tick() {
        super.tick();
        setPrevExtention(getExtension());
        double step = getSpeed() / 1000;
        setExtension(getExtension() + step);
        if (isFullyExtended()) {
            if (countdown > 0) {
                countdown -= getSpeed() / 64;
                if (countdown < 0) countdown = 0;
                if (getWorld().getTime() % 3 == 0) {
                    Vec3d p = Vec3d.ofCenter(getPos());
                    if (getCachedState().get(Properties.HORIZONTAL_AXIS) == Direction.Axis.Z) {
                        p = p.add(-3 / 16.0, -20 / 16.0, -3 / 16.0);
                    } else {
                        p = p.add(-3 / 16.0, -20 / 16.0, 3 / 16.0);
                    }
                    getWorld().addParticle(ParticleTypesRegistry.STEAM.get(), p.getX(), p.getY(), p.getZ(), 0, 0.01 + getWorld().getRandom().nextDouble() * 0.02, 0);
                }
            } else if (countdown > -1) {
                applyBasinRecipe();
                countdown = -1;
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

}
