package phoupraw.mcmod.createsdelight.misc;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.*;

public class IdentityWeakHashMap<K, V> extends AbstractMap<K, V> {
    public final Map<Reference<K>, V> backMap = new HashMap<>();
    public final ReferenceQueue<Object> refQueue = new ReferenceQueue<>();
    public int gc = 0;
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
        return backMap.containsKey(new IdentityWeakReference<>(key, refQueue));
    }
    @Override
    public boolean containsValue(Object value) {
        return backMap.containsValue(value);
    }
    @Override
    public V get(Object key) {
        return backMap.get(new IdentityWeakReference<>(key, refQueue));
    }
    @Nullable
    @Override
    public V put(K key, V value) {
        while (refQueue.poll() != null) {
            gc++;
        }
        if (gc > size() / 2) {
            Map<K, V> temp = new HashMap<>(this);
            this.clear();
            this.putAll(temp);
        }
        return backMap.put(new IdentityWeakReference<>(key, refQueue), value);
    }
    @Override
    public V remove(Object key) {
        return backMap.remove(new IdentityWeakReference<>(key, refQueue));
    }
    @Override
    public void clear() {
        backMap.clear();
        //noinspection StatementWithEmptyBody
        while (refQueue.poll() != null) ;
        gc = 0;
    }
    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        return new AbstractSet<>() {
            @Override
            public Iterator<Entry<K, V>> iterator() {
                return new Iterator<>() {
                    final Iterator<Entry<Reference<K>, V>> backIter = backMap.entrySet().iterator();
                    @Nullable Entry<K, V> theNext;
                    @Override
                    public boolean hasNext() {
                        if (theNext != null) return true;
                        theNext = next0();
                        return theNext != null && theNext.getKey() != null;
                    }
                    @Override
                    public Entry<K, V> next() {
                        return next0();
                    }
                    @Override
                    public void remove() {
                        backIter.remove();
                    }
                    private Entry<K, V> next0() {
                        if (theNext != null) {
                            var theNext = this.theNext;
                            this.theNext = null;
                            return theNext;
                        }
                        return new Entry<>() {
                            Entry<Reference<K>, V> backEntry;
                            K key;
                            {
                                while (backIter.hasNext()) {
                                    backEntry = backIter.next();
                                    K key = backEntry.getKey().get();
                                    if (key != null) {
                                        this.key = key;
                                        break;
                                    }
                                }
                            }
                            @Override
                            public K getKey() {
                                return key;
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
                };
            }
            @Override
            public int size() {
                return backMap.size();
            }
            @Override
            public boolean contains(Object o) {
                return o instanceof Map.Entry<?, ?> entry && containsKey(entry.getKey()) && containsValue(entry.getValue());
            }
            @Override
            public boolean add(Entry<K, V> entry) {
                return !Objects.equals(entry.getValue(), put(entry.getKey(), entry.getValue()));
            }
            @Override
            public boolean remove(Object o) {
                return o instanceof Map.Entry<?, ?> entry && IdentityWeakHashMap.this.remove(entry.getKey(), entry.getValue());
            }
        };
    }
}
