package phoupraw.mcmod.createsdelight.misc;

import java.util.Map;
import java.util.function.Function;

public class FunctionDefaultedMap<K, V> extends ConstForwardingDefaultedMap<K, V> {
    public final Function<? super K, ? extends V> function;
    public FunctionDefaultedMap(Map<K, V> delegate, Function<? super K, ? extends V> function) {
        super(delegate);
        this.function = function;
    }
    @Override
    public V makeValue(Object key) {
        return function.apply((K) key);
    }
}
