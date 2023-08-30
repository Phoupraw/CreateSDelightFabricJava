package phoupraw.mcmod.createsdelight;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.createsdelight.misc.VirtualFluids;
import phoupraw.mcmod.createsdelight.registry.*;

public final class CreateSDelight implements ModInitializer {

    public static final String MOD_ID = "createsdelight";
    @ApiStatus.Internal
    public static final Logger LOGGER = LogManager.getLogger();

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void loadClasses() {
        //原版
        CSDBlocks.PRINTED_CAKE.hashCode();
        CSDBlockEntityTypes.PRINTED_CAKE.hashCode();
        CSDItems.ITEM_GROUP.hashCode();
        CSDStatusEffects.SATIATION.hashCode();

        //我
        CSDCakeIngredients.MILK.hashCode();
    }

    @Override
    public void onInitialize() {
        loadClasses();
        CSDCommands.register();
        VirtualFluids.registerBucketStorage(CSDFluids.EGG_LIQUID, CSDItems.BUCKETED_EGG_LIQUID);
    }

}
