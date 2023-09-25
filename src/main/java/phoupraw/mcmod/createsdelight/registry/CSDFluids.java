package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import phoupraw.mcmod.createsdelight.client.CSDClientModInitializer;
import phoupraw.mcmod.createsdelight.datagen.CSDRecipeProvider;
import phoupraw.mcmod.createsdelight.datagen.client.CSDChineseProvider;
import phoupraw.mcmod.createsdelight.datagen.client.CSDEnglishProvider;
import phoupraw.mcmod.createsdelight.misc.ThickStaticFluid;
import phoupraw.mcmod.createsdelight.misc.VirtualFluids;

/**
 虚拟流体创建流程：
 <ol>
 <li>在{@link CSDIdentifiers}创建{@link Identifier}。<br/>
 <li>在{@link CSDFluids}用{@link VirtualFluids#of}或{@link ThickStaticFluid}创建流体<b>并注册</b>。<br/>
 <li>若要装有该流体桶、瓶或其它物品，则在{@link CSDItems}按照创建物品的流程创建物品。</li>
 <li>若有桶，则补齐{@link VirtualFluids#of(Item)}的参数。<br/>
 <li>在{@link CSDChineseProvider}和{@link CSDEnglishProvider}添加翻译。<br/>
 <li>在{@link CSDRecipeProvider}添加配方。<br/>
 <li>运行数据生成器。<br/>
 <li>在{@code src/main/resources/assets/createsdelight/textures/block}创建流体的纹理。<br/>
 <li>若有装有该流体桶、瓶或其它物品，用{@link VirtualFluids#emptyProviderOf}和{@link VirtualFluids#fullProviderOf}在{@link FluidStorage#combinedItemApiProvider}注册流体物品转运。<br/>
 <li>在{@link CSDClientModInitializer#onInitializeClient()}用{@link SimpleFluidRenderHandler#coloredWater}或其它{@link FluidRenderHandler}注册贴图。<br/>
 <li>若流体半透明，则在{@link CSDClientModInitializer#onInitializeClient()}用{@link RenderLayer#getTranslucent()}在{@link BlockRenderLayerMap#putFluids}注册。<br/>
 <li>运行客户端，检查流体效果是否如预期。<br/>
 <li>在{@code ChangeLog.md}添加更新日志。<br/>
 <li>提交git。
 </ol> */
public final class CSDFluids {
    //public static final Fluid SUNFLOWER_OIL = VirtualFluids.of(CDItems.BUCKETED_SUNFLOWER_OIL);
    //public static final Fluid VEGETABLE_BIG_STEW = VirtualFluids.of(CDItems.VEGETABLE_BIG_STEW);
    //public static final Fluid ROSE_MILK_TEA = VirtualFluids.of(CDItems.ROSE_MILK_TEA);
    //public static final Fluid BEETROOT_SOUP = VirtualFluids.of(Items.BEETROOT_SOUP);
    //public static final Fluid TOMATO_SAUCE = VirtualFluids.of(ItemsRegistry.TOMATO_SAUCE.get());
    //public static final Fluid POPPY_RUSSIAN_SOUP = VirtualFluids.of(CDItems.POPPY_RUSSIAN_SOUP);
    public static final Fluid EGG_LIQUID = VirtualFluids.of(CSDItems.BUCKETED_EGG_LIQUID);
    public static final Fluid APPLE_JAM = register(CSDIdentifiers.APPLE_JAM, new ThickStaticFluid(CSDItems.BUCKETED_APPLE_JAM, CSDBlocks.APPLE_JAM));
    public static final Fluid WHEAT_PASTE = register(CSDIdentifiers.WHEAT_PASTE, new ThickStaticFluid(CSDItems.BUCKETED_WHEAT_PASTE, CSDBlocks.WHEAT_PASTE));
    public static final Fluid CREAM = register(CSDIdentifiers.CREAM, new ThickStaticFluid(CSDItems.BUCKETED_CREAM, CSDBlocks.CREAM));
    //public static final Fluid WHEAT_BLACK_TEA = VirtualFluids.of(CDItems.WHEAT_BLACK_TEA);
    //public static final Fluid ICED_MELON_JUICE = VirtualFluids.of(CDItems.ICED_MELON_JUICE);
    //public static final Fluid MELON_JUICE = VirtualFluids.of(ItemsRegistry.MELON_JUICE.get());
    //public static final Fluid THICK_HOT_COCOA = VirtualFluids.of(CDItems.THICK_HOT_COCOA);
    //public static final Fluid PASTE = VirtualFluids.of();
    //public static final Fluid PUMPKIN_OIL = VirtualFluids.of(CDItems.BUCKETED_PUMPKIN_OIL);
    //public static final Fluid APPLE_PASTE = VirtualFluids.of();
    //public static final Fluid MASHED_POTATO = VirtualFluids.of(CDItems.MASHED_POTATO);
    //public static final Fluid CHOCOLATE_PASTE = VirtualFluids.of();
    static {
        register(CSDIdentifiers.EGG_LIQUID, EGG_LIQUID);
        //Registries2.register(CDIdentifiers.SUNFLOWER_OIL, SUNFLOWER_OIL);
        ////Registries.register(CDIdentifiers.VEGETABLE_BIG_STEW, VEGETABLE_BIG_STEW);
        ////Registries.register(CDIdentifiers.ROSE_MILK_TEA, ROSE_MILK_TEA);
        //Registries2.register(CDIdentifiers.BEETROOT_SOUP, BEETROOT_SOUP);
        //Registries2.register(CDIdentifiers.TOMATO_SAUCE, TOMATO_SAUCE);
        ////Registries.register(CDIdentifiers.POPPY_RUSSIAN_SOUP, POPPY_RUSSIAN_SOUP);
        ////Registries.register(CDIdentifiers.WHEAT_BLACK_TEA, WHEAT_BLACK_TEA);
        ////Registries.register(CDIdentifiers.ICED_MELON_JUICE, ICED_MELON_JUICE);
        //Registries2.register(CDIdentifiers.MELON_JUICE, MELON_JUICE);
        ////Registries.register(CDIdentifiers.THICK_HOT_COCOA, THICK_HOT_COCOA);
        //Registries2.register(CDIdentifiers.PASTE, PASTE);
        //Registries2.register(CDIdentifiers.PUMPKIN_OIL, PUMPKIN_OIL);
        //Registries2.register(CDIdentifiers.APPLE_PASTE, APPLE_PASTE);
        ////Registries.register(CDIdentifiers.MASHED_PATATO, MASHED_POTATO);
        //Registries2.register(CDIdentifiers.CHOCOLATE_PASTE, CHOCOLATE_PASTE);
    }
    /**
     * @since 1.0.0
     */
    @Contract("_, _ -> param2")
    public static <T extends Fluid> T register(Identifier id, T fluid) {
        return Registry.register(Registries.FLUID, id, fluid);
    }

    private CSDFluids() {
    }

}
