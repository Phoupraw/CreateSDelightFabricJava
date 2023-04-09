package phoupraw.mcmod.createsdelight.registry;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.components.millstone.MillingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.foundation.block.BlockStressDefaults;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.common.api.Lambdas;
import phoupraw.mcmod.common.api.RecipeEvents;
import phoupraw.mcmod.common.api.VirtualFluids;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.block.IronBarsBlock;
import phoupraw.mcmod.createsdelight.item.IronBowlItem;

import java.util.regex.Pattern;
/**
 模组构建和发布流程（每次修改代码后都需要从头开始，切不可跳步骤）：
 <ol border="1">
 <li>在开发环境运行客户端，进入世界，简单随意游玩，确保不会崩溃。</li>
 <li>在开发环境运行专用服务端，确保不会崩溃。</li>
 <li>运行{@code publishToMavenLocal}或{@code publish} Gradle任务，如果报错，一般是Mixin的remap错误，请根据情况把报错的mixin的remap设为{@code false}。<br/></li>
 <li>在生产环境运行客户端，进入世界，简单随意游玩，确保不会崩溃。</li>
 <li>在{@code gradle.properties}里把版本号加一，并提交git。</li>
 <li>在<a href="https://modrinth.com/mod/creates-delight/versions">Modrinth</a>和<a href="https://beta.curseforge.com/minecraft/mc-mods/creates-delight/files">Curseforge</a>上发布新版本。</li>
 </ol> */
@ApiStatus.Internal
public final class MyModInitializer implements ModInitializer {
    public static void initializeAfterCreate() {
        loadClasses();

        BlockStressDefaults.setDefaultImpact(MyIdentifiers.SPRINKLER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.VERTICAL_CUTTER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.PRESSURE_COOKER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.MINCER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.SKEWER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.IRON_BAR_SKEWER, 1);

        VirtualFluids.registerAttributeHandler(VirtualFluids.ATTRIBUTE_HANDLER, MyFluids.SUNFLOWER_OIL, MyFluids.VEGETABLE_BIG_STEW, MyFluids.ROSE_MILK_TEA, MyFluids.BEETROOT_SOUP, MyFluids.TOMATO_SAUCE, MyFluids.POPPY_RUSSIAN_SOUP, MyFluids.EGG_LIQUID, MyFluids.WHEAT_BLACK_TEA, MyFluids.ICED_MELON_JUICE, MyFluids.MELON_JUICE, MyFluids.THICK_HOT_COCOA, MyFluids.PASTE, MyFluids.PUMPKIN_OIL, MyFluids.APPLE_PASTE, MyFluids.MASHED_POTATO, MyFluids.CHOCOLATE_PASTE);
        VirtualFluids.registerBowlStorage(MyFluids.VEGETABLE_BIG_STEW, MyItems.VEGETABLE_BIG_STEW);
        VirtualFluids.registerBucketStorage(MyFluids.SUNFLOWER_OIL, MyItems.BUCKETED_SUNFLOWER_OIL);
        VirtualFluids.registerBottleStorage(MyFluids.SUNFLOWER_OIL, MyItems.BOTTLED_SUNFLOWER_OIL);
        VirtualFluids.registerBottleStorage(MyFluids.ROSE_MILK_TEA, MyItems.ROSE_MILK_TEA);
        VirtualFluids.registerBowlStorage(MyFluids.BEETROOT_SOUP, Items.BEETROOT_SOUP);
        VirtualFluids.registerBowlStorage(MyFluids.TOMATO_SAUCE, ItemsRegistry.TOMATO_SAUCE.get());
        VirtualFluids.registerBowlStorage(MyFluids.POPPY_RUSSIAN_SOUP, MyItems.POPPY_RUSSIAN_SOUP);
        FluidStorage.combinedItemApiProvider(Items.EGG).register(Lambdas.fullProviderOf(MyItems.EGG_SHELL, FluidVariant.of(MyFluids.EGG_LIQUID), FluidConstants.BOTTLE / 2));
        VirtualFluids.registerBowlStorage(MyFluids.POPPY_RUSSIAN_SOUP, MyItems.POPPY_RUSSIAN_SOUP);
        VirtualFluids.registerBottleStorage(MyFluids.WHEAT_BLACK_TEA, MyItems.WHEAT_BLACK_TEA);
        VirtualFluids.registerBottleStorage(MyFluids.ICED_MELON_JUICE, MyItems.ICED_MELON_JUICE);
        VirtualFluids.registerBottleStorage(MyFluids.MELON_JUICE, ItemsRegistry.MELON_JUICE.get());
        VirtualFluids.registerBottleStorage(MyFluids.THICK_HOT_COCOA, MyItems.THICK_HOT_COCOA);
        VirtualFluids.registerBucketStorage(MyFluids.PUMPKIN_OIL, MyItems.BUCKETED_PUMPKIN_OIL);
        VirtualFluids.registerBowlStorage(MyFluids.MASHED_POTATO, MyItems.MASHED_POTATO);

        IronBowlItem.onInitialize();

        if (!(Blocks.IRON_BARS instanceof IronBarsBlock)) {
            if (FabricLoader.getInstance().isModLoaded("immersive_weathering")) {
                CreateSDelight.LOGGER.info("检测到沉浸风化，已变更铁栏杆的Mixin。");
            } else {
                throw new IllegalStateException("检测到铁栏杆的Mixin未按预期生效！");
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void loadClasses() {
        MyBlocks.PAN.hashCode();
        MyBlockEntityTypes.PAN.hashCode();
        MyItems.ITEM_GROUP.hashCode();
        MyFluids.SUNFLOWER_OIL.hashCode();
        MyRecipeTypes.PAN_FRYING.hashCode();
        MyStatusEffects.SATIATION.hashCode();

        MyArmPointTypes.BASKET.hashCode();
        MySpoutingBehaviours.PAN.hashCode();
    }

    private static void checkCreateVersion() {
        String version = FabricLoader.getInstance().getModContainer("create").orElseThrow().getMetadata().getVersion().toString();
        var matcher = Pattern.compile("0\\.5\\.0\\.([a-z])-([0-9]+)\\+1\\.19\\.2").matcher(version);
        if (!matcher.matches()) {
            CreateSDelight.LOGGER.warn("can't read create version");
        } else {
            char letter = matcher.group(1).charAt(0);
            int build = Integer.parseInt(matcher.group(2));
            if (letter < 'g' || letter == 'g' && build < 817) {
                throw new RuntimeException("Version of Create needs to be at least `0.5.0.g-817+1.19.2`, but it's actually `" + version + "`! 机械动力版本需要至少`0.5.0.g-817+1.19.2`，但是实际为`" + version + "`！");
            }
        }
    }

    @Override
    public void onInitialize() {
        checkCreateVersion();
        RecipeEvents.FINAL.register(recipes -> {
            var recipe = (MillingRecipe) recipes.get(AllRecipeTypes.MILLING.getType()).get(Create.asResource("milling/sunflower"));
            recipe.getRollableResults().add(new ProcessingOutput(new ItemStack(MyItems.SUNFLOWER_KERNELS, 3), 1f));
//            recipe.getFluidResults().add(new FluidStack(MyFluids.SUNFLOWER_OIL, FluidConstants.BOTTLE / 2));
        });
    }
}
