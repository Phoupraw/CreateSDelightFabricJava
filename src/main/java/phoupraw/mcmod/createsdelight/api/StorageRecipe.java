package phoupraw.mcmod.createsdelight.api;

import net.fabricmc.fabric.api.transfer.v1.storage.Storage;

import java.util.function.Predicate;
public interface StorageRecipe<T> extends Predicate<Storage<T>> {

}
