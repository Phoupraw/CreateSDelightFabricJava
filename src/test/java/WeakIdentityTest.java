import com.google.common.collect.MapMaker;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakIdentityTest {
    @SuppressWarnings("StringOperationCanBeSimplified")
    public static void main(String[] args) {
        Map<Object, Object> weakMap = new WeakHashMap<>();
        Map<Object, Object> identityMap = new IdentityHashMap<>();
        Map<Object, Object> guavaWeakKeyMap = new MapMaker().weakKeys().makeMap();
        weakMap.put("1", 1);
        identityMap.put("1", 1);
        guavaWeakKeyMap.put("1", 1);
        Object newKey = new String("1");
        System.out.println(weakMap.get(newKey));
        System.out.println(identityMap.get(newKey));
        System.out.println(guavaWeakKeyMap.get(newKey));
    }
}
