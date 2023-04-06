package phoupraw.mcmod.common.api;

import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.NotNull;
public interface StorageViewStorage<T> extends ViewStorage<T> {
    @Override
    default long insert(T resource, long maxAmount, TransactionContext transaction) {
        return getStorage().insert(resource, maxAmount, transaction);
    }
    @NotNull Storage<T> getStorage();
}
