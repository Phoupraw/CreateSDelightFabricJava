package phoupraw.mcmod.createsdelight.exp;

import org.jetbrains.annotations.NotNull;

import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
/**
 @deprecated 改用 {@link ConcurrentArrayList} */
@Deprecated
public class ClearOnlyCollectionImpl<E> extends AbstractCollection<E> implements ClearOnlyCollection<E> {
    public final Lock lock = new ReentrantLock();
    public E[] elements;
    public final AtomicInteger size = new AtomicInteger(0);

    @SuppressWarnings("unchecked")
    public ClearOnlyCollectionImpl(int initialCapacity) {
        elements = (E[]) new Object[initialCapacity];
    }

    @Override
    public @NotNull Iterator<E> iterator() {
        return new Iterator<>() {
            int i = 0;

            @Override
            public boolean hasNext() {
                return 0 < size();
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                return elements[i++];
            }
        };
    }

    @Override
    public int size() {
        return size.get();
    }

    @Override
    public boolean add(E e) {
        while (elements.length <= size.get()) {
            if (lock.tryLock()) {
                elements = Arrays.copyOf(elements, elements.length * 2);
                lock.unlock();
            } else {
                Thread.yield();
            }
        }
        elements[size.getAndIncrement()] = e;
        return true;
    }

    @Override
    public void clear() {
        size.set(0);
    }
}
