package phoupraw.mcmod.createsdelight.registry;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.common.api.Lambdas;
import phoupraw.mcmod.common.api.Registries;
import phoupraw.mcmod.common.api.VirtualFluids;
import phoupraw.mcmod.createsdelight.datagen.MyChineseProvider;
import phoupraw.mcmod.createsdelight.datagen.MyEnglishProvider;
import phoupraw.mcmod.createsdelight.datagen.MyRecipeProvider;
/**
 虚拟流体创建流程：
 <ol>
 <li>在{@link MyIdentifiers}创建{@link Identifier}。<br>
 <li>在{@link MyFluids}用{@link VirtualFluids#of}创建流体<b>并注册</b>。<br>
 <li>若要装有该流体桶、瓶或其它物品，则在{@link MyItems}按照创建物品的流程创建物品。</li>
 <li>若有桶，则补齐{@link VirtualFluids#of(Item)}的参数。<br>
 <li>在{@link MyChineseProvider}和{@link MyEnglishProvider}用{@link VirtualFluids#getTranslationKey}添加翻译。<br>
 <li>在{@link MyRecipeProvider}添加配方。<br>
 <li>运行数据生成器。<br>
 <li>在{@code src/main/resources/assets/createsdelight/textures/block}创建流体的纹理。<br>
 <li>若有装有该流体桶、瓶或其它物品，在{@link MyModInitializer#initializeAfterCreate()}用{@link Lambdas#emptyProviderOf}和{@link Lambdas#fullProviderOf}在{@link FluidStorage#combinedItemApiProvider}注册流体物品转运。<br>
 <li>在{@link MyModInitializer#initializeAfterCreate()}用{@link VirtualFluids#ATTRIBUTE_HANDLER}在{@link FluidVariantAttributes#register}注册属性。<br>
 <li>在{@link MyClientModInitializer#onInitializeClient()}用{@link SimpleFluidRenderHandler#coloredWater}或其它{@link FluidRenderHandler}在{@link FluidRenderHandlerRegistry#register(Fluid, FluidRenderHandler)}注册贴图。<br>
 <li>若流体半透明，则在{@link MyClientModInitializer#onInitializeClient()}用{@link RenderLayer#getTranslucent()}在{@link BlockRenderLayerMap#putFluids}注册。<br>
 <li>运行客户端，检查流体效果是否如预期。<br>
 <li>在{@code ChangeLog.md}添加更新日志。<br>
 <li>提交git。
 </ol> */
public final class MyFluids {
    public static final Fluid SUNFLOWER_OIL = VirtualFluids.of(MyItems.BUCKETED_SUNFLOWER_OIL);
    public static final Fluid VEGETABLE_BIG_STEW = VirtualFluids.of(MyItems.VEGETABLE_BIG_STEW);
    public static final Fluid ROSE_MILK_TEA = VirtualFluids.of(MyItems.ROSE_MILK_TEA);
    public static final Fluid BEETROOT_SOUP = VirtualFluids.of(Items.BEETROOT_SOUP);
    public static final Fluid TOMATO_SAUCE = VirtualFluids.of(ItemsRegistry.TOMATO_SAUCE.get());
    public static final Fluid POPPY_RUSSIAN_SOUP = VirtualFluids.of(MyItems.POPPY_RUSSIAN_SOUP);
    public static final Fluid EGG_LIQUID = VirtualFluids.of(Items.EGG);
    public static final Fluid WHEAT_BLACK_TEA = VirtualFluids.of(MyItems.WHEAT_BLACK_TEA);
    public static final Fluid ICED_MELON_JUICE = VirtualFluids.of(MyItems.ICED_MELON_JUICE);
    public static final Fluid MELON_JUICE = VirtualFluids.of(ItemsRegistry.MELON_JUICE.get());
    public static final Fluid THICK_HOT_COCOA = VirtualFluids.of(MyItems.THICK_HOT_COCOA);
    public static final Fluid PASTE = VirtualFluids.of();
    public static final Fluid PUMPKIN_OIL = VirtualFluids.of(MyItems.BUCKETED_PUMPKIN_OIL);
    public static final Fluid APPLE_PASTE = VirtualFluids.of();
    public static final Fluid MASHED_POTATO = VirtualFluids.of(MyItems.MASHED_POTATO);
    //TODO 1桶苹果酒糟=1桶苹果酒+3稻米+3草秆+1瓶水+压煮
    //TODO 1桶高钙牛奶=1桶牛奶+深板岩圆石+竹子+淡灰色染料+绞肉+超级加热
    //TODO 1瓶灵魂热汤=1瓶岩浆+诡异菌索+灵魂土+搅拌
    static {
        Registries.register(MyIdentifiers.SUNFLOWER_OIL, SUNFLOWER_OIL);
        Registries.register(MyIdentifiers.VEGETABLE_BIG_STEW, VEGETABLE_BIG_STEW);
        Registries.register(MyIdentifiers.ROSE_MILK_TEA, ROSE_MILK_TEA);
        Registries.register(MyIdentifiers.BEETROOT_SOUP, BEETROOT_SOUP);
        Registries.register(MyIdentifiers.TOMATO_SAUCE, TOMATO_SAUCE);
        Registries.register(MyIdentifiers.POPPY_RUSSIAN_SOUP, POPPY_RUSSIAN_SOUP);
        Registries.register(MyIdentifiers.EGG_LIQUID, EGG_LIQUID);
        Registries.register(MyIdentifiers.WHEAT_BLACK_TEA, WHEAT_BLACK_TEA);
        Registries.register(MyIdentifiers.ICED_MELON_JUICE, ICED_MELON_JUICE);
        Registries.register(MyIdentifiers.MELON_JUICE, MELON_JUICE);
        Registries.register(MyIdentifiers.THICK_HOT_COCOA, THICK_HOT_COCOA);
        Registries.register(MyIdentifiers.PASTE, PASTE);
        Registries.register(MyIdentifiers.PUMPKIN_OIL, PUMPKIN_OIL);
        Registries.register(MyIdentifiers.APPLE_PASTE, APPLE_PASTE);
        Registries.register(MyIdentifiers.MASHED_PATATO, MASHED_POTATO);
    }
    private MyFluids() {}
}
