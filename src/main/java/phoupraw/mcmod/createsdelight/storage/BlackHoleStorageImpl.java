package phoupraw.mcmod.createsdelight.storage;

public class BlackHoleStorageImpl<T> implements BlackHoleStorage<T> {
    public static final BlackHoleStorageImpl<Object> INSTANCE = new BlackHoleStorageImpl<>();

    @SuppressWarnings("unchecked")
    public static <T> BlackHoleStorageImpl<T> of() {
        return (BlackHoleStorageImpl<T>) INSTANCE;
    }
}
