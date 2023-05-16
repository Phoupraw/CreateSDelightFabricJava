package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.content.contraptions.fluids.actors.FillingBySpout;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import phoupraw.mcmod.createsdelight.inject.InjectFillingBySpout;
@Mixin(FillingBySpout.class)
public class MixinFillingBySpout {
    @Inject(method = "getRequiredAmountForItem", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/contraptions/fluids/actors/GenericItemFilling;getRequiredAmountForItem(Lnet/minecraft/world/World;Lnet/minecraft/item/ItemStack;Lio/github/fabricators_of_create/porting_lib/util/FluidStack;)J"), cancellable = true)
    private static void findCakeBlueprint(World world, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<Long> cir) {
        InjectFillingBySpout.findCakeBlueprint(world, stack, availableFluid, cir);
    }

    @Inject(method = "fillItem", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/contraptions/fluids/actors/GenericItemFilling;fillItem(Lnet/minecraft/world/World;JLnet/minecraft/item/ItemStack;Lio/github/fabricators_of_create/porting_lib/util/FluidStack;)Lnet/minecraft/item/ItemStack;"), cancellable = true)
    private static void fillCakeBlueprint(World world, long requiredAmount, ItemStack stack, FluidStack availableFluid, CallbackInfoReturnable<ItemStack> cir) {
        InjectFillingBySpout.fillCakeBlueprint(world, requiredAmount, stack, availableFluid, cir);
    }
}
