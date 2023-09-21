package phoupraw.mcmod.createsdelight.misc;

import java.util.Map;

public abstract class ConstForwardingDefaultedMap<K, V> extends ConstForwardingMap<K, V> implements DefaultedMap<K, V> {
    public ConstForwardingDefaultedMap(Map<K, V> delegate) {
        super(delegate);
    }
    @Override
    public V get(Object key) {
        V v = delegate().get(key);
        if (v == null) {
            v = makeValue(key);
            put((K) key, v);
        }
        return v;
    }
    @Deprecated
    @Override
    public V getOrPut(Object key, V value) {
        V v = delegate().get(key);
        if (v == null) {
            v = value;
            put((K) key, v);
        }
        return v;
    }
    public abstract V makeValue(Object key);
}
