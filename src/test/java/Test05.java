/**
 类加载机制 */
public class Test05 {
    public static void main(String[] args) {
        C2.f1();
    }

    private static class C1 {
        static {
            System.out.println("C1 load");
        }
    }

    private static class C2 {
        public static final Object OBJECT = new Object();
        static {
            System.out.println("C2 load");
        }
        static C1 f1() {
            C1 c1 = null;
            if (System.currentTimeMillis() > 1) {
                c1 = new C1();
            }
            return c1;
        }
    }
}
