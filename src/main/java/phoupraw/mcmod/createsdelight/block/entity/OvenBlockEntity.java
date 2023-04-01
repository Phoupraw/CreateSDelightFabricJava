package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.utility.recipe.RecipeConditions;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.api.HeatSources;
import phoupraw.mcmod.createsdelight.api.LambdasC;
import phoupraw.mcmod.createsdelight.api.WorldContainerItemContext;
import phoupraw.mcmod.createsdelight.recipe.BakingRecipe;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.MyItems;
import phoupraw.mcmod.createsdelight.storage.IronBowlFluidStorage;
import phoupraw.mcmod.createsdelight.storage.IronBowlItemStorage;

import java.util.List;
public class OvenBlockEntity extends SmartTileEntity implements SidedStorageBlockEntity {
    private final SmartInventory inventory = new SmartInventory(4, this);
//    private SmartFluidTankBehaviour tank;

    public OvenBlockEntity(BlockPos pos, BlockState state) {
        this(MyBlockEntityTypes.OVEN, pos, state);
    }

    public OvenBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        getInventory().whenContentsChanged(() -> {

        });
    }

    @Override
    protected void read(NbtCompound tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        getInventory().deserializeNBT(tag.getCompound("items"));
    }

    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.put("items", getInventory().serializeNBT());
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
//        tank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.TYPE, this, 4, FluidConstants.BUCKET * 2, true);
//        behaviours.add(tank);
    }

    @Override
    public @NotNull Storage<ItemVariant> getItemStorage(@Nullable Direction side) {
        return getInventory();
    }

//    @Override
//    public @NotNull Storage<FluidVariant> getFluidStorage(@Nullable Direction side) {
//        return getTank().getCapability();
//    }

    @Override
    public void tick() {
        super.tick();
        Double heat = HeatSources.SIDED.find(getWorld(), getPos().down(), Direction.UP);
        if (heat != null && heat > 1) {
            for (int i = 0; i < getInventory().size(); i++) {
                ItemStack itemStack = getInventory().getStack(i);
                if (itemStack.isOf(MyItems.IRON_BOWL)) {
                    ContainerItemContext itemContext = WorldContainerItemContext.of(itemStack);
                    var itemS = new IronBowlItemStorage(itemContext, getWorld());
                    var fluidS = new IronBowlFluidStorage(itemContext, getWorld());
                    NbtCompound nbt = itemStack.getOrCreateNbt();
                    BakingRecipe recipe = null;
                    if (!itemS.isResourceBlank()) {
                        recipe = BakingRecipe.findFirst(getWorld(), RecipeConditions.firstIngredientMatches(itemS.getResource().toStack()));
                    } else if (!fluidS.isResourceBlank()) {
                        recipe = BakingRecipe.findFirst(getWorld(), LambdasC.firstIngredientMatching(fluidS.getResource().getFluid()));
                    }
                    if (recipe == null) {
                        nbt.remove("countdown");
                    } else {
                        double countdown;
                        if (!nbt.contains("countdown")) {
                            countdown = recipe.getProcessingDuration();
                        } else {
                            countdown = nbt.getDouble("countdown") - 1;
                        }
                        if (countdown > 0) {
                            nbt.putDouble("countdown", countdown);
                        } else {
                            nbt.remove("countdown");
                            TransferUtil.clearStorage(itemS);
                            TransferUtil.clearStorage(fluidS);
                            itemS.setAmnesty(true);
                            TransferUtil.insertItem(itemS, recipe.getOutput());
                            itemS.setAmnesty(false);
                        }
                    }
                }
            }
        }
    }

//    public SmartFluidTankBehaviour getTank() {
//        return tank;
//    }

    public SmartInventory getInventory() {
        return inventory;
    }

    @SuppressWarnings("ConstantConditions")
    @NotNull
    @Override
    public World getWorld() {
        return super.getWorld();
    }
}
