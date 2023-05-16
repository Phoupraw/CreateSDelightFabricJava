package phoupraw.mcmod.createsdelight.behaviour;

import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Direction;
import phoupraw.mcmod.common.api.Collections3;
import phoupraw.mcmod.createsdelight.api.HeatSources;
import phoupraw.mcmod.createsdelight.recipe.PanFryingRecipe;
import phoupraw.mcmod.createsdelight.registry.CDRecipeTypes;

import java.util.Objects;
public class PanFrierBehaviour extends TileEntityBehaviour {
    public static final BehaviourType<PanFrierBehaviour> TYPE = new BehaviourType<>("pan_frier");
    private boolean working;
    private int ticks;

    public PanFrierBehaviour(SmartTileEntity te) {
        super(te);
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void write(NbtCompound nbt, boolean clientPacket) {
        super.write(nbt, clientPacket);
        nbt.putInt("ticks", getTicks());
    }

    @Override
    public void read(NbtCompound nbt, boolean clientPacket) {
        super.read(nbt, clientPacket);
        setTicks(nbt.getInt("ticks"));
    }

    @Override
    public void tick() {
        super.tick();
        if (!getWorld().isClient()) {
            if (getHeat() >= 1) {
                var recipes = Collections3.top(getWorld().getRecipeManager().listAllOfType(CDRecipeTypes.PAN_FRYING.getRecipeType()), PanFryingRecipe.testing(getItemS(), getFluidS()), 1);
                if (recipes.isEmpty()) {
                    setWorking(false);
                    setTicks(-1);
                } else {
                    setWorking(true);
                    var recipe = recipes.iterator().next();
                    if (getTicks() == -1) {
                        setTicks(recipe.getProcessingDuration() - 1);
                    } else if (getTicks() == 0) {
                        if (recipe.replace(getItemS(), getFluidS(), null)) {
                            setTicks(-1);
                        }
                    } else {
                        setTicks(getTicks() - 1);
                    }
                }
            } else {
                setWorking(false);
            }
        }
    }

    public double getHeat() {
        return Objects.requireNonNullElse(HeatSources.SIDED.find(getWorld(), getPos().down(), Direction.UP), 0.0);
    }

    public Storage<ItemVariant> getItemS() {
        return Objects.requireNonNullElse(ItemStorage.SIDED.find(getWorld(), getPos(), tileEntity.getCachedState(), tileEntity, null), Storage.empty());
    }

    public Storage<FluidVariant> getFluidS() {
        return Objects.requireNonNullElse(FluidStorage.SIDED.find(getWorld(), getPos(), tileEntity.getCachedState(), tileEntity, null), Storage.empty());
    }

    public boolean isWorking() {
        return working;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }

    public int getTicks() {
        return ticks;
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }
}
