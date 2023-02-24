package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.common.Registries;
import phoupraw.mcmod.common.fluid.VirtualFluid;
import phoupraw.mcmod.common.misc.Lambdas;
import phoupraw.mcmod.createsdelight.datagen.MyChineseProvider;
import phoupraw.mcmod.createsdelight.datagen.MyEnglishProvider;
import phoupraw.mcmod.createsdelight.datagen.MyRecipeProvider;
import phoupraw.mcmod.createsdelight.fluid.SunFlowerOilFluid;
/**
 虚拟流体创建流程：
 <ol>
 <li>创建流体类，继承{@link VirtualFluid}，或者直接用{@link VirtualFluid}。<br>
 <li>在{@link MyIdentifiers}创建{@link Identifier}。<br>
 <li>在{@link MyFluids}创建流体<b>并注册</b>。<br>
 <li>在{@link MyItems}按照创建物品的流程创建桶、瓶或其它物品。</li>
 <li>补齐流体类的{@link Fluid#getBucketItem()}。<br>
 <li>在{@link MyChineseProvider}和{@link MyEnglishProvider}用{@link VirtualFluid#getTranslationKey}添加翻译。<br>
 <li>在{@link MyRecipeProvider}添加配方。<br>
 <li>运行数据生成器。<br>
 <li>在{@code src/main/resources/assets/createsdelight/textures/fluid}创建流体的纹理。<br>
 <li>在{@link MyModInitializer}用{@link Lambdas#emptyProviderOf}和{@link Lambdas#fullProviderOf}在{@link FluidStorage#combinedItemApiProvider}注册。<br>
 <li>在{@link MyModInitializer}用{@link VirtualFluid#ATTRIBUTE_HANDLER}在{@link FluidVariantAttributes#register}注册。<br>
 <li>在{@link MyClientModInitializer}用{@link SimpleFluidRenderHandler#coloredWater}在{@link FluidRenderHandlerRegistry#register(Fluid, FluidRenderHandler)}注册。<br>
 <li>在{@link MyClientModInitializer}用{@link RenderLayer#getTranslucent()}在{@link BlockRenderLayerMap#putFluids}注册。<br>
 <li>运行客户端，检查流体效果是否如预期。<br>
 <li>在{@code ChangeLog.md}添加更新日志。<br>
 <li>提交git。
 </ol> */
public final class MyFluids {
    public static final VirtualFluid SUNFLOWER_OIL = new SunFlowerOilFluid();
    public static final VirtualFluid VEGETABLE_BIG_STEW = new VirtualFluid();
    public static final VirtualFluid ROSE_MILK_TEA = new VirtualFluid();
    public static final VirtualFluid BEETROOT_SOUP = new VirtualFluid();
    public static final VirtualFluid TOMATO_SAUCE = new VirtualFluid();
    public static final VirtualFluid POPPY_RUSSIAN_SOUP = new VirtualFluid();
    static {
        Registries.register(MyIdentifiers.SUNFLOWER_OIL, SUNFLOWER_OIL);
        Registries.register(MyIdentifiers.VEGETABLE_BIG_STEW, VEGETABLE_BIG_STEW);
        Registries.register(MyIdentifiers.ROSE_MILK_TEA, ROSE_MILK_TEA);
        Registries.register(MyIdentifiers.BEETROOT_SOUP, BEETROOT_SOUP);
        Registries.register(MyIdentifiers.TOMATO_SAUCE, TOMATO_SAUCE);
        Registries.register(MyIdentifiers.POPPY_RUSSIAN_SOUP, POPPY_RUSSIAN_SOUP);
    }
    private MyFluids() {}
}
