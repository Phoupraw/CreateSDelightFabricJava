package phoupraw.mcmod.createsdelight.exp;

import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
public interface StorageOperation0<T> {
    int insert(Storage<T> target, int maxRepeatings, TransactionContext transaction);
    int extract(Storage<T> target, int maxRepeatings, TransactionContext transaction);
    default int replace(Storage<T> target, int maxRepeatings, TransactionContext transaction) {
        while (true) {
            int extracting, inserting;
            try (var transaction2 = transaction.openNested()) {
                extracting = extract(target, maxRepeatings, transaction2);
                inserting = insert(target, maxRepeatings, transaction2);
                if (extracting == inserting) {
                    transaction2.commit();
                    return extracting;
                }
            }
            maxRepeatings = Math.min(extracting, inserting);
        }
    }
}
