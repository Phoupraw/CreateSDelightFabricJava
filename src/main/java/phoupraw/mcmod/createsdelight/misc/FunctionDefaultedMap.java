package phoupraw.mcmod.createsdelight.misc;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;

public class FunctionDefaultedMap<K, V> extends ForwardingDefaultedMap<K, V> {
    public final Function<? super K, ? extends V> function;
    public FunctionDefaultedMap(Map<K, V> delegate, Function<? super K, ? extends V> function) {
        super(delegate);
        this.function = function;
    }
    @Override
    public @NotNull V makeValue(Object key) {
        return function.apply((K) key);
    }
}
