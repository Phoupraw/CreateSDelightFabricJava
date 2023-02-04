package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder.ProcessingRecipeFactory;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.display.Display;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.datagen.MyChineseProvider;
import phoupraw.mcmod.createsdelight.datagen.MyEnglishProvider;
import phoupraw.mcmod.createsdelight.recipe.GrillingRecipe;
import phoupraw.mcmod.createsdelight.recipe.PanFryingRecipe;
import phoupraw.mcmod.createsdelight.recipe.SprinklingRecipe;
import phoupraw.mcmod.createsdelight.recipe.SteamingRecipe;
/**
 * 配方类型添加步骤：<br>
 * - 创建配方类，继承{@link ProcessingRecipe}，创建符合{@link ProcessingRecipeFactory}的构造器<br>
 * - 在{@link MyRecipeTypes}创建{@link RecipeTypeInfo}<br>
 * - 补齐配方类的构造器中的{@link IRecipeTypeInfo}<br>
 * - 创建显示类，继承{@link Display}，创建接受配方类对象作为唯一参数的构造器<br>
 * - 创建类别类，继承{@link DisplayCategory}，重写{@link DisplayCategory#getIcon()}<br>
 * - 补齐显示类的{@link Display#getCategoryIdentifier()}<br>
 * - 在{@link MyREIClientPlugin}注册<br>
 * - 在{@link MyChineseProvider}和{@link MyEnglishProvider}用{@link MyChineseProvider#keyOfCategory}添加翻译<br>
 * - 运行数据生成器<br>
 * - 重写类别类的{@link DisplayCategory#setupDisplay(Display, Rectangle)}<br>
 * - 在{@code ChangeLog.md}添加更新日志<br>
 * - 提交git<br>
 */
@SuppressWarnings("OverrideOnly")
public class MyRecipeTypes {
    public static final RecipeTypeInfo<PanFryingRecipe> PAN_FRYING = new RecipeTypeInfo<>(new Identifier(MyIdentifiers.MOD_ID, "pan_frying"), PanFryingRecipe::new);
    public static final RecipeTypeInfo<GrillingRecipe> GRILLING = new RecipeTypeInfo<>(new Identifier(MyIdentifiers.MOD_ID, "grilling"), GrillingRecipe::new);
    public static final RecipeTypeInfo<SprinklingRecipe> SPRINKLING = new RecipeTypeInfo<>(new Identifier(MyIdentifiers.MOD_ID, "sprinkling"), SprinklingRecipe::new);
    public static final RecipeTypeInfo<SteamingRecipe> STEAMING = new RecipeTypeInfo<>(new Identifier(MyIdentifiers.MOD_ID, "steaming"), SteamingRecipe::new);

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

        public RecipeTypeInfo(Identifier id, ProcessingRecipeFactory<R> factory) {
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
