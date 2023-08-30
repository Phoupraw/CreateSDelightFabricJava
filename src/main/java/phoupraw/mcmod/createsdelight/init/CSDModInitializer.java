package phoupraw.mcmod.createsdelight.init;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.misc.VirtualFluids;
import phoupraw.mcmod.createsdelight.registry.*;

import java.util.regex.Pattern;

/**
 * 模组构建和发布流程（每次修改代码后都需要从头开始，切不可跳步骤）：
 * <ol border="1">
 * <li>在开发环境运行客户端，进入世界，简单随意游玩，确保不会崩溃。</li>
 * <li>在开发环境运行专用服务端，确保不会崩溃。</li>
 * <li>运行{@code publishToMavenLocal}或{@code publish} Gradle任务，如果报错，一般是Mixin的remap错误，请根据情况把报错的mixin的remap设为{@code false}。<br/></li>
 * <li>在生产环境运行客户端，进入世界，简单随意游玩，确保不会崩溃。</li>
 * <li>在{@code gradle.properties}里把版本号加一，并提交git。</li>
 * <li>在<a href="https://modrinth.com/mod/creates-delight/versions">Modrinth</a>和<a href="https://beta.curseforge.com/minecraft/mc-mods/creates-delight/files">Curseforge</a>上发布新版本。</li>
 * </ol>
 */

public final class CSDModInitializer implements ModInitializer {

    public static void initializeAfterCreate() {

        //BlockStressDefaults.setDefaultImpact(CDIdentifiers.SPRINKLER, 1);
        //BlockStressDefaults.setDefaultImpact(CDIdentifiers.VERTICAL_CUTTER, 1);
        //BlockStressDefaults.setDefaultImpact(CDIdentifiers.PRESSURE_COOKER, 1);
        //BlockStressDefaults.setDefaultImpact(CDIdentifiers.MINCER, 1);
        //BlockStressDefaults.setDefaultImpact(CDIdentifiers.SKEWER, 1);
        //BlockStressDefaults.setDefaultImpact(CDIdentifiers.IRON_BAR_SKEWER, 1);

        //VirtualFluids.registerAttributeHandler(VirtualFluids.ATTRIBUTE_HANDLER, CDFluids.SUNFLOWER_OIL, /*CDFluids.VEGETABLE_BIG_STEW,*//* CDFluids.ROSE_MILK_TEA,*/ CDFluids.BEETROOT_SOUP, CDFluids.TOMATO_SAUCE,/* CDFluids.POPPY_RUSSIAN_SOUP,*/ CDFluids.EGG_LIQUID, /*CDFluids.WHEAT_BLACK_TEA, CDFluids.ICED_MELON_JUICE,*/ CDFluids.MELON_JUICE,/* CDFluids.THICK_HOT_COCOA,*/ CDFluids.PASTE, CDFluids.PUMPKIN_OIL, CDFluids.APPLE_PASTE, /*CDFluids.MASHED_POTATO,*/ CDFluids.CHOCOLATE_PASTE);
        //VirtualFluids.registerBowlStorage(CDFluids.VEGETABLE_BIG_STEW, CDItems.VEGETABLE_BIG_STEW);
        //VirtualFluids.registerBucketStorage(CDFluids.SUNFLOWER_OIL, CDItems.BUCKETED_SUNFLOWER_OIL);
        //VirtualFluids.registerBottleStorage(CDFluids.SUNFLOWER_OIL, CDItems.BOTTLED_SUNFLOWER_OIL);
        //VirtualFluids.registerBottleStorage(CDFluids.ROSE_MILK_TEA, CDItems.ROSE_MILK_TEA);
        //VirtualFluids.registerBowlStorage(CDFluids.BEETROOT_SOUP, Items.BEETROOT_SOUP);
        //VirtualFluids.registerBowlStorage(CDFluids.TOMATO_SAUCE, ItemsRegistry.TOMATO_SAUCE.get());
        //VirtualFluids.registerBowlStorage(CDFluids.POPPY_RUSSIAN_SOUP, CDItems.POPPY_RUSSIAN_SOUP);
        //VirtualFluids.registerBowlStorage(CDFluids.POPPY_RUSSIAN_SOUP, CDItems.POPPY_RUSSIAN_SOUP);
        //VirtualFluids.registerBottleStorage(CDFluids.WHEAT_BLACK_TEA, CDItems.WHEAT_BLACK_TEA);
        //VirtualFluids.registerBottleStorage(CDFluids.ICED_MELON_JUICE, CDItems.ICED_MELON_JUICE);
        //VirtualFluids.registerBottleStorage(CDFluids.MELON_JUICE, ItemsRegistry.MELON_JUICE.get());
        //VirtualFluids.registerBottleStorage(CDFluids.THICK_HOT_COCOA, CDItems.THICK_HOT_COCOA);
        //VirtualFluids.registerBucketStorage(CDFluids.PUMPKIN_OIL, CDItems.BUCKETED_PUMPKIN_OIL);
        //VirtualFluids.registerBowlStorage(CDFluids.MASHED_POTATO, CDItems.MASHED_POTATO);

        //IronBowlItem.onInitialize();

        //if (!(Blocks.IRON_BARS instanceof IronBarsBlock)) {
        //    if (FabricLoader.getInstance().isModLoaded("immersive_weathering")) {
        //        CreateSDelight.LOGGER.info("检测到沉浸风化，已变更铁栏杆的Mixin。");
        //    } else {
        //        throw new IllegalStateException("检测到铁栏杆的Mixin未按预期生效！");
        //    }
        //}

        //test
        //        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
        //            @Override
        //            public Identifier getFabricId() {
        //                return ResourceReloadListenerKeys.LOOT_TABLES;
        //            }
        //
        //            @Override
        //            public void reload(ResourceManager manager) {
        //                CreateSDelight.LOGGER.info("3333");
        //            }
        //        });
        //        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
        //            @Override
        //            public Identifier getFabricId() {
        //                return ResourceReloadListenerKeys.SOUNDS;
        //            }
        //
        //            @Override
        //            public void reload(ResourceManager manager) {
        //                CreateSDelight.LOGGER.info("2222");
        //            }
        //        });
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void loadClasses() {
        //原版
        CSDBlocks.PRINTED_CAKE.hashCode();
        CSDBlockEntityTypes.PRINTED_CAKE.hashCode();
        CSDItems.ITEM_GROUP.hashCode();
        //CDFluids.SUNFLOWER_OIL.hashCode();
        //CDRecipeTypes.PAN_FRYING.hashCode();
        CSDStatusEffects.SATIATION.hashCode();

        //机械
        //CDArmPointTypes.BASKET.hashCode();
        //CDSpoutingBehaviours.PAN.hashCode();

        //我
        CSDCakeIngredients.MILK.hashCode();
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
        //checkCreateVersion();
        //RecipeEvents.FINAL.register(recipes -> {
        //    var recipe = (MillingRecipe) recipes.get(AllRecipeTypes.MILLING.getType()).get(Create.asResource("milling/sunflower"));
        //    recipe.getRollableResults().add(new ProcessingOutput(new ItemStack(CDItems.SUNFLOWER_KERNELS, 3), 1f));
        //    //            recipe.getFluidResults().add(new FluidStack(MyFluids.SUNFLOWER_OIL, FluidConstants.BOTTLE / 2));
        //});
        CDCommands.register();
        loadClasses();
        VirtualFluids.registerBucketStorage(CSDFluids.EGG_LIQUID, CSDItems.BUCKETED_EGG_LIQUID);
        //FluidVariantAttributes.register(CSDFluids.EGG_LIQUID,VirtualFluids.ATTRIBUTE_HANDLER);
    }

}
