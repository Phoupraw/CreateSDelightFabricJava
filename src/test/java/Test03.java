import net.minecraft.nbt.NbtIo;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
public class Test03 {
    public static void main(String[] args) throws Throwable {
        String path1 = "D:\\Entertainments\\Minecraft 最多版本\\.minecraft\\versions\\1.19.3 Fabric\\saves\\生电建筑之旅/region\\r.0.0.mca";
        String path2 = "D:\\Entertainments\\Minecraft 最多版本\\.minecraft\\versions\\1.19.3 Fabric\\saves\\生电建筑之旅\\level.dat";
        var nbt = NbtIo.readCompressed(new DataInputStream(new FileInputStream(path2)));
        try (var writer = new FileWriter("D:\\CCC\\Documents\\03_other/test03.json")) {
            //writer.append(JsonNbtConvertions.convert(nbt).toString());
        }
    }
}
