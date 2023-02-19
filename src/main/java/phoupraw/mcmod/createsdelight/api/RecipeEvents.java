package phoupraw.mcmod.createsdelight.api;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import phoupraw.mcmod.createsdelight.mixin.MixinRecipeManager;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
public final class RecipeEvents {
    /**
     * 此事件仅供服务端调用，添加的配方会自动同步到客户端。
     *
     * @see MixinRecipeManager#append_0
     * @see RecipeManager#apply
     */
    public static final Event<Consumer<Consumer<Recipe<?>>>> APPEND_0 = EventFactory.createArrayBacked(Consumer.class, callbacks -> adder -> {
        for (var callback : callbacks) callback.accept(adder);
    });
    /**
     * 此事件仅供服务端调用，添加的配方会自动同步到客户端。
     *
     * @see MixinRecipeManager#append_1
     * @see RecipeManager
     */
    public static final Event<BiConsumer<RecipeManager, Consumer<Recipe<?>>>> APPEND_1 = EventFactory.createArrayBacked(BiConsumer.class, callbacks -> (recipeManager, adder) -> {
        for (var callback : callbacks) callback.accept(recipeManager, adder);
    });

    private RecipeEvents() {}
}
