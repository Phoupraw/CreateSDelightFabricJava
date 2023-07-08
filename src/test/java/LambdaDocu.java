import java.util.function.Consumer;

public class LambdaDocu {

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        append(s -> builder.append(s).append(" "));
        System.out.println(builder);
    }

    private static void append(Consumer<String> consumer) {
        consumer.accept("123");
    }

}
