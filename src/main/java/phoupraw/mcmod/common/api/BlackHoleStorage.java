package phoupraw.mcmod.common.api;

import net.fabricmc.fabric.api.transfer.v1.storage.base.InsertionOnlyStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.common.impl.BlackHoleStorageImpl;
/**
 能够无限接收物品的黑洞
 @since 0.1.0 */
public interface BlackHoleStorage<T> extends InsertionOnlyStorage<T> {
    @Contract(pure = true)
    static <T> @NotNull BlackHoleStorage<T> of() {
        return BlackHoleStorageImpl.of();
    }
    @Override
    @Contract(pure = true, value = "_,_,_->param2")
    default long insert(T resource, long maxAmount, TransactionContext transaction) {
        return maxAmount;
    }
}
