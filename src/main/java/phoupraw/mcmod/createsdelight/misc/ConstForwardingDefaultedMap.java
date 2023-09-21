package phoupraw.mcmod.createsdelight.misc;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class ConstForwardingDefaultedMap<K, V> extends ConstForwardingMap<K, V> implements DefaultedMap<K, V> {
    public ConstForwardingDefaultedMap(Map<K, V> delegate) {
        super(delegate);
    }
    @Override
    public @NotNull V get(Object key) {
        V v = delegate().get(key);
        if (v == null) {
            v = makeValue(key);
            //noinspection unchecked
            put((K) key, v);
        }
        return v;
    }
    public abstract @NotNull V makeValue(Object key);
}
