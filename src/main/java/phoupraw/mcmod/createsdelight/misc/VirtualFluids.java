package phoupraw.mcmod.createsdelight.misc;

import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.fluid.*;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.EmptyItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.createsdelight.fluid.VirtualFluid;

/**
 * 快捷创建虚拟流体及其相关静态方法。
 * @since 1.0.0
 */
public final class VirtualFluids {

    /**
     * @return 新的虚拟流体
     * @since 1.0.0
     */
    @Contract(value = " -> new", pure = true)
    public static @NotNull Fluid of() {
        return new VirtualFluid();
    }

    /**
     * @param bucketItem 桶物品，但可以不是桶，可以由其它容器盛装流体。
     * @return {@link Fluid#getBucketItem()}返回{@code bucketItem}的虚拟流体。
     * @since 1.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Fluid of(@NotNull Item bucketItem) {
        return new BucketedVirtualFluid(bucketItem);
    }

    /**
     * {@link FluidVariantAttributes#register}的批量快捷调用。
     */
    public static void registerAttributeHandler(FluidVariantAttributeHandler attributeHandler, Fluid... fluids) {
        for (Fluid fluid : fluids) FluidVariantAttributes.register(fluid, attributeHandler);
    }

    /**
     * 用{@link Items#BUCKET}和{@value FluidConstants#BUCKET}作为{@code emptyItem}和{@code amount}在{@link #registerStorage}注册。
     * @since 1.0.0
     */
    public static void registerBucketStorage(Fluid fluid, Item fullItem) {
        registerStorage(Items.BUCKET, fullItem, fluid, FluidConstants.BUCKET);
    }

    /**
     * 用{@link Items#GLASS_BOTTLE}和{@value FluidConstants#BOTTLE}作为{@code emptyItem}和{@code amount}在{@link #registerStorage}注册。
     * @since 1.0.0
     */
    public static void registerBottleStorage(Fluid fluid, Item fullItem) {
        registerStorage(Items.GLASS_BOTTLE, fullItem, fluid, FluidConstants.BOTTLE);
    }

    /**
     * 用{@link Items#BOWL}和<code>{@value FluidConstants#BUCKET}/4</code>作为{@code emptyItem}和{@code amount}在{@link #registerStorage}注册。
     * @since 1.0.0
     */
    public static void registerBowlStorage(Fluid fluid, Item fullItem) {
        registerStorage(Items.BOWL, fullItem, fluid, FluidConstants.BUCKET / 4);
    }

    /**
     * 用{@link FluidStorage#combinedItemApiProvider}注册可逆的物品与流体转换，例如：水桶<->水+桶。
     * @param emptyItem 空的容器物品，例如桶、玻璃瓶、碗等。
     * @param fullItem 装满流体的容器物品，例如水桶、蜂蜜瓶、甜菜汤等。
     * @param fluid 要被装进容器物品的流体。
     * @param amount 一个容器物品可以盛装的流体的量。
     * @see #registerBucketStorage
     * @see #registerBottleStorage
     * @see #registerBowlStorage
     * @since 1.0.0
     */
    public static void registerStorage(Item emptyItem, Item fullItem, Fluid fluid, long amount) {
        FluidStorage.combinedItemApiProvider(emptyItem).register(emptyProviderOf(fullItem, fluid, amount));
        FluidStorage.combinedItemApiProvider(fullItem).register(fullProviderOf(emptyItem, FluidVariant.of(fluid), amount));
    }

    /**
     * @param emptyItem {@link FullItemFluidStorage#FullItemFluidStorage(ContainerItemContext, Item, FluidVariant, long)}的第2个参数
     * @param variant {@link FullItemFluidStorage#FullItemFluidStorage(ContainerItemContext, Item, FluidVariant, long)}的第3个参数
     * @param amount {@link FullItemFluidStorage#FullItemFluidStorage(ContainerItemContext, Item, FluidVariant, long)}的第4个参数
     * @return 用于在 {@link  FluidStorage#combinedItemApiProvider} 注册的lambda表达式
     * @see VirtualFluids#registerStorage
     * @since 1.0.0
     *   如果想用{@link FullItemFluidStorage#FullItemFluidStorage(ContainerItemContext, Item, FluidVariant, long)}写{@link FluidStorage.CombinedItemApiProvider}但不想引入额外的lambda表达式，可以用此方法。
     */
    @Contract(pure = true, value = "_,_,_->new")
    public static FluidStorage.@NotNull CombinedItemApiProvider fullProviderOf(Item emptyItem, FluidVariant variant, long amount) {
        return context -> new FullItemFluidStorage(context, emptyItem, variant, amount);
    }

    /**
     * @param fullItem {@link EmptyItemFluidStorage#EmptyItemFluidStorage(ContainerItemContext, Item, Fluid, long)}的第2个参数
     * @param fluid {@link EmptyItemFluidStorage#EmptyItemFluidStorage(ContainerItemContext, Item, Fluid, long)}的第3个参数
     * @param amount {@link EmptyItemFluidStorage#EmptyItemFluidStorage(ContainerItemContext, Item, Fluid, long)}的第4个参数
     * @return 用于在 {@link  FluidStorage#combinedItemApiProvider} 注册的lambda表达式
     * @see VirtualFluids#registerStorage
     * @since 1.0.0
     *   如果想用{@link EmptyItemFluidStorage#EmptyItemFluidStorage(ContainerItemContext, Item, Fluid, long)}写{@link FluidStorage.CombinedItemApiProvider}但不想引入额外的lambda表达式，可以用此方法。
     */
    @Contract(pure = true, value = "_,_,_->new")
    public static FluidStorage.@NotNull CombinedItemApiProvider emptyProviderOf(Item fullItem, Fluid fluid, long amount) {
        return context -> new EmptyItemFluidStorage(context, fullItem, fluid, amount);
    }
    public static SimpleFluidRenderHandler simpleFluidRenderHandlerOfBlock(Identifier fluidId) {
        Identifier textureId = fluidId.withPrefixedPath("block/");
        return new SimpleFluidRenderHandler(textureId, textureId);
    }
    private VirtualFluids() {

    }
}
