package phoupraw.mcmod.createsdelight.registry;

import com.nhoryzon.mc.farmersdelight.registry.ItemsRegistry;
import com.simibubi.create.foundation.block.BlockStressDefaults;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.fluid.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.common.fluid.VirtualFluid;
import phoupraw.mcmod.createsdelight.CreateSDelight;

import java.util.regex.Pattern;

import static phoupraw.mcmod.common.misc.Lambdas.emptyProviderOf;
import static phoupraw.mcmod.common.misc.Lambdas.fullProviderOf;
public class MyModInitializer implements ModInitializer {
    public static void register(FluidVariantAttributeHandler attributeHandler, Fluid... fluids) {
        for (Fluid fluid : fluids) FluidVariantAttributes.register(fluid, attributeHandler);
    }

    public static void registerBucket(Fluid fluid, Item fullItem) {
        registerStorage(Items.BUCKET, fullItem, fluid, FluidConstants.BUCKET);
    }

    public static void registerBottle(Fluid fluid, Item fullItem) {
        registerStorage(Items.GLASS_BOTTLE, fullItem, fluid, FluidConstants.BOTTLE);
    }

    public static void registerBowl(Fluid fluid, Item fullItem) {
        registerStorage(Items.BOWL, fullItem, fluid, FluidConstants.BUCKET / 4);
    }

    public static void registerStorage(Item emptyItem, Item fullItem, Fluid fluid, long amount) {
        FluidStorage.combinedItemApiProvider(emptyItem).register(emptyProviderOf(fullItem, fluid, amount));
        FluidStorage.combinedItemApiProvider(fullItem).register(fullProviderOf(emptyItem, FluidVariant.of(fluid), amount));
    }

    @ApiStatus.Internal
    public static void initializeAfterCreate() {
        loadClasses();
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.SPRINKLER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.VERTICAL_CUTTER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.PRESSURE_COOKER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.MINCER, 1);
        FluidStorage.combinedItemApiProvider(Items.BOWL).register(emptyProviderOf(MyItems.VEGETABLE_BIG_STEW, MyFluids.VEGETABLE_BIG_STEW, FluidConstants.BUCKET / 4));
        FluidStorage.combinedItemApiProvider(MyItems.VEGETABLE_BIG_STEW).register(fullProviderOf(Items.BOWL, FluidVariant.of(MyFluids.VEGETABLE_BIG_STEW), FluidConstants.BUCKET / 4));
        FluidStorage.combinedItemApiProvider(Items.BUCKET).register(emptyProviderOf(MyItems.BUCKETED_SUNFLOWER_OIL, MyFluids.SUNFLOWER_OIL, FluidConstants.BUCKET));
        FluidStorage.combinedItemApiProvider(MyItems.BUCKETED_SUNFLOWER_OIL).register(fullProviderOf(Items.BUCKET, FluidVariant.of(MyFluids.SUNFLOWER_OIL), FluidConstants.BUCKET));
        FluidStorage.combinedItemApiProvider(Items.GLASS_BOTTLE).register(emptyProviderOf(MyItems.BOTTLED_SUNFLOWER_OIL, MyFluids.SUNFLOWER_OIL, FluidConstants.BOTTLE));
        FluidStorage.combinedItemApiProvider(MyItems.BOTTLED_SUNFLOWER_OIL).register(fullProviderOf(Items.GLASS_BOTTLE, FluidVariant.of(MyFluids.SUNFLOWER_OIL), FluidConstants.BOTTLE));
        FluidVariantAttributes.register(MyFluids.SUNFLOWER_OIL, VirtualFluid.ATTRIBUTE_HANDLER);
        FluidVariantAttributes.register(MyFluids.VEGETABLE_BIG_STEW, VirtualFluid.ATTRIBUTE_HANDLER);
        FluidStorage.combinedItemApiProvider(Items.GLASS_BOTTLE).register(emptyProviderOf(MyItems.ROSE_MILK_TEA, MyFluids.ROSE_MILK_TEA, FluidConstants.BOTTLE));
        FluidStorage.combinedItemApiProvider(MyItems.ROSE_MILK_TEA).register(fullProviderOf(Items.GLASS_BOTTLE, FluidVariant.of(MyFluids.ROSE_MILK_TEA), FluidConstants.BOTTLE));
        FluidVariantAttributes.register(MyFluids.ROSE_MILK_TEA, VirtualFluid.ATTRIBUTE_HANDLER);
        FluidStorage.combinedItemApiProvider(Items.BOWL).register(emptyProviderOf(Items.BEETROOT_SOUP, MyFluids.BEETROOT_SOUP, FluidConstants.BUCKET / 4));
        FluidStorage.combinedItemApiProvider(Items.BEETROOT_SOUP).register(fullProviderOf(Items.BOWL, FluidVariant.of(MyFluids.BEETROOT_SOUP), FluidConstants.BUCKET / 4));
        FluidVariantAttributes.register(MyFluids.BEETROOT_SOUP, VirtualFluid.ATTRIBUTE_HANDLER);
        FluidStorage.combinedItemApiProvider(Items.BOWL).register(emptyProviderOf(ItemsRegistry.TOMATO_SAUCE.get(), MyFluids.TOMATO_SAUCE, FluidConstants.BUCKET / 4));
        FluidStorage.combinedItemApiProvider(ItemsRegistry.TOMATO_SAUCE.get()).register(fullProviderOf(Items.BOWL, FluidVariant.of(MyFluids.TOMATO_SAUCE), FluidConstants.BUCKET / 4));
        FluidVariantAttributes.register(MyFluids.TOMATO_SAUCE, VirtualFluid.ATTRIBUTE_HANDLER);
        registerBowl(MyFluids.POPPY_RUSSIAN_SOUP, MyItems.POPPY_RUSSIAN_SOUP);
        register(VirtualFluid.ATTRIBUTE_HANDLER, MyFluids.POPPY_RUSSIAN_SOUP);
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
