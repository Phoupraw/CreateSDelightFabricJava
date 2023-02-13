package phoupraw.mcmod.createsdelight.behaviour;

import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.utility.recipe.RecipeConditions;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.api.HeatSources;
import phoupraw.mcmod.createsdelight.recipe.SteamingRecipe;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;
import phoupraw.mcmod.createsdelight.storage.ReplaceableStorageView;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
/**
 * 从{@link GrillerBehaviour}抄了很多。
 */
public class SteamerBehaviour extends TileEntityBehaviour {
    public static final BehaviourType<SteamerBehaviour> TYPE = new BehaviourType<>("steamer");
    public @Nullable Storage<ItemVariant> itemS;
    public @Nullable Storage<FluidVariant> fluidS;
    private List<@NotNull Integer> ticksS;
    private @Nullable Integer size = 9;
    private double heat;

    public SteamerBehaviour(SmartTileEntity te) {
        super(te);
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    @Override
    public void write(NbtCompound nbt, boolean clientPacket) {
        super.write(nbt, clientPacket);
        nbt.put("ticksS", new NbtIntArray(getTicksS()));
        if (clientPacket) {
            nbt.putInt("size", getSize());
            nbt.putDouble("heat", getHeat());
        }
    }

    @Override
    public void read(NbtCompound nbt, boolean clientPacket) {
        super.read(nbt, clientPacket);
        var ticksS = getTicksS();
        ticksS.clear();
        var array = nbt.getIntArray("ticksS");
        for (int ticks : array) {
            ticksS.add(ticks);
        }
        if (clientPacket) {
            setSize(nbt.getInt("size"));
            setHeat(nbt.getInt("heat"));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!isSteamable()) return;
        List<Integer> ticksS = getTicksS();
        double heat = getHeat();
        if (heat >= 1) {
            for (var ite = ticksS.listIterator(); ite.hasNext(); ) {
                int ticks = ite.next();
                if (ticks > 0) ite.set(ticks - 1);
            }
        }
        if (getWorld().isClient()) return;
        var fluidS = getFluidS();
        if (fluidS == null) return;
        boolean water = false;
        for (StorageView<FluidVariant> view : fluidS) {
            if (view.getResource().isOf(Fluids.WATER)) {
                try (var transa = Transaction.openOuter()) {
                    long amount = view.extract(view.getResource(), 1, transa);
                    if (amount > 0) {
                        water = true;
                        if (heat >= 1) {
                            transa.commit();
                        }
                        break;
                    }
                }
            }
        }
        if (!water) return;
        BitSet bitSet = new BitSet(getSize());
        var itemS = getItemS();
        if (itemS != null) {
            int i = 0;
            for (StorageView<ItemVariant> view : itemS) {
                var recipe = getRecipe(view.getResource().toStack());
                if (recipe != null) {
                    bitSet.set(i);
                    if (ticksS.get(i) == -1) {
                        ticksS.set(i, (int) (recipe.getProcessingDuration() * Math.sqrt(view.getAmount())));
                        tileEntity.sendData();
                    } else if (ticksS.get(i) == 0) {
                        if (onDone(view, i, recipe)) {
                            ticksS.set(i, -1);
                            tileEntity.sendData();
                        }
                    }
                }
                i++;
            }
        }
        for (int i = 0; i < getSize(); i++) {
            if (!bitSet.get(i)) ticksS.set(i, -1);
        }
    }

    public double getHeat() {
        double previous = this.heat;
        if (getWorld().isClient()) return previous;
        try {
            var world = (ServerWorld) getWorld();
            var heat = HeatSources.CACHE.get(world).find(getPos(), tileEntity.getCachedState(), tileEntity, Direction.UP);
            if (heat != null) {
                this.heat = heat;
            } else {
                heat = HeatSources.CACHE.get(world).find(getPos(), tileEntity.getCachedState(), tileEntity, null);
                if (heat != null) {
                    this.heat = heat;
                } else {
                    heat = HeatSources.CACHE.get(world).find(getPos().down(), Direction.UP);
                    this.heat = Objects.requireNonNullElse(heat, 0.0);
                }
            }
            if ((previous >= 1) ^ (this.heat >= 1)) tileEntity.sendData();
            return this.heat;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see GrillerBehaviour#getStorage()
     */
    public @Nullable Storage<ItemVariant> getItemS() {
        if (itemS == null) itemS = ItemStorage.SIDED.find(getWorld(), getPos(), tileEntity.getCachedState(), tileEntity, Direction.UP);
        if (itemS == null) itemS = ItemStorage.SIDED.find(getWorld(), getPos(), tileEntity.getCachedState(), tileEntity, null);
        return itemS;
    }

    public @Nullable Storage<FluidVariant> getFluidS() {
        if (fluidS == null) fluidS = FluidStorage.SIDED.find(getWorld(), getPos(), tileEntity.getCachedState(), tileEntity, Direction.UP);
        if (fluidS == null) fluidS = FluidStorage.SIDED.find(getWorld(), getPos(), tileEntity.getCachedState(), tileEntity, null);
        return fluidS;
    }

    /**
     * @see GrillerBehaviour#getTicksS()
     */
    public List<@NotNull Integer> getTicksS() {
        if (ticksS == null) setTicksS(new ArrayList<>());
        while (ticksS.size() < getSize()) ticksS.add(-1);
        return ticksS;
    }

    public void setTicksS(List<@NotNull Integer> ticksS) {
        this.ticksS = ticksS;
    }

    public int getSize() {
        if (size == null) {
            size = 0;
            if (!getWorld().isClient()) {
                var storage = getItemS();
                if (storage != null) {
                    for (var ignored : storage) size++;
                }
            }
            tileEntity.sendData();
        }
        return size;
    }

    public void setSize(@Nullable Integer size) {
        this.size = size;
    }

    public void setHeat(double heat) {
        this.heat = heat;
    }

    public @Nullable SteamingRecipe getRecipe(ItemStack ingredient) {
        return getWorld().getRecipeManager().listAllOfType(MyRecipeTypes.STEAMING.getRecipeType()).parallelStream().filter(RecipeConditions.firstIngredientMatches(ingredient)).findFirst().orElse(null);
    }

    public boolean onDone(StorageView<ItemVariant> view, int i, SteamingRecipe recipe) {
        try (var transa = Transaction.openOuter()) {
            boolean b = false;
            ItemVariant output = ItemVariant.of(recipe.getOutput());
            if (view instanceof ReplaceableStorageView<ItemVariant> rep) {
                if (rep.replace(output, view.getAmount(), transa)) {
                    b = true;
                }
            }
            if (!b) {
                //noinspection ConstantConditions
                if (view.extract(view.getResource(), view.getAmount(), transa) == getItemS().insert(output, view.getAmount(), transa)) {
                    b = true;
                }
            }
            if (b) {
                transa.commit();
                return true;
            }
        }
        return false;
    }

    public boolean isSteamable() {
        return true;
    }
}
