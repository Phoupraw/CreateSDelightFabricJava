package phoupraw.mcmod.createsdelight.api;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiCache;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
public  class WorldBlockApiCacheImpl<A, C> implements WorldBlockApiCache<A, C> {
    private final @NotNull ServerWorld world;
    private final BlockApiLookup<A, C> lookup;
    private final LoadingCache<BlockPos, BlockApiCache<A, C>> worldCache = CacheBuilder.newBuilder().build(new CacheLoader<>() {
        @Override
        public @NotNull BlockApiCache<A, C> load(@NotNull BlockPos key) {
            return BlockApiCache.create(getLookup(), getWorld(), key);
        }
    });

    public WorldBlockApiCacheImpl(@NotNull ServerWorld world, BlockApiLookup<A, C> lookup) {
        this.world = world;
        this.lookup = lookup;
    }

    @Override
    public BlockApiLookup<A, C> getLookup() {
        return lookup;
    }

    @Override
    public @NotNull ServerWorld getWorld() {
        return world;
    }

    @Override
    public LoadingCache<BlockPos, BlockApiCache<A, C>> getWorldCache() {
        return worldCache;
    }
}
