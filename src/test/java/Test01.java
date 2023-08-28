import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.IntStream;
public class Test01 {
    public static void main(String[] args) {
        Collection<Integer> normal = Collections.synchronizedCollection(new ArrayList<>());
        //Collection<Integer> fast = new ConcurrentArrayList<>(16);
        int limit = 10000_0000;
        long t = System.currentTimeMillis();
//        for (int i = 0; i < 100000; i++) {
//            new Thread(() -> {
//
//            }).start();
//        }
        IntStream.range(0, limit).boxed().parallel().forEach(normal::add);
        System.out.println("normal add " + (System.currentTimeMillis() - t));
        System.out.println("normal size " + normal.size());
        t = System.currentTimeMillis();
        normal.clear();
        System.out.println("normal clear " + (System.currentTimeMillis() - t));
        t = System.currentTimeMillis();
        //IntStream.range(0, limit).boxed().parallel().forEach(fast::add);
        System.out.println("fast add " + (System.currentTimeMillis() - t));
        //System.out.println("fast size " + fast.size());
        //        System.out.println(fast);
//        System.out.println(fast.parallelStream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
        t = System.currentTimeMillis();
        //fast.clear();
        System.out.println("fast clear " + (System.currentTimeMillis() - t));
    }
}
