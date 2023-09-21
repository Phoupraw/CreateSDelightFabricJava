package phoupraw.mcmod.createsdelight.misc;

import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;

public class ConstDefaultedMap<K, V> extends ConstForwardingDefaultedMap<K, V> {
    public final @Unmodifiable V defaultValue;
    public ConstDefaultedMap(Map<K, V> delegate, @Unmodifiable V defaultValue) {
        super(delegate);
        this.defaultValue = defaultValue;
    }
    @Override
    public V makeValue(Object key) {
        return defaultValue;
    }
}
