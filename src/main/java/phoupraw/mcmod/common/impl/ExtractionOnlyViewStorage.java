package phoupraw.mcmod.common.impl;

import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.ExtractionOnlyStorage;
import org.jetbrains.annotations.NotNull;
public class ExtractionOnlyViewStorage<T> extends ViewStorageImpl<T> implements ExtractionOnlyStorage<T> {
    public ExtractionOnlyViewStorage(@NotNull StorageView<T> view) {
        super(view);
    }
}
