package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.contraptions.fluids.actors.GenericItemFilling;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import phoupraw.mcmod.createsdelight.inject.InjectGenericItemFilling;
@Mixin(GenericItemFilling.class)
public class MixinGenericItemFilling {
    @ModifyArgs(method = "canItemBeFilled", at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/lookup/v1/item/ItemApiLookup;find(Lnet/minecraft/item/ItemStack;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static void addWorld(Args args, World world, ItemStack stack) {
        InjectGenericItemFilling.addWorld(args, world);
    }

    @ModifyArgs(method = "getRequiredAmountForItem", at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/lookup/v1/item/ItemApiLookup;find(Lnet/minecraft/item/ItemStack;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static void addWorld(Args args, World world, ItemStack stack, FluidStack availableFluid) {
        InjectGenericItemFilling.addWorld(args, world);
    }

    @ModifyArgs(method = "fillItem", at = @At(value = "INVOKE", target = "Lnet/fabricmc/fabric/api/lookup/v1/item/ItemApiLookup;find(Lnet/minecraft/item/ItemStack;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static void addWorld(Args args, World world, long requiredAmount, ItemStack stack, FluidStack availableFluid) {
        InjectGenericItemFilling.addWorld(args, world);
    }
}
