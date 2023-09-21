package phoupraw.mcmod.createsdelight.misc;

import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Function;

public class FunctionDefaultedMap<K, V> extends ConstForwardingDefaultedMap<K, V> {
    public final Function<? super K, ? extends V> function;
    public FunctionDefaultedMap(Map<K, V> delegate, Function<? super K, ? extends @NotNull V> function) {
        super(delegate);
        this.function = function;
    }
    @Override
    public @NotNull V makeValue(Object key) {
        //noinspection unchecked
        return function.apply((K) key);
    }
}
