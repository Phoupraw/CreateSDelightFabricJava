package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

///**
// 配方类型添加步骤：
// <ol>
// <li>在{@link phoupraw.mcmod.createsdelight.recipe}创建配方类，继承{@link ProcessingRecipe}，创建符合{@link ProcessingRecipeBuilder.ProcessingRecipeFactory}的构造器<br/>
// <li>在{@link CDRecipeTypes}创建{@link RecipeTypeInfo}<br/>
// <li>补齐配方类的构造器中的{@link IRecipeTypeInfo}<br/>
// <li>在{@link phoupraw.mcmod.createsdelight.rei}创建显示类，继承{@link Display}，创建接受配方类对象作为唯一参数的构造器<br/>
// <li>在{@link phoupraw.mcmod.createsdelight.rei}创建类别类，继承{@link DisplayCategory}，重写{@link DisplayCategory#getIcon()}<br/>
// <li>补齐显示类的{@link Display#getCategoryIdentifier()}<br/>
// <li>在{@link CDREIClientPlugin}注册<br/>
// <li>在{@link CDChineseProvider}和{@link CDEnglishProvider}用{@link Internationals#keyOfCategory}添加翻译<br/>
// <li>运行数据生成器<br/>
// <li>重写类别类的{@link DisplayCategory#setupDisplay(Display, Rectangle)}<br/>
// <li>在{@code ChangeLog.md}添加更新日志<br/>
// <li>提交git
// </ol> */
public final class CDRecipeTypes {

    static {
        //        RecipeEvents.APPEND_1.register((recipeManager,adder) -> {
        //            var recipes = recipeManager.listAllOfType(RecipeTypesRegistry.CUTTING_RECIPE_SERIALIZER.<CuttingBoardRecipe>type()).stream().filter(Lambdas.matchingTool(ItemsRegistry.IRON_KNIFE.get().getDefaultStack())).toList();
        //            for (var recipe : recipes) {
        //                adder.accept(VerticalCuttingRecipe.of(recipe));
        //            }
        //        });
    }
    private CDRecipeTypes() {
    }

    /**
     <b>不要注册！</b>已在构造器中注册。
     @param <R> 配方
     */
    public static final class RecipeTypeInfo<R extends ProcessingRecipe<?>> implements IRecipeTypeInfo {

        private final Identifier id;
        private final ProcessingRecipeSerializer<R> serializer;
        private final RecipeType<R> type;

        /**
         @param id 用于注册序列化器和配方类型
         @param serializer 未注册的序列化器
         @param type 未注册的配方类型
         @throws IllegalStateException 若{@code serializer}或{@code type}已注册
         */
        public RecipeTypeInfo(Identifier id, ProcessingRecipeSerializer<R> serializer, RecipeType<R> type) {
            this.id = id;
            this.serializer = serializer;
            this.type = type;
            Registry.register(Registries.RECIPE_TYPE, id, type);
            Registry.register(Registries.RECIPE_SERIALIZER, id, serializer);
        }

        public RecipeTypeInfo(Identifier id, ProcessingRecipeBuilder.ProcessingRecipeFactory<R> factory) {
            this.id = id;
            this.serializer = new ProcessingRecipeSerializer<>(factory);
            this.type = RecipeType.register(id.toString());
            Registry.register(Registries.RECIPE_SERIALIZER, id, serializer);
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
         我不知道为什么simibubi要搞个泛型方法，很不好用。
         @see #getRecipeType()
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
