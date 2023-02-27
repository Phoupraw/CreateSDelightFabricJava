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
import phoupraw.mcmod.common.fluid.VirtualFluid;
import phoupraw.mcmod.createsdelight.CreateSDelight;

import java.util.regex.Pattern;

import static phoupraw.mcmod.common.fluid.VirtualFluid.*;
import static phoupraw.mcmod.common.misc.Lambdas.fullProviderOf;
public class MyModInitializer implements ModInitializer {
    @ApiStatus.Internal
    public static void initializeAfterCreate() {
        loadClasses();
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.SPRINKLER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.VERTICAL_CUTTER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.PRESSURE_COOKER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.MINCER, 1);
        register(VirtualFluid.ATTRIBUTE_HANDLER, MyFluids.SUNFLOWER_OIL, MyFluids.VEGETABLE_BIG_STEW, MyFluids.ROSE_MILK_TEA, MyFluids.BEETROOT_SOUP, MyFluids.TOMATO_SAUCE, MyFluids.POPPY_RUSSIAN_SOUP, MyFluids.EGG_LIQUID, MyFluids.WHEAT_BLACK_TEA, MyFluids.ICED_MELON_JUICE, MyFluids.MELON_JUICE, MyFluids.THICK_HOT_COCOA);
        registerBowlStorage(MyFluids.VEGETABLE_BIG_STEW, MyItems.VEGETABLE_BIG_STEW);
        registerBucketStorage(MyFluids.SUNFLOWER_OIL, MyItems.BUCKETED_SUNFLOWER_OIL);
        registerBottleStorage(MyFluids.SUNFLOWER_OIL, MyItems.BOTTLED_SUNFLOWER_OIL);
        registerBottleStorage(MyFluids.ROSE_MILK_TEA, MyItems.ROSE_MILK_TEA);
        registerBowlStorage(MyFluids.BEETROOT_SOUP, Items.BEETROOT_SOUP);
        registerBowlStorage(MyFluids.TOMATO_SAUCE, ItemsRegistry.TOMATO_SAUCE.get());
        registerBowlStorage(MyFluids.POPPY_RUSSIAN_SOUP, MyItems.POPPY_RUSSIAN_SOUP);
        FluidStorage.combinedItemApiProvider(Items.EGG).register(fullProviderOf(MyItems.EGG_SHELL, FluidVariant.of(MyFluids.EGG_LIQUID), FluidConstants.BOTTLE));
        registerBowlStorage(MyFluids.POPPY_RUSSIAN_SOUP, MyItems.POPPY_RUSSIAN_SOUP);
        registerBottleStorage(MyFluids.WHEAT_BLACK_TEA, MyItems.WHEAT_BLACK_TEA);
        registerBottleStorage(MyFluids.ICED_MELON_JUICE, MyItems.ICED_MELON_JUICE);
        registerBottleStorage(MyFluids.MELON_JUICE, ItemsRegistry.MELON_JUICE.get());
        registerBottleStorage(MyFluids.THICK_HOT_COCOA, MyItems.THICK_HOT_COCOA);
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
    }
}
