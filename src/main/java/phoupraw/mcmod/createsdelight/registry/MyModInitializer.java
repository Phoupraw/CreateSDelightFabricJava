package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.foundation.block.BlockStressDefaults;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.common.fluid.VirtualFluid;
import phoupraw.mcmod.createsdelight.CreateSDelight;

import java.util.regex.Pattern;

import static phoupraw.mcmod.common.misc.Lambdas.emptyProviderOf;
import static phoupraw.mcmod.common.misc.Lambdas.fullProviderOf;
public class MyModInitializer implements ModInitializer {
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
            if (letter < 'g' || letter == 'g' && build < 851) {
                throw new RuntimeException("Version of Create needs to be at least `0.5.0.g-851+1.19.2`, but it's actually `" + version + "`! 机械动力版本需要至少`0.5.0.g-851+1.19.2`，但是实际为`" + version + "`！");
            }
        }
    }
}
