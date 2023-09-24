package phoupraw.mcmod.createsdelight.misc;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.MultimapBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public interface DefaultedMap<K, V> extends Map<K, V> {
    /**
     {@code CacheBuilder.newBuilder().build(CacheLoader.from(cacheLoader))}
     @see CacheBuilder#newBuilder()
     @see CacheBuilder#build(CacheLoader)
     */
    static <K, V> DefaultedMap<K, V> loadingCache(Function<? super K, ? extends V> cacheLoader) {
        return new FunctionDefaultedMap<>(new WeakHashMap<>(), cacheLoader);
    }
    /**
     {@code MultimapBuilder.hashKeys().arrayListValues().build()}
     @see MultimapBuilder#hashKeys()
     @see MultimapBuilder.MultimapBuilderWithKeys#arrayListValues()
     @see MultimapBuilder.ListMultimapBuilder#build()
     */
    static <K, V> DefaultedMap<@Nullable K, List<V>> arrayListHashMultimap() {
        return new SupplierDefaultedMap<>(new HashMap<>(), ArrayList::new);
    }
    /**
     @see HashBasedTable#create()
     */
    static <R, C, V> DefaultedMap<@Nullable R, Map<C, V>> hashBasedTable() {
        return new SupplierDefaultedMap<>(new HashMap<>(), HashMap::new);
    }
    /**
     @see HashMultiset#create()
     */
    static <E> Map<E, Integer> hashMultiset() {
        return new ConstDefaultedMap<>(new HashMap<>(), 0);
    }
    @Override
    @NotNull V get(Object key);
}
