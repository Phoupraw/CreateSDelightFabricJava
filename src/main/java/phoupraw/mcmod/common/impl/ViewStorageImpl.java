package phoupraw.mcmod.common.impl;

import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.common.api.ViewStorage;
public abstract class ViewStorageImpl<T> implements ViewStorage<T> {
    private final StorageView<T> view;

    protected ViewStorageImpl(@NotNull StorageView<T> view) {
        this.view = view;
    }

    @Override
    public @NotNull StorageView<T> getView() {
        return view;
    }
}
