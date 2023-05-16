package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.utility.recipe.RecipeConditions;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.api.HeatSources;
import phoupraw.mcmod.createsdelight.api.LambdasC;
import phoupraw.mcmod.createsdelight.recipe.BakingRecipe;
import phoupraw.mcmod.createsdelight.registry.CDBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.CDItems;
import phoupraw.mcmod.createsdelight.storage.IronBowlFluidStorage;
import phoupraw.mcmod.createsdelight.storage.IronBowlItemStorage;

import java.util.Arrays;
import java.util.List;
public class OvenBlockEntity extends SmartTileEntity implements SidedStorageBlockEntity {
    public static final int SIZE = 4;
    private final SmartInventory inventory = new SmartInventory(SIZE, this);
    private final double[] countdowns = new double[SIZE];

    public OvenBlockEntity(BlockPos pos, BlockState state) {
        this(CDBlockEntityTypes.OVEN, pos, state);
    }

    public OvenBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        getInventory().whenContentsChanged(() -> {

        });
        Arrays.fill(getCountdowns(), -1);
    }

    @Override
    protected void read(NbtCompound tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        getInventory().deserializeNBT(tag.getCompound("items"));
        NbtList list = tag.getList("countdowns", NbtElement.DOUBLE_TYPE);
        for (int i = 0; i < SIZE; i++) {
            getCountdowns()[i] = list.getDouble(i);
        }
    }

    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.put("items", getInventory().serializeNBT());
        NbtList nbtList = new NbtList();
        for (double v : getCountdowns()) {
            nbtList.add(NbtDouble.of(v));
        }
        tag.put("countdowns", nbtList);
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {

    }

    @Override
    public @NotNull Storage<ItemVariant> getItemStorage(@Nullable Direction side) {
        return getInventory();
    }

    @Override
    public void tick() {
        super.tick();
        Double heat = HeatSources.SIDED.find(getWorld(), getPos().down(), Direction.UP);
        if (heat != null && heat > 1) {
            double step = heat - 1;
            for (int i = 0; i < SIZE; i++) {
                ItemStack itemStack = getInventory().getStack(i);
                if (itemStack.isOf(CDItems.IRON_BOWL)) {
                    ContainerItemContext itemContext = ContainerItemContext.ofSingleSlot(InventoryStorage.of(getInventory(), null).getSlot(i));
                    var itemS = new IronBowlItemStorage(itemContext, getWorld());
                    var fluidS = new IronBowlFluidStorage(itemContext, getWorld());
                    BakingRecipe recipe = null;
                    if (!itemS.isResourceBlank()) {
                        recipe = BakingRecipe.findFirst(getWorld(), RecipeConditions.firstIngredientMatches(itemS.getResource().toStack()));
                    } else if (!fluidS.isResourceBlank()) {
                        recipe = BakingRecipe.findFirst(getWorld(), LambdasC.firstIngredientMatching(fluidS.getResource().getFluid(), fluidS.getAmount()));
                    }
                    if (recipe == null) {
                        getCountdowns()[i] = -1;
                    } else {
                        double countdown;
                        if (getCountdowns()[i] < 0) {
                            countdown = recipe.getProcessingDuration();
                        } else {
                            countdown = getCountdowns()[i] - step;
                        }
                        if (countdown > 0) {
                            getCountdowns()[i] = countdown;
                        } else {
                            try (var transa = TransferUtil.getTransaction()) {
                                if (!recipe.getIngredients().isEmpty()) {
                                    itemS.extract(itemS.getResource(), 1, transa);
                                } else {
                                    fluidS.extract(fluidS.getResource(), fluidS.getAmount(), transa);
                                }
                                itemS.setAmnesty(true);
                                long inserted = itemS.insert(ItemVariant.of(recipe.getOutput()), 1, transa);
                                itemS.setAmnesty(false);
                                if (inserted == 1) {
                                    transa.commit();
                                    getCountdowns()[i] = -1;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public SmartInventory getInventory() {
        return inventory;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public World getWorld() {
        return super.getWorld();
    }

    @Override
    public void destroy() {
        super.destroy();
        ItemScatterer.spawn(getWorld(), getPos(), getInventory());
    }

    @Override
    public void markDirty() {
        super.markDirty();
    }

    public double[] getCountdowns() {
        return countdowns;
    }
}
