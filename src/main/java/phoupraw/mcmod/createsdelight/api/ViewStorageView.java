package phoupraw.mcmod.createsdelight.api;

import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
@SuppressWarnings("ClassCanBeRecord")
public class ViewStorageView<T> implements SingleSlotStorage<T> {
    private final Storage<T> storage;
    private final StorageView<T> view;

    public ViewStorageView(Storage<T> storage, StorageView<T> view) {
        this.storage = storage;
        this.view = view;
    }

    @Override
    public long insert(T resource, long maxAmount, TransactionContext transaction) {
        return getStorage().extract(resource, maxAmount, transaction);
    }

    @Override
    public long extract(T resource, long maxAmount, TransactionContext transaction) {
        long amount = getView().extract(resource, maxAmount, transaction);
        if (amount != 0) return amount;
        return getStorage().extract(resource, maxAmount, transaction);
    }

    @Override
    public Iterator<StorageView<T>> iterator() {
        return getStorage().iterator();
    }

    @Override
    public @Nullable StorageView<T> exactView(T resource) {
        if (resource.equals(getResource())) return this;
        return SingleSlotStorage.super.exactView(resource);
    }

    @Override
    public boolean isResourceBlank() {
        return getView().isResourceBlank();
    }

    @Override
    public T getResource() {
        return getView().getResource();
    }

    @Override
    public long getAmount() {
        return getView().getAmount();
    }

    @Override
    public long getCapacity() {
        return getView().getCapacity();
    }

    @Override
    public StorageView<T> getUnderlyingView() {
        return getView().getUnderlyingView();
    }

    public Storage<T> getStorage() {
        return storage;
    }

    public StorageView<T> getView() {
        return view;
    }
}
