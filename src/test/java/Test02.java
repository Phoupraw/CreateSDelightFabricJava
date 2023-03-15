import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;
public class Test02 {
    public static void main(String[] args) throws IOException {
        JsonObject object = new JsonObject();
        for (int i = 0; i < 24; i++) {
            object.add(String.valueOf(i), object.deepCopy());
        }
        try (var writer = new FileWriter("D:\\CCC\\Documents\\03_other/test02.json")) {
            writer.append(object.toString());
        }
    }
}
