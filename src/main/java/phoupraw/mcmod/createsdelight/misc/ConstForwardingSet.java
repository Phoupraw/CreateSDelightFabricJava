package phoupraw.mcmod.createsdelight.misc;

import com.google.common.collect.ForwardingSet;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ConstForwardingSet<E, S extends Set<E>> extends ForwardingSet<E> {
    private final S delegate;
    public ConstForwardingSet(S delegate) {this.delegate = delegate;}
    @Override
    protected @NotNull S delegate() {
        return delegate;
    }
}
