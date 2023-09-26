package phoupraw.mcmod.createsdelight.misc;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class IdentityWeakReference<T> extends WeakReference<T> {
    public IdentityWeakReference(T referent) {
        super(referent);
    }
    public IdentityWeakReference(T obj, ReferenceQueue<? super T> refQueue) {
        super(obj, refQueue);
    }
    @Override
    public boolean equals(Object o) {
        return o instanceof IdentityWeakReference<?> that && this.get() == that.get();
    }
    @Override
    public int hashCode() {
        return System.identityHashCode(get());
    }
}
