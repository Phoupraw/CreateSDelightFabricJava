package phoupraw.mcmod.createsdelight.inject;

import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import phoupraw.mcmod.createsdelight.item.CakeBlueprintItem;
import phoupraw.mcmod.createsdelight.registry.MyItems;
@ApiStatus.Internal
public interface InjectFillingBySpout {
    static void findCakeBlueprint(World world, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<Long> cir) {
        if (!stack.isOf(MyItems.CAKE_BLUEPRINT)) return;
        var nextStep = CakeBlueprintItem.getNextStep(stack.getNbt());
        if (nextStep == null) return;
        if (nextStep.getKey().isOf(availableFluid.getFluid())) {
            cir.setReturnValue(CakeBlueprintItem.getAmount(nextStep.getValue()));
        }
    }
    static void fillCakeBlueprint(World world, long requiredAmount, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<ItemStack> cir) {
        if (!stack.isOf(MyItems.CAKE_BLUEPRINT)) return;
        NbtCompound nbt = stack.getNbt();
        var nextStep = CakeBlueprintItem.getNextStep(nbt);
        if (nextStep == null) return;
        if (nextStep.getKey().isOf(availableFluid.getFluid())) {
            availableFluid.shrink(requiredAmount);
            CakeBlueprintItem.setIndex(nbt, CakeBlueprintItem.getIndex(nbt) + 1);
            cir.setReturnValue(stack);
        }
    }
}
