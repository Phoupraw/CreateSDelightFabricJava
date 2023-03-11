package phoupraw.mcmod.createsdelight.exp;

import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 @deprecated 改用 {@link ConcurrentArrayList} */
@Deprecated
public class ConcurrentCollection<E> extends AbstractCollection<E> {
    public final AtomicInteger size = new AtomicInteger(0);
    public final Collection<Node<E>> nodes = new LinkedList<>();
    public final AtomicReference<@Nullable Node<E>> first = new AtomicReference<>(null);

    @Override
    public int size() {
        return size.get();
    }

    @Override
    public Iterator<E> iterator() {
        return new NodeIterator();
    }

    @Override
    public boolean add(E e) {
        if (first.get() == null && first.compareAndSet(null, new Node<>(e, null, null))) {
            return true;
        }
        return true;
    }

    @Override
    public void clear() {
        first.set(null);
    }

    public class NodeIterator implements Iterator<E> {
        public @Nullable Node<E> just;
        public @Nullable Node<E> next;

        public NodeIterator() {
            this(first.get());
        }

        public NodeIterator(@Nullable Node<E> first) {
            next = first;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public E next() {
            just = this.next;
            if (this.next == null) throw new NoSuchElementException();
            this.next = just.getNext();
            return just.getItem();
        }

        @Override
        public void remove() {
            if (just == null) throw new IllegalStateException();
            just.remove();
            size.decrementAndGet();
        }
    }

    public static class Node<E> {
        private final E item;
        public final ReadWriteLock lock = new ReentrantReadWriteLock();
        private volatile @Nullable Node<E> next;
        private volatile @Nullable Node<E> prev;

        public Node(E item, @Nullable Node<E> prev, @Nullable Node<E> next) {
            this.item = item;
            this.setNext(next);
            this.setPrev(prev);
        }

        public E getItem() {
            return item;
        }

        public @Nullable Node<E> getNext() {
            lock.readLock().lock();
            try {
                return next;
            } finally {
                lock.readLock().unlock();
            }
        }

        public void setNext(@Nullable Node<E> next) {
            lock.writeLock().lock();
            try {
                this.next = next;
            } finally {
                lock.writeLock().unlock();
            }
        }

        public @Nullable Node<E> getPrev() {
            lock.readLock().lock();
            try {
                return prev;
            } finally {
                lock.readLock().unlock();
            }
        }

        public void setPrev(@Nullable Node<E> prev) {
            lock.writeLock().lock();
            try {
                this.prev = prev;
            } finally {
                lock.writeLock().unlock();
            }
        }

        public void add(E item) {
            lock.writeLock().lock();
            try {
                var next = getNext();
                var node = new Node<>(item, this, next);
                setNext(node);
                if (next != null) {
                    next.setPrev(node);
                }
            } finally {
                lock.writeLock().unlock();
            }
        }

        public void remove() {
            lock.writeLock().lock();
            try {
                if (getPrev() != null) {
                    getPrev().setNext(getNext());
                }
                if (getNext() != null) {
                    getNext().setPrev(getPrev());
                }
            } finally {
                lock.writeLock().unlock();
            }
        }
    }
}
