package importable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
public class RegistryBuilder<T> {
    private final ConcurrentMap<ResrcLoc, T> map = new ConcurrentHashMap<>();

}
