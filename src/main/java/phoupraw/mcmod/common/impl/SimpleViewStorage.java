package phoupraw.mcmod.common.impl;

import net.fabricmc.fabric.api.transfer.v1.storage.base.ExtractionOnlyStorage;
import phoupraw.mcmod.common.api.ViewStorage;
public interface SimpleViewStorage<T> extends ViewStorage<T>, ExtractionOnlyStorage<T> {

}
