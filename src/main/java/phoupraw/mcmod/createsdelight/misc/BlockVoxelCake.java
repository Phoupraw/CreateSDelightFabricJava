package phoupraw.mcmod.createsdelight.misc;

import com.google.common.collect.Multimap;
import net.minecraft.nbt.NbtCompound;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.joml.Vector3ic;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

public interface BlockVoxelCake {
    static Pair<@TypeVarName("pallete") Map<CakeIngredient, Integer>, @TypeVarName("gzipBytes") byte[]> palleteGzip(BlockVoxelCake voxelCake) {
        Map<CakeIngredient, Integer> pallete = new HashMap<>(voxelCake.ingredients().size());
        for (CakeIngredient cakeIngredient : voxelCake.ingredients()) {
            pallete.put(cakeIngredient, pallete.size());
        }
        byte[] bytes = new byte[voxelCake.size().x() * voxelCake.size().y() * voxelCake.size().z()];
        for (int i = 0; i < voxelCake.size().x(); i++) {
            for (int j = 0; j < voxelCake.size().y(); j++) {
                for (int k = 0; k < voxelCake.size().z(); k++) {
                    bytes[(i * voxelCake.size().x() + j) * voxelCake.size().y() + k] = pallete.get(voxelCake.getIngredient(i, j, k)).byteValue();
                }
            }
        }
        try (var output = new ByteArrayOutputStream(); var gzip = new GZIPOutputStream(output)) {
            gzip.write(bytes);
            byte[] gzipBytes = output.toByteArray();
            return Pair.of(pallete, gzipBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    Vector3ic size();
    @UnmodifiableView Collection<CakeIngredient> ingredients();
    @Nullable @UnmodifiableView Multimap<CakeIngredient, Vector3ic> ingredient2pos();
    CakeIngredient getIngredient(int x, int y, int z);
    double hunger();
    default NbtCompound write(NbtCompound nbt) {return write(this, nbt);}
    static NbtCompound write(BlockVoxelCake voxelCake, NbtCompound nbt) {
        Map<CakeIngredient, Integer> pallete = new HashMap<>(voxelCake.ingredients().size());
        for (CakeIngredient cakeIngredient : voxelCake.ingredients()) {
            pallete.put(cakeIngredient, pallete.size());
        }
        byte[] bytes = new byte[voxelCake.size().x() * voxelCake.size().y() * voxelCake.size().z()];
        for (int i = 0; i < voxelCake.size().x(); i++) {
            for (int j = 0; j < voxelCake.size().y(); j++) {
                for (int k = 0; k < voxelCake.size().z(); k++) {
                    bytes[(i * voxelCake.size().x() + j) * voxelCake.size().y() + k] = pallete.get(voxelCake.getIngredient(i, j, k)).byteValue();
                }
            }
        }
        try (var output = new ByteArrayOutputStream(); var gzip = new GZIPOutputStream(output)) {
            gzip.write(bytes);
            byte[] gzipBytes = output.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return nbt;
    }
}
