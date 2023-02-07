package phoupraw.mcmod.createsdelight.api;

import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
/**
 * @see #replace
 */
public interface ReplaceableStorageView<T> extends StorageView<T> {
    /**
     * <p>Directly replace the resource without disorder the storage.</p>
     *
     * <p>If you just want to replace the resource, such as applying recipe, swapping items, modifying nbt, and so on, in general, you may invoke {@link StorageView#extract} and {@link Storage#insert} in order. However, if this storage is ordered, for example, a machine with multiple processing slots (like campfire), these invokings might disorder it and cause a series of problems about the order. This method provide a better way for it.</p>
     *
     * @param resource The resource to insert. May not be blank.
     * @param amount   The maximum amount of resource to insert. May not be negative.
     * @param transa   The transaction this operation is part of.
     * @return Whether this replacing is successful.
     * @see Storage#insert
     * @see StorageView#extract
     */
    boolean replace(T resource, long amount, TransactionContext transa);
}
