package phoupraw.mcmod.createsdelight.cake;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.block.entity.PrintedCakeBlockEntity;
import phoupraw.mcmod.createsdelight.registry.CSDRegistries;

public class VoxelCakeRecord implements VoxelCake {
    public static @Nullable VoxelCake of(NbtCompound nbt) {
        if (!nbt.contains("content", NbtElement.COMPOUND_TYPE) || !nbt.contains("size", NbtElement.BYTE_ARRAY_TYPE)) {
            return null;
        }
        NbtCompound nbtContent = nbt.getCompound("content");
        Multimap<CakeIngredient, BlockBox> content = HashMultimap.create(nbtContent.getSize(), 16);
        for (String key : nbtContent.getKeys()) {
            CakeIngredient cakeIngredient = CSDRegistries.CAKE_INGREDIENT.get(new Identifier(key));
            byte[] bytesBoxes = nbtContent.getByteArray(key);
            int c = bytesBoxes.length / 6;
            for (int i = 0; i < c; i++) {
                content.put(cakeIngredient, new BlockBox(bytesBoxes[i * 6], bytesBoxes[i * 6 + 1], bytesBoxes[i * 6 + 2], bytesBoxes[i * 6 + 3], bytesBoxes[i * 6 + 4], bytesBoxes[i * 6 + 5]));
            }
        }
        byte[] nbtSize = nbt.getByteArray("size");
        Vec3i size = new Vec3i(nbtSize[0], nbtSize[1], nbtSize[2]);
        return of(content, size);
    }
    public static VoxelCake of(World world, BlockBox bound) {
        ListMultimap<CakeIngredient, BlockBox> content = MultimapBuilder.hashKeys().arrayListValues().build();
        Vec3i size = bound.getDimensions().add(1, 1, 1);
        BlockPos pos0 = new BlockPos(bound.getMinX(), bound.getMinY(), bound.getMinZ());
        for (int i = 0; i <= size.getX(); i++) {
            for (int j = 0; j <= size.getY(); j++) {
                for (int k = 0; k <= size.getZ(); k++) {
                    BlockPos pos1 = pos0.add(i, j, k);
                    CakeIngredient ci = CakeIngredient.LOOKUP.find(world, pos1, null);
                    if (ci != null) {
                        content.put(ci, new BlockBox(i, j, k, i + 1, j + 1, k + 1));
                    }
                }
            }
        }
        for (CakeIngredient cakeIngredient : content.keySet()) {
            content.get(cakeIngredient).sort(PrintedCakeBlockEntity.BLOCK_BOX_COMPARATOR);
        }
        return VoxelCakeRecord.of(content, size);
    }
    public static VoxelCake of(Multimap<CakeIngredient, BlockBox> content, Vec3i size) {
        return new VoxelCakeRecord(content, size);
    }
    private final Multimap<CakeIngredient, BlockBox> content;
    private final Vec3i size;

    public VoxelCakeRecord(Multimap<CakeIngredient, BlockBox> content, Vec3i size) {
        this.content = content;
        this.size = size;
    }

    @Override
    public Multimap<CakeIngredient, BlockBox> getContent() {
        return content;
    }

    @Override
    public Vec3i getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "PredefinedCakeRecord{" +
               "content=" + content +
               ", size=" + size +
               '}';
    }

}
