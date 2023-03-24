package phoupraw.mcmod.createsdelight.registry;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.simibubi.create.foundation.block.BlockStressDefaults;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.common.api.Lambdas;
import phoupraw.mcmod.common.api.VirtualFluids;
import phoupraw.mcmod.createsdelight.CreateSDelight;

import java.util.regex.Pattern;
@ApiStatus.Internal
public final class MyModInitializer implements ModInitializer {
    public static void initializeAfterCreate() {
        loadClasses();
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.SPRINKLER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.VERTICAL_CUTTER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.PRESSURE_COOKER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.MINCER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.SKEWER, 1);
        VirtualFluids.registerAttributeHandler(VirtualFluids.ATTRIBUTE_HANDLER, MyFluids.SUNFLOWER_OIL, MyFluids.VEGETABLE_BIG_STEW, MyFluids.ROSE_MILK_TEA, MyFluids.BEETROOT_SOUP, MyFluids.TOMATO_SAUCE, MyFluids.POPPY_RUSSIAN_SOUP, MyFluids.EGG_LIQUID, MyFluids.WHEAT_BLACK_TEA, MyFluids.ICED_MELON_JUICE, MyFluids.MELON_JUICE, MyFluids.THICK_HOT_COCOA, MyFluids.PASTE);
        VirtualFluids.registerBowlStorage(MyFluids.VEGETABLE_BIG_STEW, MyItems.VEGETABLE_BIG_STEW);
        VirtualFluids.registerBucketStorage(MyFluids.SUNFLOWER_OIL, MyItems.BUCKETED_SUNFLOWER_OIL);
        VirtualFluids.registerBottleStorage(MyFluids.SUNFLOWER_OIL, MyItems.BOTTLED_SUNFLOWER_OIL);
        VirtualFluids.registerBottleStorage(MyFluids.ROSE_MILK_TEA, MyItems.ROSE_MILK_TEA);
        VirtualFluids.registerBowlStorage(MyFluids.BEETROOT_SOUP, Items.BEETROOT_SOUP);
        VirtualFluids.registerBowlStorage(MyFluids.TOMATO_SAUCE, ItemsRegistry.TOMATO_SAUCE.get());
        VirtualFluids.registerBowlStorage(MyFluids.POPPY_RUSSIAN_SOUP, MyItems.POPPY_RUSSIAN_SOUP);
        FluidStorage.combinedItemApiProvider(Items.EGG).register(Lambdas.fullProviderOf(MyItems.EGG_SHELL, FluidVariant.of(MyFluids.EGG_LIQUID), FluidConstants.BOTTLE));
        VirtualFluids.registerBowlStorage(MyFluids.POPPY_RUSSIAN_SOUP, MyItems.POPPY_RUSSIAN_SOUP);
        VirtualFluids.registerBottleStorage(MyFluids.WHEAT_BLACK_TEA, MyItems.WHEAT_BLACK_TEA);
        VirtualFluids.registerBottleStorage(MyFluids.ICED_MELON_JUICE, MyItems.ICED_MELON_JUICE);
        VirtualFluids.registerBottleStorage(MyFluids.MELON_JUICE, ItemsRegistry.MELON_JUICE.get());
        VirtualFluids.registerBottleStorage(MyFluids.THICK_HOT_COCOA, MyItems.THICK_HOT_COCOA);
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

    @Override
    public void onInitialize() {
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
//
//        ServerLifecycleEvents.SERVER_STOPPED.register(new ServerLifecycleEvents.ServerStopped() {
//            @Override
//            public void onServerStopped(MinecraftServer server) {
//                var path = server.getSavePath(WorldSavePath.ROOT);
//                for (ServerWorld world : server.getWorlds()) {
//                    world.save();
//                }
//            }
//        });
    }
}
