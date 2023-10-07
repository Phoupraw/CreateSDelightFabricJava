package phoupraw.mcmod.createsdelight.misc;

import com.google.common.collect.BiMap;
import com.google.common.collect.ForwardingMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ForwardingBiMap<K, V> extends ForwardingMap<K, V> implements BiMap<K, V> {
    public static <K, V> void biMapPut(Map<K, V> k2v, Map<V, K> v2k, K key, V value) {
        k2v.put(key, value);
        v2k.put(value, key);
    }
    public static <K, V> void biMapRemove(Map<K, V> k2v, Map<V, K> v2k, @Nullable K key, @Nullable V value) {
        if (key == null) {
            key = v2k.get(value);
        }
        if (value == null) {
            value = k2v.remove(key);
        }
        k2v.remove(key);
        v2k.remove(value);
    }
    protected Map<K, V> k2v;
    protected Map<V, K> v2k;
    protected BiMap<V, K> v2kBiMap;
    public ForwardingBiMap(Map<K, V> k2v, Map<V, K> v2k) {
        this.setK2v(k2v);
        this.setV2k(v2k);
    }
    public BiMap<V, K> getV2kBiMap() {
        if (v2kBiMap == null) {
            setV2kBiMap(new ForwardingBiMap<>(getV2k(), getK2v()));
        }
        return v2kBiMap;
    }
    @Override
    protected @NotNull Map<K, V> delegate() {
        return getK2v();
    }
    @Nullable
    @Override
    public V put(K key, V value) {
        getV2k().put(value, key);
        return super.put(key, value);
    }
    @Override
    public V remove(Object key) {
        getV2k().remove(get(key));
        return super.remove(key);
    }
    @Nullable
    @Override
    public V forcePut(K key, V value) {
        remove(key);
        return put(key, value);
    }
    @Override
    public void putAll(@NotNull Map<? extends K, ? extends V> map) {
        for (var entry : map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }
    @Override
    public void clear() {
        getV2k().clear();
        super.clear();
    }
    @NotNull
    @Override
    public Set<K> keySet() {
        return super.keySet();//TODO
    }
    @Override
    public Set<V> values() {
        return getV2k().keySet();//TODO
    }
    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return super.entrySet();//TODO
    }
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return BiMap.super.getOrDefault(key, defaultValue);
    }
    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        BiMap.super.forEach(action);
    }
    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        BiMap.super.replaceAll(function);
    }
    @Nullable
    @Override
    public V putIfAbsent(K key, V value) {
        return BiMap.super.putIfAbsent(key, value);
    }
    @Override
    public boolean remove(Object key, Object value) {
        return BiMap.super.remove(key, value);
    }
    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return BiMap.super.replace(key, oldValue, newValue);
    }
    @Nullable
    @Override
    public V replace(K key, V value) {
        return BiMap.super.replace(key, value);
    }
    @Override
    public V computeIfAbsent(K key, @NotNull Function<? super K, ? extends V> mappingFunction) {
        return BiMap.super.computeIfAbsent(key, mappingFunction);
    }
    @Override
    public @Nullable V computeIfPresent(K key, @NotNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return BiMap.super.computeIfPresent(key, remappingFunction);
    }
    @Override
    public V compute(K key, @NotNull BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return BiMap.super.compute(key, remappingFunction);
    }
    @Override
    public V merge(K key, @NotNull V value, @NotNull BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return BiMap.super.merge(key, value, remappingFunction);
    }
    @Override
    public @NotNull BiMap<V, K> inverse() {
        return getV2kBiMap();
    }
    public void setV2kBiMap(BiMap<V, K> inverse) {
        this.v2kBiMap = inverse;
    }
    public Map<V, K> getV2k() {
        return v2k;
    }
    public void setV2k(Map<V, K> v2k) {
        this.v2k = v2k;
    }
    public Map<K, V> getK2v() {
        return k2v;
    }
    public void setK2v(Map<K, V> k2v) {
        this.k2v = k2v;
    }
}
