package phoupraw.mcmod.common.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.loot.v2.LootTableSource;
import net.minecraft.loot.LootTable;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.UnmodifiableView;
import phoupraw.mcmod.createsdelight.mixin.MixinRecipeManager;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
public final class RecipeEvents {
    /**
     在所有配方已经加载完毕后立刻调用。
     */
    public static final Event<Consumer<@UnmodifiableView Map<RecipeType<?>, @UnmodifiableView Map<Identifier, Recipe<?>>>>> FINAL = EventFactory.createArrayBacked(Consumer.class, callbacks -> recipes -> {
        for (var callback : callbacks) callback.accept(recipes);
    });
    /**
     此事件仅供服务端调用，添加的配方会自动同步到客户端。
     @see MixinRecipeManager#append_0
     @see RecipeManager#apply
     */
    public static final Event<Consumer<Consumer<Recipe<?>>>> APPEND_0 = EventFactory.createArrayBacked(Consumer.class, callbacks -> adder -> {
        for (var callback : callbacks) callback.accept(adder);
    });
    /**
     此事件仅供服务端调用，添加的配方会自动同步到客户端。
     @see MixinRecipeManager#append_1
     @see RecipeManager
     */
    public static final Event<BiConsumer<RecipeManager, Consumer<Recipe<?>>>> APPEND_1 = EventFactory.createArrayBacked(BiConsumer.class, callbacks -> (recipeManager, adder) -> {
        for (var callback : callbacks) callback.accept(recipeManager, adder);
    });

    public interface Modify {
        void modifyLootTable(ResourceManager resourceManager, RecipeManager recipeManager, Identifier id, LootTable.Builder tableBuilder, LootTableSource source);
    }

    private RecipeEvents() {}
}
