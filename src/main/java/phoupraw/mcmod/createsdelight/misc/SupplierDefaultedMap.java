package phoupraw.mcmod.createsdelight.misc;

import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public class SupplierDefaultedMap<K, V> extends ConstForwardingDefaultedMap<K, V> {
    public static <K extends Enum<K>, V> Supplier<EnumMap<K, V>> newingEnumMap(Class<K> enumClass) {
        return () -> new EnumMap<>(enumClass);
    }
    public final Supplier<? extends V> supplier;
    public SupplierDefaultedMap(Map<K, V> delegate, Supplier<? extends V> supplier) {
        super(delegate);
        this.supplier = supplier;
    }
    @Override
    public V makeValue(@Nullable Object key) {
        return supplier.get();
    }
}
