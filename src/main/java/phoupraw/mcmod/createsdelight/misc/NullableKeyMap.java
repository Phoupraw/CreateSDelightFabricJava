package phoupraw.mcmod.createsdelight.misc;

import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class NullableKeyMap<K, V> extends ConstForwardingMap<K, V> {
    public @Nullable V nullKeyValue;
    public boolean containsNullKey;
    public NullableKeyMap(Map<K, V> delegate) {
        super(delegate);
    }
    @Override
    public boolean containsKey(@Nullable Object key) {
        return key == null ? containsNullKey : super.containsKey(key);
    }
    @Override
    public boolean containsValue(@Nullable Object value) {
        return Objects.equals(value, nullKeyValue) || super.containsValue(value);
    }
    @Nullable
    @Override
    public V get(@Nullable Object key) {
        return key == null ? nullKeyValue : super.get(key);
    }
    @Nullable
    @Override
    public V put(K key, V value) {
        if (key == null) {
            V prev = nullKeyValue;
            nullKeyValue = value;
            containsNullKey = true;
            return prev;
        }
        return super.put(key, value);
    }
    @Nullable
    @Override
    public V remove(@Nullable Object key) {
        if (key == null) {
            V prev = nullKeyValue;
            nullKeyValue = null;
            containsNullKey = false;
            return prev;
        }
        return super.remove(key);
    }
    @Override
    public void clear() {
        nullKeyValue = null;
        containsNullKey = false;
        super.clear();
    }
    //TODO
}
