package phoupraw.mcmod.common.impl;

import org.jetbrains.annotations.Contract;
import phoupraw.mcmod.common.api.BlackHoleStorage;
public class BlackHoleStorageImpl<T> implements BlackHoleStorage<T> {
    public static final BlackHoleStorageImpl<Object> INSTANCE = new BlackHoleStorageImpl<>();

    @Contract(pure = true)
    @SuppressWarnings("unchecked")
    public static <T> BlackHoleStorageImpl<T> of() {
        return (BlackHoleStorageImpl<T>) INSTANCE;
    }
}
