package phoupraw.mcmod.createsdelight.misc;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.joml.Vector3i;
import org.joml.Vector3ic;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.registry.CSDRegistries;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public interface BlockVoxelCake {
    static Pair<Map<CakeIngredient, Integer>, byte[]> toGzip(BlockVoxelCake voxelCake) {
        Vector3ic size = voxelCake.size();
        Collection<CakeIngredient> ingredients = voxelCake.ingredients();
        Map<CakeIngredient, Integer> pallete = new LinkedHashMap<>(ingredients.size());//必须是LinkedHashMap
        for (CakeIngredient cakeIngredient : ingredients) {
            pallete.put(cakeIngredient, pallete.size());
        }
        byte[] bytes = new byte[size.x() * size.y() * size.z()];
        for (int i = 0; i < size.x(); i++) {
            for (int j = 0; j < size.y(); j++) {
                for (int k = 0; k < size.z(); k++) {
                    CakeIngredient cakeIngredient = voxelCake.getIngredient(i, j, k);
                    if (cakeIngredient == null) continue;
                    int iid = pallete.get(cakeIngredient) + 1;
                    bytes[(i * size.x() + j) * size.y() + k] = (byte) iid;
                }
            }
        }
        try (var output = new ByteArrayOutputStream(); var gzip = new GZIPOutputStream(output)) {
            gzip.write(bytes);
            gzip.finish();
            byte[] gzipBytes = output.toByteArray();
            return Pair.of(pallete, gzipBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static Pair<Multimap<CakeIngredient, Vector3ic>, Map<Vector3ic, CakeIngredient>> fromGzip(Vector3ic size, Map<Integer, CakeIngredient> pallete, byte[] gzipBytes) {
        Multimap<CakeIngredient, Vector3ic> ingredient2pos = MultimapBuilder.hashKeys().arrayListValues().build();
        Map<Vector3ic, CakeIngredient> pos2ingredient = new HashMap<>();
        try (var input = new ByteArrayInputStream(gzipBytes); var gzip = new GZIPInputStream(input)) {
            byte[] bytes = gzip.readAllBytes();
            for (int i = 0; i < size.x(); i++) {
                for (int j = 0; j < size.y(); j++) {
                    for (int k = 0; k < size.z(); k++) {
                        int iid = bytes[(i * size.x() + j) * size.y() + k];
                        if (iid == 0) continue;
                        CakeIngredient cakeIngredient = pallete.get(iid - 1);
                        Vector3ic pos = new Vector3i(i, j, k);
                        ingredient2pos.put(cakeIngredient, pos);
                        pos2ingredient.put(pos, cakeIngredient);
                    }
                }
            }
        } catch (IOException e) {
            CreateSDelight.LOGGER.catching(e);
            CreateSDelight.LOGGER.error(Arrays.toString(gzipBytes));
            return Pair.of(EmptyVoxelCake.emptyListMultimap(), Map.of());
        }
        return Pair.of(ingredient2pos, pos2ingredient);
    }
    static Triple<Vector3ic, Multimap<CakeIngredient, Vector3ic>, Map<Vector3ic, CakeIngredient>> read(NbtCompound nbt) {
        byte[] nbtSize = nbt.getByteArray("size");
        Vector3ic size;
        if (nbtSize.length == 3) {
            size = new Vector3i(nbtSize[0], nbtSize[1], nbtSize[2]);
        } else {
            size = new Vector3i();
        }
        NbtList nbtPallete = nbt.getList("pallete", NbtElement.STRING_TYPE);
        Map<Integer, CakeIngredient> pallete = new LinkedHashMap<>();
        for (int i = 0; i < nbtPallete.size(); i++) {
            pallete.put(i, CSDRegistries.get(CSDRegistries.CAKE_INGREDIENT, new Identifier(nbtPallete.getString(i))));
        }
        byte[] gzipBytes = nbt.getByteArray("gzip");
        var pair = fromGzip(size, pallete, gzipBytes);
        return Triple.of(size, pair.getLeft(), pair.getRight());
    }
    static NbtCompound write(BlockVoxelCake voxelCake, @NotNull NbtCompound nbt) {
        nbt.putByteArray("size", new byte[]{(byte) voxelCake.size().x(), (byte) voxelCake.size().y(), (byte) voxelCake.size().z()});
        var pair = toGzip(voxelCake);
        NbtList nbtPallete = new NbtList();
        for (CakeIngredient cakeIngredient : pair.getLeft().keySet()) {
            nbtPallete.add(NbtString.of(CSDRegistries.getId(CSDRegistries.CAKE_INGREDIENT, cakeIngredient).toString()));
        }
        nbt.put("pallete", nbtPallete);
        nbt.putByteArray("gzip", pair.getRight());
        return nbt;
    }
    Vector3ic size();
    @UnmodifiableView Collection<CakeIngredient> ingredients();
    @Nullable @UnmodifiableView Multimap<CakeIngredient, Vector3ic> ingredient2pos();
    @Nullable CakeIngredient getIngredient(int x, int y, int z);
    double hunger();
    default NbtCompound write(NbtCompound nbt) {return write(this, nbt);}
}
