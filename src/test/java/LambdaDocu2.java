import java.util.function.Consumer;

public class LambdaDocu2 {

    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder();
        append(new ConsumerMain1(builder));
        System.out.println(builder);
    }

    private static void append(Consumer<String> consumer) {
        consumer.accept("123");
    }

    private static void accept_main_1(String s, StringBuilder builder) {
        builder.append(s).append(" ");
    }

    private static class ConsumerMain1 implements Consumer<String> {

        private final StringBuilder builder_main_1;

        private ConsumerMain1(StringBuilder builder_main_1) {
            this.builder_main_1 = builder_main_1;
        }

        @Override
        public void accept(String s) {
            accept_main_1(s, builder_main_1);
        }

    }

}
