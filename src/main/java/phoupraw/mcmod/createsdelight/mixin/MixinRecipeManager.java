package phoupraw.mcmod.createsdelight.mixin;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import phoupraw.mcmod.createsdelight.api.RecipeEvents;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
@Mixin(RecipeManager.class)
public abstract class MixinRecipeManager {
    @Shadow
    private Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipes;

    @Shadow
    private Map<Identifier, Recipe<?>> recipesById;

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At(value = "INVOKE", target = "Ljava/util/Map;entrySet()Ljava/util/Set;", ordinal = 1), locals = LocalCapture.CAPTURE_FAILHARD)
    private void append_0(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci, Map<RecipeType<?>, ImmutableMap.Builder<Identifier, Recipe<?>>> map2, ImmutableMap.Builder<Identifier, Recipe<?>> builder) {
        RecipeEvents.APPEND_0.invoker().accept(recipe -> {
            var map2Builder = map2.get(recipe.getType());
            if (map2Builder == null) {
                map2Builder = ImmutableMap.builder();
                map2.put(recipe.getType(), map2Builder);
            }
            map2Builder.put(recipe.getId(), recipe);
            builder.put(recipe.getId(), recipe);
        });
    }

    @Inject(method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V", at = @At("RETURN"))
    private void append_1(Map<Identifier, JsonElement> map, ResourceManager resourceManager, Profiler profiler, CallbackInfo ci) {
        Collection<Recipe<?>> toBeAdded = new LinkedList<>();
        RecipeEvents.APPEND_1.invoker().accept((RecipeManager) (Object) this, toBeAdded::add);
        Map<RecipeType<?>, ImmutableMap.Builder<Identifier, Recipe<?>>> recipesByType = new HashMap<>();
        for (Map.Entry<RecipeType<?>, Map<Identifier, Recipe<?>>> e : this.recipes.entrySet()) {
            RecipeType<?> type = e.getKey();
            Map<Identifier, Recipe<?>> map2 = e.getValue();
            ImmutableMap.Builder<Identifier, Recipe<?>> builder = ImmutableMap.builder();
            builder.putAll(map2);
            recipesByType.put(type, builder);
        }
        ImmutableMap.Builder<Identifier, Recipe<?>> recipesById = ImmutableMap.builder();
        recipesById.putAll(this.recipesById);
        for (var recipe : toBeAdded) {
            var typedRecipes = recipesByType.get(recipe.getType());
            if (typedRecipes == null) {
                typedRecipes = ImmutableMap.builder();
                recipesByType.put(recipe.getType(), typedRecipes);
            }
            typedRecipes.put(recipe.getId(), recipe);
            recipesById.put(recipe.getId(), recipe);
        }
        ImmutableMap.Builder<RecipeType<?>, Map<Identifier, Recipe<?>>> recipesByType2 = ImmutableMap.builder();
        for (Map.Entry<RecipeType<?>, ImmutableMap.Builder<Identifier, Recipe<?>>> entry : recipesByType.entrySet()) {
            RecipeType<?> type = entry.getKey();
            ImmutableMap.Builder<Identifier, Recipe<?>> map2 = entry.getValue();
            recipesByType2.put(type, map2.build());
        }
        this.recipes = recipesByType2.build();
        this.recipesById = recipesById.build();
    }
}
