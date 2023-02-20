package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.foundation.block.BlockStressDefaults;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.base.EmptyItemFluidStorage;
import net.minecraft.item.Items;
import org.jetbrains.annotations.ApiStatus;
public class MyModInitializer implements ModInitializer {
    @ApiStatus.Internal
    public static void initializeAfterCreate() {
        loadClasses();
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.SPRINKLER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.VERTICAL_CUTTER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.PRESSURE_COOKER, 1);
        FluidStorage.combinedItemApiProvider(Items.BOWL).register(context -> new EmptyItemFluidStorage(context, MyItems.VEGETABLE_BIG_STEW, MyFluids.VEGETABLE_BIG_STEW, FluidConstants.BUCKET / 4));
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
//RegistryEntryAddedCallback.event(Registry.BLOCK).register((rawId, id, object) -> {
//    if (id.equals(AllBlocks.ITEM_DRAIN.getId())) {
//        MyBlocks.SMART_DRAIN = new SmartDrainBlock(FabricBlockSettings.copyOf(AllBlocks.ITEM_DRAIN.get()));
//    }
//});
    }
}
