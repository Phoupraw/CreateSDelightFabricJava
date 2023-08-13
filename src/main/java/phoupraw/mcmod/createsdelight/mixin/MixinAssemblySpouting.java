package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.compat.rei.category.sequencedAssembly.ReiSequencedAssemblySubCategory;
import com.simibubi.create.content.processing.sequenced.SequencedRecipe;
import me.shedaniel.math.Point;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import phoupraw.mcmod.common.api.REICreates;

import java.util.List;
@Mixin(ReiSequencedAssemblySubCategory.AssemblySpouting.class)
@Environment(EnvType.CLIENT)
public class MixinAssemblySpouting {
//    @Redirect(method = "addFluidIngredients", at = @At(value = "INVOKE", target = "Lme/shedaniel/rei/api/common/util/EntryIngredients;of(Ldev/architectury/fluid/FluidStack;)Lme/shedaniel/rei/api/common/entry/EntryIngredient;"))
//    private EntryIngredient fixInexplicableCode(FluidStack stack, SequencedRecipe<?> recipe, List<Widget> widgets, int x, int index, Point origin) {
//        return PanFryingDisplay.of(recipe.getRecipe().getFluidIngredients().get(0));
//    }

    @ModifyArg(method = "addFluidIngredients", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;"), remap = false)
    private int fixIndex(int index) {
        return 0;
    }

    @ModifyArgs(method = "addFluidIngredients", at = @At(value = "INVOKE", target = "Lme/shedaniel/rei/api/client/gui/widgets/Slot;entries(Ljava/util/Collection;)Lme/shedaniel/rei/api/client/gui/widgets/Slot;"))
    private void fluidIngredientToREI(Args args, SequencedRecipe<?> recipe, List<Widget> widgets, int x, int index, Point origin) {
        args.set(0, REICreates.ingredientOf(recipe.getRecipe().getFluidIngredients().get(0)));
    }
}
