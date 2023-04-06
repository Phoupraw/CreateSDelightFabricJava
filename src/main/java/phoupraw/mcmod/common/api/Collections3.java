package phoupraw.mcmod.common.api;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Collection;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
/**
 @since 1.0.0 */
public final class Collections3 {
    /**
     搜索元素并提至最前。
     @param source 要搜索的容器，必须可以被修改
     @param filter 过滤器
     @param limit 最多搜索几个元素
     @param <E> 元素类型
     @return 一个包含搜索到的元素的容器，可以修改。
     @throws UnsupportedOperationException 若{@code source}不支持修改
     @since 1.0.0
     */
    @Contract(mutates = "param1", value = "_,_,_->new")
    public static <E> @NotNull Collection<E> top(@NotNull List<E> source, @NotNull Predicate<? super E> filter, @Range(from = 0, to = Integer.MAX_VALUE) int limit) {
        Collection<E> filtered = remove(source, filter, limit);
        source.addAll(0, filtered);
        return filtered;
    }

    /**
     搜索元素并提至最前。
     @param source 要搜索的容器，必须可以被修改
     @param filter 过滤器
     @param limit 最多搜索几个元素
     @param <E> 元素类型
     @return 一个包含搜索到的元素的容器，可以修改。
     @throws UnsupportedOperationException 若{@code source}不支持修改
     @since 1.0.0
     */
    @Contract(mutates = "param1", value = "_,_,_->new")
    public static <E> @NotNull Collection<E> top(@NotNull Deque<E> source, @NotNull Predicate<? super E> filter, @Range(from = 0, to = Integer.MAX_VALUE) int limit) {
        Collection<E> filtered = remove(source, filter, limit);
        for (E e : filtered) source.addFirst(e);
        return filtered;
    }

    /**
     移除元素，就像{@link Collection#removeIf}。
     @param source 要被移除元素的容器
     @param filter 满足条件的才会被移除
     @param limit 最多删除几个元素
     @param <E> 元素类型
     @return 一个包含被删除的元素的容器，可以修改。
     @throws UnsupportedOperationException 若{@code source}不支持修改
     @since 1.0.0
     */
    @Contract(mutates = "param1", value = "_,_,_->new")
    public static <E> @NotNull Collection<E> remove(@NotNull Iterable<? extends E> source, @NotNull Predicate<? super E> filter, @Range(from = 0, to = Integer.MAX_VALUE) int limit) {
        Collection<E> removed = new LinkedList<>();
        for (var iterator = source.iterator(); iterator.hasNext() && removed.size() < limit; ) {
            var e = iterator.next();
            if (filter.test(e)) {
                removed.add(e);
                iterator.remove();
            }
        }
        return removed;
    }

    private Collections3() {}

}
