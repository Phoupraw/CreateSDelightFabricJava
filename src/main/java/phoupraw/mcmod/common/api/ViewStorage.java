package phoupraw.mcmod.common.api;

import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.common.impl.ExtractionOnlyViewStorage;
import phoupraw.mcmod.common.impl.StorageViewStorageImpl;
public interface ViewStorage<T> extends SingleSlotStorage<T> {
    @Contract("_ -> new")
    static <T> @NotNull ViewStorage<T> of(@NotNull StorageView<T> delegate) {
        return new ExtractionOnlyViewStorage<>(delegate);
    }
    @Contract("_, _ -> new")
    static <T> @NotNull StorageViewStorage<T> of(@NotNull StorageView<T> delegate, @NotNull Storage<T> storage) {
        return new StorageViewStorageImpl<>(delegate, storage);
    }
    @NotNull StorageView<T> getView();
    @Override
    default long extract(T resource, long maxAmount, TransactionContext transaction) {
        return getView().extract(resource, maxAmount, transaction);
    }
    @Override
    default @Nullable StorageView<T> exactView(@NotNull T resource) {
        if (resource.equals(getResource())) return this;
        return SingleSlotStorage.super.exactView(resource);
    }
    @Override
    default boolean isResourceBlank() {
        return getView().isResourceBlank();
    }

    @Override
    default T getResource() {
        return getView().getResource();
    }

    @Override
    default long getAmount() {
        return getView().getAmount();
    }

    @Override
    default long getCapacity() {
        return getView().getCapacity();
    }

    @Override
    default StorageView<T> getUnderlyingView() {
        return getView().getUnderlyingView();
    }
}
