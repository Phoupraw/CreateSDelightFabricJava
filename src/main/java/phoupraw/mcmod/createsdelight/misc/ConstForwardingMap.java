package phoupraw.mcmod.createsdelight.misc;

import com.google.common.collect.ForwardingMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class ConstForwardingMap<K, V> extends ForwardingMap<K, V> {
    private final Map<K, V> delegate;
    public ConstForwardingMap(Map<K, V> delegate) {this.delegate = delegate;}
    @Override
    public @NotNull Map<K, V> delegate() {
        return delegate;
    }
}
