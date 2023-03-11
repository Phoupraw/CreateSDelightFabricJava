import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.storage.RegionBasedStorage;
import net.minecraft.world.storage.RegionFile;
import phoupraw.mcmod.createsdelight.exp.JsonNbtConvertions;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Test04 {
    public static void main(String[] args) throws InvocationTargetException, InstantiationException, IllegalAccessException, IOException {
        Pattern pattern = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
        File mcaFile = new File("D:\\Entertainments\\Minecraft 最多版本\\.minecraft\\versions\\1.19.3 Fabric\\saves\\生电建筑之旅/region\\r.0.0.mca");
        Matcher matcher = pattern.matcher(mcaFile.getName());
        matcher.matches();
        int x0 = Integer.parseInt(matcher.group(1)) << 5;
        int z0 = Integer.parseInt(matcher.group(2)) << 5;
        var chunks = new NbtCompound();
        var constructor = RegionBasedStorage.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        var storage = (RegionBasedStorage) constructor.newInstance(mcaFile.getParentFile().toPath(), false);
        long time = System.currentTimeMillis();
        try (RegionFile regionFile = new RegionFile(mcaFile.toPath(), mcaFile.getParentFile().toPath(), false)) {
            for (int x1 = 0; x1 < 32; ++x1) {
                for (int z1 = 0; z1 < 32; ++z1) {
                    ChunkPos chunkPos = new ChunkPos(x1 + x0, z1 + z0);
                    if (regionFile.isChunkValid(chunkPos)) {
                        chunks.put(chunkPos.x + "," + chunkPos.z, storage.getTagAt(chunkPos));
                    }
                }
            }
        }
        System.out.println("mca time = " + (System.currentTimeMillis() - time));
        File jsonFile = new File("D:\\CCC\\Documents\\03_other/test04.json");
        try (var writer = new FileWriter(jsonFile)) {
            writer.append(JsonNbtConvertions.convert(chunks).toString());
        }
        time = System.currentTimeMillis();
        new Gson().fromJson(new FileReader(jsonFile), JsonObject.class);
//        JsonNbtConvertions.convert();
        System.out.println("json time = " + (System.currentTimeMillis() - time));
    }
}
