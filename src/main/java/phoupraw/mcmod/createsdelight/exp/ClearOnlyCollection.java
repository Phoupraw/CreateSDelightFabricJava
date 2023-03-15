package phoupraw.mcmod.createsdelight.exp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Predicate;
/**
 支持高并发调用{@link Collection#add}的集合，支持{@link #iterator()}，但是不支持{@link Iterator#remove()}和{@link Collection#remove}，只支持{@link Collection#clear()}。
 @see #of()
 @deprecated 改用 {@link ConcurrentArrayList} */
@Deprecated
public interface ClearOnlyCollection<E> extends Collection<E> {
    /**
     创建一个新的{@link ClearOnlyCollection}。
     */
    @Contract(value = " -> new", pure = true)
    static <E> @NotNull ClearOnlyCollection<E> of() {
        return new ClearOnlyCollectionImpl<>(16);
    }
    /**
     时间复杂度：几乎常数，在高并发情况下也是如此。
     */
    @Override
    @Contract(value = "_->true", mutates = "this")
    boolean add(E e);
    /**
     <code>c</code>中所有元素都会被添加。<br>时间复杂度：几乎线性
     */
    @Override
    @Contract(mutates = "this")
    boolean addAll(@NotNull Collection<? extends E> c);
    /**
     集合内的元素的顺序是调用{@link #add}的顺序。
     @return 一个不支持 {@link Iterator#remove()}的迭代器
     */
    @NotNull
    @Override
    @Contract(pure = true)
    Iterator<E> iterator();
    /**
     时间复杂度：常数
     */
    @Override
    @Contract(mutates = "this")
    void clear();
    /**
     @throws UnsupportedOperationException 总是
     */
    @Override
    @Contract(value = "_->fail", pure = true)
    default boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }
    /**
     @throws UnsupportedOperationException 总是
     */
    @Override
    @Contract(value = "_->fail", pure = true)
    default boolean removeAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException();
    }
    /**
     @throws UnsupportedOperationException 总是
     */
    @Override
    @Contract(value = "_->fail", pure = true)
    default boolean removeIf(Predicate<? super E> filter) {
        throw new UnsupportedOperationException();
    }
    /**
     @throws UnsupportedOperationException 总是
     */
    @Override
    @Contract(value = "_->fail", pure = true)
    default boolean retainAll(@NotNull Collection<?> c) {
        throw new UnsupportedOperationException();
    }
}
