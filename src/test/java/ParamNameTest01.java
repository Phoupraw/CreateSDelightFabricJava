public class ParamNameTest01 {
    public static void main(String[] args) throws NoSuchMethodException {
        var method = ParamNameTest01.class.getMethod("test", int.class, int.class);
        System.out.println(method.getParameters()[0].getName());
    }

    public void test(int a, int b) {

    }
}
