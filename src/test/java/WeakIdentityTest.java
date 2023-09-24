import java.util.IdentityHashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakIdentityTest {
    @SuppressWarnings("StringOperationCanBeSimplified")
    public static void main(String[] args) {
        Map<Object, Object> weakMap = new WeakHashMap<>();
        Map<Object, Object> identityMap = new IdentityHashMap<>();
        weakMap.put("1", 1);
        identityMap.put("1", 1);
        System.out.println(weakMap.get(new String("1")));
        System.out.println(identityMap.get(new String("1")));
    }
}
