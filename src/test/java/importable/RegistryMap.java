package importable;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
public class RegistryMap<T> {
    public final BiMap<ResrcLoc, T> biMap = HashBiMap.create();
}
