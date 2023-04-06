package phoupraw.mcmod.common.impl;

import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.common.api.StorageViewStorage;
@SuppressWarnings("ClassCanBeRecord")
public class StorageViewStorageImpl<T> extends ViewStorageImpl<T> implements StorageViewStorage<T> {
    private final Storage<T> storage;

    public StorageViewStorageImpl(@NotNull StorageView<T> view, @NotNull Storage<T> storage) {
        super(view);
        this.storage = storage;
    }

    @Override
    public @NotNull Storage<T> getStorage() {
        return storage;
    }
}
