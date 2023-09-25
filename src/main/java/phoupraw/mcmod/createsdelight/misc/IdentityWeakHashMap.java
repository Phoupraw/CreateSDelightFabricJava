package phoupraw.mcmod.createsdelight.misc;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class IdentityWeakHashMap<K, V> extends AbstractMap<K, V> {
    public final Map<StrongRef<K>, V> backMap = new WeakHashMap<>();
    @Override
    public int size() {
        return backMap.size();
    }
    @Override
    public boolean isEmpty() {
        return backMap.isEmpty();
    }
    @Override
    public boolean containsKey(Object key) {
        return backMap.containsKey(new StrongRef<>(key));
    }
    @Override
    public boolean containsValue(Object value) {
        return backMap.containsValue(value);
    }
    @Override
    public V get(Object key) {
        return backMap.get(new StrongRef<>(key));
    }
    @Nullable
    @Override
    public V put(K key, V value) {
        return backMap.put(new StrongRef<>(key), value);
    }
    @Override
    public V remove(Object key) {
        return backMap.remove(new StrongRef<>(key));
    }
    @Override
    public void clear() {
        backMap.clear();
    }
    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new AbstractSet<>() {
            @Override
            public Iterator<Entry<K, V>> iterator() {
                Iterator<Entry<StrongRef<K>, V>> backIter = backMap.entrySet().iterator();
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return backIter.hasNext();
                    }
                    @Override
                    public Entry<K, V> next() {
                        return new Entry<>() {
                            final Entry<StrongRef<K>, V> backEntry = backIter.next();
                            @Override
                            public K getKey() {
                                return backEntry.getKey().obj();
                            }
                            @Override
                            public V getValue() {
                                return backEntry.getValue();
                            }
                            @Override
                            public V setValue(V value) {
                                return backEntry.setValue(value);
                            }
                        };
                    }
                    @Override
                    public void remove() {
                        backIter.remove();
                    }
                };
            }
            @Override
            public int size() {
                return backMap.size();
            }
        };
    }
    public record StrongRef<K>(K obj) {
        @Override
        public boolean equals(Object o) {
            return o instanceof IdentityWeakHashMap.StrongRef<?> that && this.obj == that.obj;
        }
        @Override
        public int hashCode() {
            return System.identityHashCode(obj);
        }
    }
}
