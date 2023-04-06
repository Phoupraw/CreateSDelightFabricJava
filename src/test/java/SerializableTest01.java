import java.io.*;
/**
 {@link Serializable}类一改变，就不兼容了。不支持默认值。{@link Externalizable}也一样。因为是数据流而不是键值对，所以类的字段一旦改变就会不兼容，且难以检测和处理。 */
public class SerializableTest01 {
    public static void main(String[] args) {
        class2();
    }

    private static void class1() {
        var file = new File("SerializableTest01$Class1_1.obj");
        System.out.println(file.getAbsolutePath());
        write(file, new Class1());
        var obj = (Class1) read(file);
        System.out.println(obj.i);
    }

    private static void class2() {
        var file = new File("SerializableTest01$Class2_1.obj");
        System.out.println(file.getAbsolutePath());
        write(file, new Class2());
        var obj = (Class2) read(file);
        System.out.println(obj.i);
    }

    private static void write(File file, Object obj) {
        try (var output = new ObjectOutputStream(new FileOutputStream(file))) {
            output.writeObject(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object read(File file) {
        try (var input = new ObjectInputStream(new FileInputStream(file))) {
            return input.readObject();
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Class1 implements Serializable {
        @java.io.Serial
        private static final long serialVersionUID = -674041671700562447L;
        public int i = 12;
//        public double i = 12;
    }

    private static class Class2 implements Externalizable {
        @java.io.Serial
        private static final long serialVersionUID = -674041671700562448L;
        public int i = 12;

        public Class2() {}

        @Override
        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeInt(i);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            i = in.readInt();
        }
    }
}
