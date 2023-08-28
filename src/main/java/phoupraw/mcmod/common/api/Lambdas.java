package phoupraw.mcmod.common.api;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.EmptyItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Predicate;
/**
 一些快捷创建lambda表达式的静态方法。
 @since 1.0.0 */
public final class Lambdas {
    /**
     @param <T> the type of the input to the operation
     @return 一个什么都不做的 {@link Consumer}
     @since 1.0.0
     */
    @Contract(pure = true)
    public static <T> @NotNull Consumer<T> emptyConsumer() {
        return t -> {};
    }

    /**
     @return 一个什么都不做的 {@link Runnable}
     @since 1.0.0
     */
    @Contract(pure = true)
    public static @NotNull Runnable emptyRunnable() {
        return () -> {};
    }

    /**
     用于{@link StorageUtil#move}的{@code filter参数}
     @param predicate 要匹配的合成材料
     @return 用于匹配的predicate
     @since 1.0.0
     */
    @Contract(pure = true, value = "_->new")
    public static @NotNull Predicate<ItemVariant> matching(Ingredient predicate) {
        return v -> predicate.test(v.toStack());
    }

    /**
     @since 1.0.0
     转换泛型而不引起额外警告。
     */
    @Contract(value = "_ -> param1", pure = true)
    @SuppressWarnings("unchecked")
    public static <T> Predicate<T> cast(Predicate<?> predicate) {
        return (Predicate<T>) predicate;
    }

    /**
     @param emptyItem {@link FullItemFluidStorage#FullItemFluidStorage(ContainerItemContext, Item, FluidVariant, long)}的第2个参数
     @param variant {@link FullItemFluidStorage#FullItemFluidStorage(ContainerItemContext, Item, FluidVariant, long)}的第3个参数
     @param amount {@link FullItemFluidStorage#FullItemFluidStorage(ContainerItemContext, Item, FluidVariant, long)}的第4个参数
     @return 用于在 {@link  FluidStorage#combinedItemApiProvider} 注册的lambda表达式
     @see VirtualFluids#registerStorage
     @since 1.0.0
     如果想用{@link FullItemFluidStorage#FullItemFluidStorage(ContainerItemContext, Item, FluidVariant, long)}写{@link FluidStorage.CombinedItemApiProvider}但不想引入额外的lambda表达式，可以用此方法。
     */
    @Contract(pure = true, value = "_,_,_->new")
    public static FluidStorage.@NotNull CombinedItemApiProvider fullProviderOf(Item emptyItem, FluidVariant variant, long amount) {
        return context -> new FullItemFluidStorage(context, emptyItem, variant, amount);
    }

    /**
     @param fullItem {@link EmptyItemFluidStorage#EmptyItemFluidStorage(ContainerItemContext, Item, Fluid, long)}的第2个参数
     @param fluid {@link EmptyItemFluidStorage#EmptyItemFluidStorage(ContainerItemContext, Item, Fluid, long)}的第3个参数
     @param amount {@link EmptyItemFluidStorage#EmptyItemFluidStorage(ContainerItemContext, Item, Fluid, long)}的第4个参数
     @return 用于在 {@link  FluidStorage#combinedItemApiProvider} 注册的lambda表达式
     @see VirtualFluids#registerStorage
     @since 1.0.0
     如果想用{@link EmptyItemFluidStorage#EmptyItemFluidStorage(ContainerItemContext, Item, Fluid, long)}写{@link FluidStorage.CombinedItemApiProvider}但不想引入额外的lambda表达式，可以用此方法。
     */
    @Contract(pure = true, value = "_,_,_->new")
    public static FluidStorage.@NotNull CombinedItemApiProvider emptyProviderOf(Item fullItem, Fluid fluid, long amount) {
        return context -> new EmptyItemFluidStorage(context, fullItem, fluid, amount);
    }

    ///**
    // @param textures 纹理路径，例如<code>minecraft:block/stone</code>
    // @return 用于在 {@link  ClientSpriteRegistryCallback#event} 注册的lambda表达式
    // @see VirtualFluids#registerTexture
    // @since 1.0.0
    // 如果想在{@link ClientSpriteRegistryCallback}简单添加几张纹理而不想引入额外的lambda表达式，可以用此方法。
    // @since 0.1.0-pre9
    // */
    //@Contract(pure = true, value = "_->new")
    //@Environment(EnvType.CLIENT)
    //public static @NotNull ClientSpriteRegistryCallback addingTextures(Identifier... textures) {
    //    return (atlasTexture, registry) -> {
    //        for (Identifier texture : textures) registry.register(texture);
    //    };
    //}

    private Lambdas() {}

}
