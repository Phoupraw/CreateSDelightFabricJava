package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.MyRecipeTypes;

import java.util.List;

public class PanBlockEntity extends MyBlockEntity1 implements IHaveGoggleInformation, Runnable {

    public PanBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.PAN, pos, state);}

    public PanBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        resetTicks();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        behaviours.add(new SmartFluidTankBehaviour(SmartFluidTankBehaviour.TYPE, this, 1, FluidConstants.BOTTLE, false).whenFluidUpdates(this));
    }

    @Override
    public void tick() {
        super.tick();

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
                    case NOT_DOING -> {
                        setFlippingTicks(0);
                        sendData();
                    }
                    case DOING -> {}
                    case DONE -> {
                        if (getProcessedTicks() >= recipe.getProcessingDuration()) {
                            resetTicks();
                            getItem().getStorage().setTransported(TransportedItemStack.EMPTY);
                            if (!recipe.getFluidIngredients().isEmpty()) {
                                try (var transaction = Transaction.openOuter()) {
                                    getTank().getCapability().extract(getTank().getPrimaryHandler().getResource(), recipe.getFluidIngredients().get(0).getRequiredAmount(), transaction);
                                    transaction.commit();
                                }
                            }
                            var result = recipe.rollResults().get(0);
                            var itemEntity = new ItemEntity(getWorld(), getPos().getX()+0.5, getPos().getY()+1/16.0, getPos().getZ()+0.5, result, 0, 0.3, 0);
                            getWorld().spawnEntity(itemEntity);
                            notifyUpdate();
                        }
                    }
                }
            }
        }
    }

    @Override
    public @NotNull Storage<FluidVariant> getFluidStorage(@Nullable Direction side) {
        return getTank().getCapability();
    }

    @Override
    public boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(tooltip, isPlayerSneaking, getTank().getCapability());
    }

    /**
     * @see SmartFluidTankBehaviour#whenFluidUpdates(Runnable)
     * @see #addBehaviours(List)
     */
    @Override
    public void run() {
    }

    public @NotNull SmartFluidTankBehaviour getTank() {
        return getBehaviour(SmartFluidTankBehaviour.TYPE);
    }

    @Override
    public @Nullable ProcessingRecipe<?> getRecipe() {
        return getWorld().getRecipeManager().listAllOfType(MyRecipeTypes.PAN_FRYING.getRecipeType()).parallelStream().filter(this).findFirst().orElse(null);
    }

    @Override
    public boolean test(ProcessingRecipe<?> r) {
        DefaultedList<Ingredient> ingredients = r.getIngredients();
        DefaultedList<FluidIngredient> fluidIngredients = r.getFluidIngredients();
        return (ingredients.isEmpty() || ingredients.get(0).test(getItem().getStorage().getStack())) && (fluidIngredients.isEmpty() || fluidIngredients.get(0).test(getTank().getPrimaryHandler().getFluid()));
    }
}
