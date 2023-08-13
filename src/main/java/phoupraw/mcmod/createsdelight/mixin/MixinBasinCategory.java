package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.compat.rei.category.BasinCategory;
import com.simibubi.create.compat.rei.display.CreateDisplay;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.utility.Pair;
import dev.architectury.fluid.FluidStack;
import me.shedaniel.math.Point;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.collection.DefaultedList;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import phoupraw.mcmod.common.api.REICreates;

import java.util.List;
@Mixin(BasinCategory.class)
@Environment(EnvType.CLIENT)
public class MixinBasinCategory {
    private final ThreadLocal<FluidIngredient> fluidIngredient = new ThreadLocal<>();

    @Inject(method = "addWidgets", at = @At(value = "INVOKE", target = "Lme/shedaniel/rei/api/common/util/EntryIngredients;of(Ldev/architectury/fluid/FluidStack;)Lme/shedaniel/rei/api/common/entry/EntryIngredient;", ordinal = 0, remap = false), locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
    private void captureVars(CreateDisplay<BasinRecipe> display, List<Widget> widgets, Point origin, CallbackInfo ci, BasinRecipe recipe, DefaultedList<FluidIngredient> fluidIngredients, List<Pair<Ingredient, MutableInt>> ingredients, List<ItemStack> itemOutputs, DefaultedList<io.github.fabricators_of_create.porting_lib.util.FluidStack> fluidOutputs, int size, int xOffset, int yOffset, int i, int j) {
        fluidIngredient.set(fluidIngredients.get(j));
    }

    @Redirect(method = "addWidgets", at = @At(value = "INVOKE", target = "Lme/shedaniel/rei/api/common/util/EntryIngredients;of(Ldev/architectury/fluid/FluidStack;)Lme/shedaniel/rei/api/common/entry/EntryIngredient;", ordinal = 0, remap = false), remap = false)
    private EntryIngredient fixFluidIngredient(FluidStack stack) {
        return REICreates.ingredientOf(fluidIngredient.get());
    }
}
