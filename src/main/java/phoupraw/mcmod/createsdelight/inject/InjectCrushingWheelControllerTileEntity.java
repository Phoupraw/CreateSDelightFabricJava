package phoupraw.mcmod.createsdelight.inject;

import com.google.common.base.Predicates;
import com.simibubi.create.content.contraptions.components.crusher.CrushingWheelControllerTileEntity;
import com.simibubi.create.content.contraptions.fluids.actors.ItemDrainTileEntity;
import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
public interface InjectCrushingWheelControllerTileEntity {
    static void addBehaviours(Object subject, List<TileEntityBehaviour> behaviours, CallbackInfo ci) {
        var controller = (CrushingWheelControllerTileEntity & InjectCrushingWheelControllerTileEntity) subject;
        var tank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.OUTPUT, controller, 1, FluidConstants.BUCKET * 64, false);
        behaviours.add(tank);
        controller.setTank(tank);
    }
    static void checkTankEmpty(Object subject, CallbackInfo ci) {
        var controller = (CrushingWheelControllerTileEntity & InjectCrushingWheelControllerTileEntity) subject;
        if (controller.getTank().getPrimaryTank().getFluidLevel().getValue() > 0.1) {
            ci.cancel();
        }
    }
    static void applyFluidResult(Object subject, ProcessingRecipe<Inventory> recipe) {
        var controller = (CrushingWheelControllerTileEntity & InjectCrushingWheelControllerTileEntity) subject;
        DefaultedList<FluidStack> fluidResults = recipe.getFluidResults();
        if (fluidResults.isEmpty()) return;
        TransferUtil.insert(controller.getTank().getCapability(), fluidResults.get(0).getType(), fluidResults.get(0).getAmount() * controller.inventory.getStack(0).getCount());
    }
    static void setBottom(Object subject) {
        var controller = (CrushingWheelControllerTileEntity & InjectCrushingWheelControllerTileEntity) subject;
        SmartFluidTankBehaviour tank = controller.getTank();
        if (tank.isEmpty()) return;
        double offset = -0.5;
        @SuppressWarnings("ConstantConditions") var blockEntity = controller.getWorld().getBlockEntity(controller.getPos().down());
        if (blockEntity == null) {
            blockEntity = controller.getWorld().getBlockEntity(controller.getPos().down(2));
            offset = -1.5;
        }
        if (blockEntity instanceof BasinTileEntity) {
            controller.setBottom(offset - 14 / 16.0);
        } else if (blockEntity instanceof ItemDrainTileEntity) {
            controller.setBottom(offset - 11 / 16.0);
        } else {
            controller.setBottom(0);
        }
    }
    static void lazyTick(Object subject) {
        var controller = (CrushingWheelControllerTileEntity & InjectCrushingWheelControllerTileEntity) subject;
        SmartFluidTankBehaviour tank = controller.getTank();
        if (tank.isEmpty()) return;
        @SuppressWarnings("ConstantConditions") var blockEntity = controller.getWorld().getBlockEntity(controller.getPos().down());
        if (blockEntity == null) {
            blockEntity = controller.getWorld().getBlockEntity(controller.getPos().down(2));
        }
        long maxAmount = FluidConstants.BOTTLE;
        Storage<FluidVariant> capability = tank.getCapability();
        if (blockEntity instanceof BasinTileEntity basin) {
            StorageUtil.move(capability, basin.inputTank.getCapability(), Predicates.alwaysTrue(), maxAmount, null);
        } else if (blockEntity instanceof ItemDrainTileEntity drain) {
            var drainTank = drain.getBehaviour(SmartFluidTankBehaviour.TYPE);
            drainTank.allowInsertion();
            StorageUtil.move(capability, drainTank.getCapability(), Predicates.alwaysTrue(), maxAmount, null);
            drainTank.forbidInsertion();
        }
    }
    SmartFluidTankBehaviour getTank();
    void setTank(SmartFluidTankBehaviour tank);
    /**
     表示液柱底部以方块中心为原点的竖坐标，当≥0时无液柱。
     */
    double getBottom();
    void setBottom(double bottom);
}
