package phoupraw.mcmod.createsdelight;

import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.misc.VirtualFluids;
import phoupraw.mcmod.createsdelight.registry.*;

public final class CreateSDelight implements ModInitializer {
    public static final String MOD_ID = "createsdelight";
    @ApiStatus.Internal
    public static final Logger LOGGER = LogManager.getLogger();
    /**
     用mixin在{@link Create#onInitialize()}的返回处调用此方法，保证在机械动力之后初始化。
     */
    @ApiStatus.Internal
    public static void afterCreateInit() {
        loadClasses();
        CSDCommands.register();
        VirtualFluids.registerBucketStorage(CSDFluids.EGG_LIQUID, CSDItems.BUCKETED_EGG_LIQUID);
        BlockStressDefaults.setDefaultImpact(CSDIdentifiers.CAKE_OVEN, 16);
        BlockStressDefaults.setDefaultImpact(CSDIdentifiers.VOXEL_MAKER, 16);
        Registry.register(CSDRegistries.PREDEFINED_CAKE, CSDIdentifiers.of("empty"), VoxelCake.empty());
    }
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void loadClasses() {
        //原版
        CSDBlocks.PRINTED_CAKE.hashCode();
        CSDBlockEntityTypes.PRINTED_CAKE.hashCode();
        CSDItems.ITEM_GROUP.hashCode();
        CSDStatusEffects.SATIATION.hashCode();

        //我
        CSDCakeIngredients.CREAM_BLOCK.hashCode();
    }
    /**
     @see #afterCreateInit()
     */
    @Override
    public void onInitialize() {

    }
}
