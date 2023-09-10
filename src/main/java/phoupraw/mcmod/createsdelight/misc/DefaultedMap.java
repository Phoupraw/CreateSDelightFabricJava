package phoupraw.mcmod.createsdelight.misc;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class DefaultedMap<K, V> extends ConstForwardingMap<K, V> {
    public DefaultedMap(Map<K, V> delegate) {
        super(delegate);
    }
    @Override
    public @NotNull V get(Object key) {
        return getOrPut(key, newValue(key));
    }
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return getOrPut(key, defaultValue);
    }
    public V getOrPut(Object key, V value) {
        V v = super.get(key);
        if (v == null) {
            v = value;
            put((K) key, v);
        }
        return v;
    }
    public abstract @NotNull V newValue(Object key);
}
