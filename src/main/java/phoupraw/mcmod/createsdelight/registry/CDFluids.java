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
import phoupraw.mcmod.createsdelight.datagen.CDChineseProvider;
import phoupraw.mcmod.createsdelight.datagen.CDEnglishProvider;
import phoupraw.mcmod.createsdelight.datagen.CDRecipeProvider;
/**
 虚拟流体创建流程：
 <ol>
 <li>在{@link CDIdentifiers}创建{@link Identifier}。<br/>
 <li>在{@link CDFluids}用{@link VirtualFluids#of}创建流体<b>并注册</b>。<br/>
 <li>若要装有该流体桶、瓶或其它物品，则在{@link CDItems}按照创建物品的流程创建物品。</li>
 <li>若有桶，则补齐{@link VirtualFluids#of(Item)}的参数。<br/>
 <li>在{@link CDChineseProvider}和{@link CDEnglishProvider}用{@link VirtualFluids#getTranslationKey}添加翻译。<br/>
 <li>在{@link CDRecipeProvider}添加配方。<br/>
 <li>运行数据生成器。<br/>
 <li>在{@code src/main/resources/assets/createsdelight/textures/block}创建流体的纹理。<br/>
 <li>若有装有该流体桶、瓶或其它物品，在{@link CDModInitializer#initializeAfterCreate()}用{@link Lambdas#emptyProviderOf}和{@link Lambdas#fullProviderOf}在{@link FluidStorage#combinedItemApiProvider}注册流体物品转运。<br/>
 <li>在{@link CDModInitializer#initializeAfterCreate()}用{@link VirtualFluids#ATTRIBUTE_HANDLER}在{@link FluidVariantAttributes#register}注册属性。<br/>
 <li>在{@link CDClientModInitializer#onInitializeClient()}用{@link SimpleFluidRenderHandler#coloredWater}或其它{@link FluidRenderHandler}在{@link FluidRenderHandlerRegistry#register(Fluid, FluidRenderHandler)}注册贴图。<br/>
 <li>若流体半透明，则在{@link CDClientModInitializer#onInitializeClient()}用{@link RenderLayer#getTranslucent()}在{@link BlockRenderLayerMap#putFluids}注册。<br/>
 <li>运行客户端，检查流体效果是否如预期。<br/>
 <li>在{@code ChangeLog.md}添加更新日志。<br/>
 <li>提交git。
 </ol> */
public final class CDFluids {
    public static final Fluid SUNFLOWER_OIL = VirtualFluids.of(CDItems.BUCKETED_SUNFLOWER_OIL);
    //public static final Fluid VEGETABLE_BIG_STEW = VirtualFluids.of(CDItems.VEGETABLE_BIG_STEW);
    //public static final Fluid ROSE_MILK_TEA = VirtualFluids.of(CDItems.ROSE_MILK_TEA);
    public static final Fluid BEETROOT_SOUP = VirtualFluids.of(Items.BEETROOT_SOUP);
    public static final Fluid TOMATO_SAUCE = VirtualFluids.of(ItemsRegistry.TOMATO_SAUCE.get());
    //public static final Fluid POPPY_RUSSIAN_SOUP = VirtualFluids.of(CDItems.POPPY_RUSSIAN_SOUP);
    public static final Fluid EGG_LIQUID = VirtualFluids.of(Items.EGG);
    //public static final Fluid WHEAT_BLACK_TEA = VirtualFluids.of(CDItems.WHEAT_BLACK_TEA);
    //public static final Fluid ICED_MELON_JUICE = VirtualFluids.of(CDItems.ICED_MELON_JUICE);
    public static final Fluid MELON_JUICE = VirtualFluids.of(ItemsRegistry.MELON_JUICE.get());
    //public static final Fluid THICK_HOT_COCOA = VirtualFluids.of(CDItems.THICK_HOT_COCOA);
    public static final Fluid PASTE = VirtualFluids.of();
    public static final Fluid PUMPKIN_OIL = VirtualFluids.of(CDItems.BUCKETED_PUMPKIN_OIL);
    public static final Fluid APPLE_PASTE = VirtualFluids.of();
    //public static final Fluid MASHED_POTATO = VirtualFluids.of(CDItems.MASHED_POTATO);
    public static final Fluid CHOCOLATE_PASTE = VirtualFluids.of();
    //TODO 1桶苹果酒糟=1桶苹果酒+3稻米+3草秆+1瓶水+压煮
    //TODO 1桶高钙牛奶=1桶牛奶+深板岩圆石+竹子+淡灰色染料+绞肉+超级加热
    //TODO 1瓶灵魂热汤=1瓶岩浆+诡异菌索+灵魂土+搅拌
    static {
        Registries.register(CDIdentifiers.SUNFLOWER_OIL, SUNFLOWER_OIL);
        //Registries.register(CDIdentifiers.VEGETABLE_BIG_STEW, VEGETABLE_BIG_STEW);
        //Registries.register(CDIdentifiers.ROSE_MILK_TEA, ROSE_MILK_TEA);
        Registries.register(CDIdentifiers.BEETROOT_SOUP, BEETROOT_SOUP);
        Registries.register(CDIdentifiers.TOMATO_SAUCE, TOMATO_SAUCE);
        //Registries.register(CDIdentifiers.POPPY_RUSSIAN_SOUP, POPPY_RUSSIAN_SOUP);
        Registries.register(CDIdentifiers.EGG_LIQUID, EGG_LIQUID);
        //Registries.register(CDIdentifiers.WHEAT_BLACK_TEA, WHEAT_BLACK_TEA);
        //Registries.register(CDIdentifiers.ICED_MELON_JUICE, ICED_MELON_JUICE);
        Registries.register(CDIdentifiers.MELON_JUICE, MELON_JUICE);
        //Registries.register(CDIdentifiers.THICK_HOT_COCOA, THICK_HOT_COCOA);
        Registries.register(CDIdentifiers.PASTE, PASTE);
        Registries.register(CDIdentifiers.PUMPKIN_OIL, PUMPKIN_OIL);
        Registries.register(CDIdentifiers.APPLE_PASTE, APPLE_PASTE);
        //Registries.register(CDIdentifiers.MASHED_PATATO, MASHED_POTATO);
        Registries.register(CDIdentifiers.CHOCOLATE_PASTE, CHOCOLATE_PASTE);
    }
    private CDFluids() {
    }
}
