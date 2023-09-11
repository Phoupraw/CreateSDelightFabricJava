package phoupraw.mcmod.createsdelight.misc;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Function;

public interface DefaultedMap<K, V> extends Map<K, V> {
    static <K, V> DefaultedMap<K, V> loadingCache(Function<? super K, ? extends V> cacheLoader) {
        return new FunctionDefaultedMap<>(new WeakHashMap<>(), cacheLoader);
    }
    static <K, V> DefaultedMap<K, List<V>> arrayListHashMultimap() {
        return new SupplierDefaultedMap<>(new HashMap<>(), ArrayList::new);
    }
    static <R, C, V> DefaultedMap<R, Map<C, V>> hashBasedTable() {
        return new SupplierDefaultedMap<>(new HashMap<>(), HashMap::new);
    }
    /**
     * @throws ClassCastException if the class of the specified key or value prevents it from being stored in this map
     */
    @NotNull V getOrPut(Object key, V value);
    @Override
    @NotNull V get(Object key);
}
