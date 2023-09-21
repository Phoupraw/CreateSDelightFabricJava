package phoupraw.mcmod.createsdelight.misc;

import com.google.common.collect.HashBasedTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public interface DefaultedMap<K, V> extends Map<K, V> {
    /**
     {@code CacheBuilder.newBuilder().build(CacheLoader.from(cacheLoader))}
     */
    static <K, V> DefaultedMap<@NotNull K, V> loadingCache(Function<? super K, ? extends V> cacheLoader) {
        return new FunctionDefaultedMap<>(new WeakHashMap<>(), cacheLoader);
    }
    /**
     {@code MultimapBuilder.hashKeys().arrayListValues().build()}
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
    static <E> Map<E, Integer> hashMultiset() {
        return new ConstDefaultedMap<>(new HashMap<>(), 0);
    }
    /**
     @throws ClassCastException if the class of the specified key or value prevents it from being stored in this map
     */
    @Deprecated
    V getOrPut(Object key, V value);
    @Override
    V get(Object key);
}
