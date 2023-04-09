package phoupraw.mcmod.common.api;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.transfer.v1.fluid.*;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.common.impl.BucketedVirtualFluid;
import phoupraw.mcmod.common.impl.VirtualFluid;
/**
 快捷创建虚拟流体及其相关静态方法。
 @since 1.0.0 */
public final class VirtualFluids {

    /**
     用{@link #getTranslationKey}本地化。
     */
    public static final FluidVariantAttributeHandler ATTRIBUTE_HANDLER = new FluidVariantAttributeHandler() {
        @Override
        public Text getName(FluidVariant fluidVariant) {
            return Text.translatable(getTranslationKey(fluidVariant.getFluid()));
        }
    };

    /**
     @return 新的虚拟流体
     @since 1.0.0
     */
    @Contract(value = " -> new", pure = true)
    public static @NotNull Fluid of() {
        return new VirtualFluid();
    }

    /**
     @param bucketItem 桶物品，但可以不是桶，可以由其它容器盛装流体。
     @return {@link Fluid#getBucketItem()}返回{@code bucketItem}的虚拟流体。
     @since 1.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Fluid of(@NotNull Item bucketItem) {
        return new BucketedVirtualFluid(bucketItem);
    }

    /**
     给再{@link #ATTRIBUTE_HANDLER}注册的流体生成本地化键。
     */
    public static String getTranslationKey(Fluid fluid) {
        Identifier id = Registry.FLUID.getId(fluid);
        return "fluid" + "." + id.getNamespace() + "." + id.getPath().replace('/', '.');
    }

    /**
     {@link FluidVariantAttributes#register}的批量快捷调用。
     */
    public static void registerAttributeHandler(FluidVariantAttributeHandler attributeHandler, Fluid... fluids) {
        for (Fluid fluid : fluids) FluidVariantAttributes.register(fluid, attributeHandler);
    }

    /**
     用{@link Items#BUCKET}和{@value FluidConstants#BUCKET}作为{@code emptyItem}和{@code amount}在{@link #registerStorage}注册。
     @since 1.0.0
     */
    public static void registerBucketStorage(Fluid fluid, Item fullItem) {
        registerStorage(Items.BUCKET, fullItem, fluid, FluidConstants.BUCKET);
    }

    /**
     用{@link Items#GLASS_BOTTLE}和{@value FluidConstants#BOTTLE}作为{@code emptyItem}和{@code amount}在{@link #registerStorage}注册。
     @since 1.0.0
     */
    public static void registerBottleStorage(Fluid fluid, Item fullItem) {
        registerStorage(Items.GLASS_BOTTLE, fullItem, fluid, FluidConstants.BOTTLE);
    }

    /**
     用{@link Items#BOWL}和<code>{@value FluidConstants#BUCKET}/4</code>作为{@code emptyItem}和{@code amount}在{@link #registerStorage}注册。
     @since 1.0.0
     */
    public static void registerBowlStorage(Fluid fluid, Item fullItem) {
        registerStorage(Items.BOWL, fullItem, fluid, FluidConstants.BUCKET / 4);
    }

    /**
     用{@link FluidStorage#combinedItemApiProvider}注册可逆的物品与流体转换，例如：水桶<->水+桶。
     @param emptyItem 空的容器物品，例如桶、玻璃瓶、碗等。
     @param fullItem 装满流体的容器物品，例如水桶、蜂蜜瓶、甜菜汤等。
     @param fluid 要被装进容器物品的流体。
     @param amount 一个容器物品可以盛装的流体的量。
     @see #registerBucketStorage
     @see #registerBottleStorage
     @see #registerBowlStorage
     @since 1.0.0
     */
    public static void registerStorage(Item emptyItem, Item fullItem, Fluid fluid, long amount) {
        FluidStorage.combinedItemApiProvider(emptyItem).register(Lambdas.emptyProviderOf(fullItem, fluid, amount));
        FluidStorage.combinedItemApiProvider(fullItem).register(Lambdas.fullProviderOf(emptyItem, FluidVariant.of(fluid), amount));
    }

    /**
     如果流体的纹理是<code>assets/namespace/textures/block/path.png</code>，则可以用此方法快捷注册纹理。
     <p>
     执行的注册：<code>
     FluidRenderHandlerRegistry.INSTANCE.register<br/>
     ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register
     </code>
     @param fluids 流体
     @see Lambdas#addingTextures
     @see SimpleFluidRenderHandler#SimpleFluidRenderHandler(Identifier, Identifier)
     @since 1.0.0
     */
    @SuppressWarnings("deprecation")
    @Environment(EnvType.CLIENT)
    public static void registerTexture(Fluid... fluids) {
        Identifier[] textures = new Identifier[fluids.length];
        for (int i = 0; i < fluids.length; i++) {
            var fluid = fluids[i];
            var texture = Registries.prefixBlock(Registry.FLUID.getId(fluid));
            textures[i] = texture;
            FluidRenderHandlerRegistry.INSTANCE.register(fluid, new SimpleFluidRenderHandler(texture, texture));
        }
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register(Lambdas.addingTextures(textures));
    }

    /**
     如果一个流体的流动纹理和静止纹理相同，可以用此方法快捷创建{@link SimpleFluidRenderHandler}。
     @param textureId 纹理路径，具体写法可以参考{@link SimpleFluidRenderHandler#WATER_STILL}等。
     @param tint 用于给纹理染色的RGB颜色，忽略透明度。
     @return {@link SimpleFluidRenderHandler}
     @see SimpleFluidRenderHandler#SimpleFluidRenderHandler(Identifier, Identifier, int)
     @since 1.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    @Environment(EnvType.CLIENT)
    public static @NotNull FluidRenderHandler newSimpleFluidRenderHandler(Identifier textureId, int tint) {
        return new SimpleFluidRenderHandler(textureId, textureId, tint);
    }

    private VirtualFluids() {

    }
}
