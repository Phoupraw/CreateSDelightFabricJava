package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.foundation.block.BlockStressDefaults;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.EmptyItemFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.FullItemFluidStorage;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Items;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.createsdelight.CreateSDelight;

import java.util.regex.Pattern;
public class MyModInitializer implements ModInitializer {
    @ApiStatus.Internal
    public static void initializeAfterCreate() {
        loadClasses();
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.SPRINKLER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.VERTICAL_CUTTER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.PRESSURE_COOKER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.MINCER, 1);
        FluidStorage.combinedItemApiProvider(Items.BOWL).register(context -> new EmptyItemFluidStorage(context, MyItems.VEGETABLE_BIG_STEW, MyFluids.VEGETABLE_BIG_STEW, FluidConstants.BUCKET / 4));
        FluidStorage.combinedItemApiProvider(MyItems.VEGETABLE_BIG_STEW).register(context -> new FullItemFluidStorage(context, Items.BOWL, FluidVariant.of(MyFluids.VEGETABLE_BIG_STEW), FluidConstants.BUCKET / 4));
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
