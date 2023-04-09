package phoupraw.mcmod.createsdelight.exp;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 支持高并发调用{@link Collection#add}的集合。<br/>
 经测试，效率还不如<code>{@link Collections#synchronizedCollection}(new {@link ArrayList}<>())</code> */
@ApiStatus.Experimental
public class ConcurrentArrayList<E> extends AbstractCollection<E> {
    public final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    public final AtomicReference<E[]> elementsHolder = new AtomicReference<>();
    public final AtomicInteger sizeHolder = new AtomicInteger(0);

    @SuppressWarnings("unchecked")
    @Contract(pure = true)
    public ConcurrentArrayList(int initialCapacity) {
        elementsHolder.set((E[]) new Object[initialCapacity]);
    }

    /**
     集合内的元素的顺序是调用{@link #add}的顺序。
     @return 一个支持 {@link Iterator#remove()}的迭代器
     */

    @Override
    @Contract(pure = true, value = "->new")
    public @NotNull Iterator<E> iterator() {
        return new Iterator<>() {
            E just;
            int i = 0;

            @Override
            public boolean hasNext() {
                return i < size();
            }

            @Override
            public E next() {
                if (!hasNext()) throw new NoSuchElementException();
                readWriteLock.readLock().lock();
                try {
                    return just = elementsHolder.get()[i++];
                } finally {
                    readWriteLock.readLock().unlock();
                }
            }

            @Override
            public void remove() {
                readWriteLock.writeLock().lock();
                try {
                    if (!Objects.equals(just, elementsHolder.get()[i - 1])) {
                        boolean found = false;
                        for (int j = i - 1; j >= 0; j--) {
                            if (Objects.equals(just, elementsHolder.get()[j])) {
                                i = j;
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            return;
                        }
                    }
                    System.arraycopy(elementsHolder.get(), i, elementsHolder.get(), i - 1, size() - i);
                } finally {
                    readWriteLock.writeLock().unlock();
                }
            }
        };
    }

    @Override
    public int size() {
        return sizeHolder.get();
    }

    /**
     时间复杂度：几乎常数，在高并发情况下也是如此。
     */
    @Override
    @Contract(value = "_->true", mutates = "this")
    public boolean add(E e) {
        int size = sizeHolder.getAndIncrement();
        while (size >= elementsHolder.get().length) {
            if (readWriteLock.writeLock().tryLock()) {
                try {
                    E[] elements = elementsHolder.get();
                    elementsHolder.set(Arrays.copyOf(elements, elements.length * 2));
                } finally {
                    readWriteLock.writeLock().unlock();
                }
            } else {
                Thread.yield();
            }
        }
        readWriteLock.readLock().lock();
        try {
            elementsHolder.get()[size] = e;
        } finally {
            readWriteLock.readLock().unlock();
        }
        return true;
    }

    /**
     时间复杂度：常数
     */
    @Override
    @Contract(mutates = "this")
    public void clear() {
        readWriteLock.writeLock().lock();
        try {
            sizeHolder.set(0);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

}
