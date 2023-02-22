package phoupraw.mcmod.createsdelight.storage;

import org.jetbrains.annotations.Contract;
public class BlackHoleStorageImpl<T> implements BlackHoleStorage<T> {
    public static final BlackHoleStorageImpl<Object> INSTANCE = new BlackHoleStorageImpl<>();

    @Contract(pure = true)
    @SuppressWarnings("unchecked")
    public static <T> BlackHoleStorageImpl<T> of() {
        return (BlackHoleStorageImpl<T>) INSTANCE;
    }
}
