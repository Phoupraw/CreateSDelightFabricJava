package phoupraw.mcmod.createsdelight.mixin;

import com.google.gson.Gson;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
@Mixin(RecipeManager.class)
public interface AccessRecipeManager {
    @Contract(pure = true)
    @Accessor
    static @NotNull Gson getGSON() {
        //noinspection ConstantConditions
        return null;
    }
    @Contract(pure = true)
    @Accessor
    Map<RecipeType<?>, Map<Identifier, Recipe<?>>> getRecipes();
    @Contract(mutates = "this")
    @Accessor
    void setRecipes(Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipes);
    @Contract(pure = true)
    @Accessor
    Map<Identifier, Recipe<?>> getRecipesById();
    @Contract(mutates = "this")
    @Accessor
    void setRecipesById(Map<Identifier, Recipe<?>> recipesById);
}
