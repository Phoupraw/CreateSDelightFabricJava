import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.block.MadeVoxelBlock;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;

/**
 @see MadeVoxelBlock#reduceFailable */
class Test01 {
    public static void main(String[] args) {
        Collection<Integer> source = IntStream.concat(IntStream.of(1), IntStream.iterate(1, i -> i < 100, i -> i + 1)).boxed().toList();
        Collection<Integer> reduced = reduceFailable2(source, (a, b) -> a != null && a.equals(b) ? a + 1 : null);
        System.out.println(reduced);
    }
    public static <E> Collection<@NotNull E> reduceFailable(Collection<@NotNull E> source, BinaryOperator<@Nullable E> combiner) {
        Deque<E> queue = new ConcurrentLinkedDeque<>(source);
        AtomicInteger polledC = new AtomicInteger();
        int threadC = Runtime.getRuntime().availableProcessors() * 4;
        Collection<Thread> threads = new ArrayList<>(threadC);
        int loopC = source.size() * 3;
        for (int i = 0; i < threadC; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < loopC; j++) {
                    E e1 = queue.pollFirst();
                    if (e1 == null) {
                        if (polledC.get() >= 1) j--;
                        continue;
                    }
                    polledC.incrementAndGet();
                    E e2 = queue.pollFirst();
                    if (e2 == null) {
                        queue.offerFirst(e1);
                        polledC.decrementAndGet();
                        if (polledC.get() >= 2) j--;
                        continue;
                    }
                    polledC.incrementAndGet();
                    E e12 = combiner.apply(e1, e2);
                    if (e12 == null) {
                        queue.offerFirst(e1);
                        queue.offerLast(e2);
                        polledC.addAndGet(-2);
                    } else {
                        queue.offerLast(e12);
                        polledC.decrementAndGet();
                    }
                }
            });
            threads.add(thread);
            thread.start();
        }
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return queue;
    }
    public static <E> Collection<@NotNull E> reduceFailable2(Collection<@NotNull E> source, BinaryOperator<@Nullable E> combiner) {
        Deque<E> deque = new ArrayDeque<>(source);
        for (int i = 0, tryC = deque.size(); i < tryC; i++) {
            for (int j = 0, loopC = deque.size(); j < loopC; j++) {
                E e1 = deque.pollFirst();
                if (e1 == null) {
                    continue;
                }
                E e2 = deque.pollFirst();
                if (e2 == null) {
                    deque.offerFirst(e1);
                    continue;
                }
                E e12 = combiner.apply(e1, e2);
                if (e12 == null) {
                    deque.offerFirst(e1);
                    deque.offerLast(e2);
                } else {
                    deque.offerLast(e12);
                }
            }
            tryC = Math.min(tryC / 2, deque.size());
        }
        return deque;
    }
}
