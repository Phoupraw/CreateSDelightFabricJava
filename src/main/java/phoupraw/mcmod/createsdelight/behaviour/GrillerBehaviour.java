package phoupraw.mcmod.createsdelight.behaviour;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.recipe.RecipeConditions;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.api.HeatSources;
import phoupraw.mcmod.createsdelight.recipe.GrillingRecipe;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;
import phoupraw.mcmod.createsdelight.storage.ReplaceableStorageView;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
public class GrillerBehaviour extends BlockEntityBehaviour {
    public static final BehaviourType<GrillerBehaviour> TYPE = new BehaviourType<>("griller");
    private @Nullable Storage<ItemVariant> storage;
    private List<@NotNull Integer> ticksS;
    private @Nullable Integer size = 9;
    private double heat;

    public GrillerBehaviour(SmartBlockEntity te) {
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
        List<Integer> ticksS = getTicksS();
        if (getHeat() >= 1) {
            for (var IBE = ticksS.listIterator(); IBE.hasNext(); ) {
                int ticks = IBE.next();
                if (ticks > 0) IBE.set(ticks - 1);
            }
        }
        if (getWorld().isClient()) return;
        BitSet bitSet = new BitSet(getSize());
        var storage = getStorage();
        if (storage != null) {
            int i = 0;
            for (StorageView<ItemVariant> view : storage) {
                GrillingRecipe recipe = getRecipe(view.getResource().toStack());
                if (recipe != null) {
                    bitSet.set(i);
                    if (ticksS.get(i) == -1) {
                        ticksS.set(i, (int) (recipe.getProcessingDuration() * Math.sqrt(view.getAmount())));
                        blockEntity.sendData();
                    } else if (ticksS.get(i) == 0) {
                        if (onDone(view, i, recipe)) {
                            ticksS.set(i, -1);
                            blockEntity.sendData();
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
            var heat = HeatSources.CACHE.get(world).find(getPos(), blockEntity.getCachedState(), blockEntity, Direction.UP);
            if (heat != null) {
                this.heat = heat;
            } else {
                heat = HeatSources.CACHE.get(world).find(getPos(), blockEntity.getCachedState(), blockEntity, null);
                if (heat != null) {
                    this.heat = heat;
                } else {
                    heat = HeatSources.CACHE.get(world).find(getPos().down(), Direction.UP);
                    this.heat = Objects.requireNonNullElse(heat, 0.0);
                }
            }
            if ((previous >= 1) ^ (this.heat >= 1)) blockEntity.sendData();
            return this.heat;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public @Nullable Storage<ItemVariant> getStorage() {
        if (storage == null) storage = ItemStorage.SIDED.find(getWorld(), getPos(), blockEntity.getCachedState(), blockEntity, Direction.UP);
        if (storage == null) storage = ItemStorage.SIDED.find(getWorld(), getPos(), blockEntity.getCachedState(), blockEntity, null);
        return storage;
    }

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
                var storage = getStorage();
                if (storage != null) {
                    for (var ignored : storage) size++;
                }
            }
            blockEntity.sendData();
        }
        return size;
    }

    public void setSize(@Nullable Integer size) {
        this.size = size;
    }

    public void setHeat(double heat) {
        this.heat = heat;
    }

    public @Nullable GrillingRecipe getRecipe(ItemStack ingredient) {
        return getWorld().getRecipeManager().listAllOfType(MyRecipeTypes.GRILLING.getRecipeType()).parallelStream().filter(RecipeConditions.firstIngredientMatches(ingredient)).findFirst().orElse(null);
    }

    public boolean onDone(StorageView<ItemVariant> view, int i, GrillingRecipe recipe) {
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
                if (view.extract(view.getResource(), view.getAmount(), transa) == getStorage().insert(output, view.getAmount(), transa)) {
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

}
