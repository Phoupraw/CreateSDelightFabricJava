import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
public class IteratorTest01 {
    public static void main(String[] args) {
        Collection<Object> c = new ArrayList<>(List.of(1, 2, 3, 4, 1));
        outer:
        for (Iterator<Object> iter1 = c.iterator(); iter1.hasNext(); ) {
            Object i1 = iter1.next();
            for (Iterator<Object> iter2 = c.iterator(); iter2.hasNext(); ) {
                Object i2 = iter2.next();
                if (i1 == i2) {
                    iter2.remove();
                    iter1.remove();
                    break outer;
                }
            }
        }
        System.out.println(c);
    }
}
