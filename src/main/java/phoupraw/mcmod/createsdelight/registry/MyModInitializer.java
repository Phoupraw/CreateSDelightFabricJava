package phoupraw.mcmod.createsdelight.registry;

import com.simibubi.create.foundation.block.BlockStressDefaults;
import net.fabricmc.api.ModInitializer;
import org.jetbrains.annotations.ApiStatus;
public class MyModInitializer implements ModInitializer {
    @ApiStatus.Internal
    public static void initializeAfterCreate() {
        loadClasses();
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.SPRINKLER, 1);
        BlockStressDefaults.setDefaultImpact(MyIdentifiers.VERTICAL_CUTTER, 1);
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
