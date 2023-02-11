package phoupraw.mcmod.createsdelight.api;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
public class CacheCollections {
    @Contract(mutates = "param1", value = "_,_,_->new")
    public static <E> @NotNull Collection<E> top(@NotNull List<E> source, Predicate<E> filter, int limit) {
        Collection<E> filtered = remove(source, filter, limit);
        source.addAll(0, filtered);
        return filtered;
    }

    @Contract(mutates = "param1", value = "_,_,_->new")
    public static <E> @NotNull Collection<E> top(@NotNull Deque<E> source, Predicate<E> filter, int limit) {
        Collection<E> filtered = remove(source, filter, limit);
        for (E e : filtered) source.addFirst(e);
        return filtered;
    }

    @Contract(mutates = "param1", value = "_,_,_->new")
    public static <E> @NotNull Collection<E> remove(@NotNull Iterable<E> source, Predicate<E> filter, int limit) {
        Collection<E> filtered = new LinkedList<>();
        for (var ite = source.iterator(); ite.hasNext() && filtered.size() < limit; ) {
            var e = ite.next();
            if (filter.test(e)) {
                filtered.add(e);
                ite.remove();
            }
        }
        return filtered;
    }
}
