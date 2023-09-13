package phoupraw.mcmod.createsdelight.misc;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class ConstForwardingDefaultedMap<K, V> extends ConstForwardingMap<K, V> implements DefaultedMap<K, V> {
    public ConstForwardingDefaultedMap(Map<K, V> delegate) {
        super(delegate);
    }
    @Override
    public @NotNull V get(Object key) {
        return getOrPut(key, makeValue(key));
    }
    @Override
    public @NotNull V getOrPut(Object key, V value) {
        V v = delegate().get(key);
        if (v == null) {
            v = value;
            put((K) key, v);
        }
        return v;
    }
    public abstract @NotNull V makeValue(Object key);
}
