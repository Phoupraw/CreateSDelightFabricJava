package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.recipe.GrillingRecipe;
import phoupraw.mcmod.createsdelight.recipe.PanFryingRecipe;
import phoupraw.mcmod.createsdelight.recipe.SprinklingRecipe;
public class MyRecipeTypes {
    public static final RecipeTypeInfo<PanFryingRecipe> PAN_FRYING = new RecipeTypeInfo<>(new Identifier(MyIdentifiers.MOD_ID, "pan_frying"), PanFryingRecipe::new);
    public static final RecipeTypeInfo<GrillingRecipe> GRILLING = new RecipeTypeInfo<>(new Identifier(MyIdentifiers.MOD_ID, "grilling"), GrillingRecipe::new);
    public static final RecipeTypeInfo<SprinklingRecipe> SPRINKLING = new RecipeTypeInfo<>(new Identifier(MyIdentifiers.MOD_ID, "sprinkling"), SprinklingRecipe::new);

    /**
     * <b>不要注册！</b>已在构造器中注册。
     *
     * @param <R> 配方
     */
    public static final class RecipeTypeInfo<R extends ProcessingRecipe<?>> implements IRecipeTypeInfo {
        private final Identifier id;
        private final ProcessingRecipeSerializer<R> serializer;
        private final RecipeType<R> type;

        public RecipeTypeInfo(Identifier id, ProcessingRecipeSerializer<R> serializer, RecipeType<R> type) {
            this.id = id;
            this.serializer = serializer;
            this.type = type;
            Registry.register(Registry.RECIPE_TYPE, id, type);
            Registry.register(Registry.RECIPE_SERIALIZER,id,serializer);
        }

        public RecipeTypeInfo(Identifier id, ProcessingRecipeBuilder.ProcessingRecipeFactory<R> factory) {
            this.id = id;
            this.serializer = new ProcessingRecipeSerializer<>(factory);
            this.type = RecipeType.register(id.toString());
            Registry.register(Registry.RECIPE_SERIALIZER,id,serializer);
        }

        @Override
        public Identifier getId() {
            return id;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends RecipeSerializer<?>> T getSerializer() {
            return (T) serializer;
        }

        /**
         * 用
         */
        @SuppressWarnings("unchecked")
        @Override
        @Deprecated
        public <T extends RecipeType<?>> T getType() {
            return (T) type;
        }

        public RecipeType<R> getRecipeType() {
            return type;
        }
    }
}
