package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.compat.rei.display.CreateDisplay;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import phoupraw.mcmod.createsdelight.inject.InjectCreateDisplay;
@Mixin(CreateDisplay.class)
public abstract class MixinCreateDisplay {
    @ModifyArgs(method = "<init>(Lnet/minecraft/recipe/Recipe;Lme/shedaniel/rei/api/common/category/CategoryIdentifier;)V", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/compat/rei/display/CreateDisplay;<init>(Lnet/minecraft/recipe/Recipe;Lme/shedaniel/rei/api/common/category/CategoryIdentifier;Ljava/util/List;Ljava/util/List;)V"))
    private static void improveStupidDisplay(Args args, Recipe<?> recipe, CategoryIdentifier<CreateDisplay<Recipe<?>>> id) {
        InjectCreateDisplay.improveStupidDisplay(args, recipe, id);
    }
}
