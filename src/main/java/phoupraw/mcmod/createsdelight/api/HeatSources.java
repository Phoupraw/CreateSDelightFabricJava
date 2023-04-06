package phoupraw.mcmod.createsdelight.api;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.fluids.tank.BoilerHeaters;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.common.api.WorldBlockApiCache;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.block.entity.SmartDrainBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public final class HeatSources {
    public static final BlockApiLookup<Double, @Nullable Direction> SIDED = BlockApiLookup.get(new Identifier(CreateSDelight.MOD_ID, "heat_source"), Double.class, Direction.class);
    public static final LoadingCache<ServerWorld, WorldBlockApiCache<Double, @Nullable Direction>> CACHE = CacheBuilder.newBuilder().build(new CacheLoader<>() {
        @Override
        public @NotNull WorldBlockApiCache<Double, Direction> load(@NotNull ServerWorld key) {
            return WorldBlockApiCache.of(key, SIDED);
        }
    });

    public static double find(World world, BlockPos pos, @Nullable Direction context) {
        return find(world, pos, null, null, context);
    }

    public static double find(World world, BlockPos pos, @Nullable BlockState state, @Nullable BlockEntity blockEntity, @Nullable Direction context) {
        var heat = SIDED.find(world, pos, state, blockEntity, context);
        return heat == null ? 0 : heat;
    }

    static {
        SIDED.registerFallback((world, pos, state, blockEntity, side) -> {
            if (side != Direction.UP) return null;
            float boiler = BoilerHeaters.getActiveHeat(world, pos, state);
            return boiler >= 0 ? boiler + 1.0 : null;
        });
        SIDED.registerFallback((world, pos, state, blockEntity, side) -> side == Direction.UP && (state.isIn(AllTags.AllBlockTags.PASSIVE_BOILER_HEATERS.tag) || state.isIn(TagsRegistry.HEAT_SOURCES)) ? 1.0 : null);
        SIDED.registerForBlockEntity(SmartDrainBlockEntity::getSelfHeat, MyBlockEntityTypes.SMART_DRAIN);
    }
    private HeatSources() {}
}
