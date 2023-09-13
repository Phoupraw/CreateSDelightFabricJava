package phoupraw.mcmod.createsdelight.misc;

import com.google.common.collect.Iterators;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Set;

public class NullableKeySet<E, S extends Set<E>> extends ConstForwardingSet<E, S> {
    public boolean containsNull;
    public NullableKeySet(S delegate) {
        super(delegate);
    }
    @Override
    public boolean contains(@Nullable Object object) {
        return object == null ? containsNull : super.contains(object);
    }
    @Override
    public boolean add(E element) {
        return super.add(element);
    }
    @Override
    public Iterator<@Nullable E> iterator() {
        return Iterators.concat(Iterators.singletonIterator(null), super.iterator());
    }
    //TODO
}
