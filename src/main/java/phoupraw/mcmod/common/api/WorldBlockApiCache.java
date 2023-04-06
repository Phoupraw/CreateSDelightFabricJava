package phoupraw.mcmod.common.api;

import com.google.common.cache.LoadingCache;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiCache;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.common.impl.WorldBlockApiCacheImpl;

import java.util.concurrent.ExecutionException;
public interface WorldBlockApiCache<A, C> {
    static <A, C> WorldBlockApiCache<A, C> of(@NotNull ServerWorld world, BlockApiLookup<A, C> lookup) {
        return new WorldBlockApiCacheImpl<>(world, lookup);
    }
    default @Nullable A find(BlockPos pos, C context) {
        return find(pos, null, null, context);
    }
    default @Nullable A find(BlockPos pos, @Nullable BlockState state, @Nullable BlockEntity blockEntity, C context) {
        try {
            return getWorldCache().get(pos).find(state, context);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    BlockApiLookup<A, C> getLookup();
    ServerWorld getWorld();
    LoadingCache<BlockPos, BlockApiCache<A, C>> getWorldCache();
}
