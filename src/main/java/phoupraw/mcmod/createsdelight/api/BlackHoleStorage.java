package phoupraw.mcmod.createsdelight.api;

import net.fabricmc.fabric.api.transfer.v1.storage.base.InsertionOnlyStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
public interface BlackHoleStorage<T> extends InsertionOnlyStorage<T> {
    @Contract(pure = true)
    static <T> @NotNull BlackHoleStorage<T> of() {
        return BlackHoleStorageImpl.of();
    }
    @Override
    default long insert(T resource, long maxAmount, TransactionContext transaction) {
        return maxAmount;
    }
}
