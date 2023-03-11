package phoupraw.mcmod.createsdelight.exp;

import java.util.Collection;
import java.util.LinkedList;
public abstract class RecursiveCollection<E> implements Collection<E> {
    @SuppressWarnings("unchecked")
    public static <E> E blank() {
        return (E) RecursiveCollection.class;
    }

    public final Collection<RecursiveCollection<E>> recursion = new LinkedList<>();
    public E element = blank();

    @Override
    public boolean add(E e) {
        if (isBlank()) {
            element = e;
        } else {

        }
        return true;
    }

    public boolean isBlank() {
        return element == blank();
    }
}
