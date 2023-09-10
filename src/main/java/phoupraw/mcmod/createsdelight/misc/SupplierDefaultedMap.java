package phoupraw.mcmod.createsdelight.misc;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Supplier;

public class SupplierDefaultedMap<K, V> extends DefaultedMap<K, V> {
    public final Supplier<? extends @NotNull V> supplier;
    public SupplierDefaultedMap(Map<K, V> delegate, Supplier<? extends @NotNull V> supplier) {
        super(delegate);
        this.supplier = supplier;
    }
    @Override
    public @NotNull V newValue(@Nullable Object key) {
        return supplier.get();
    }
}
