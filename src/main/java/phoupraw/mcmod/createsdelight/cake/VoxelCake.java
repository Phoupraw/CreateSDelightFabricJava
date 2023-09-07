package phoupraw.mcmod.createsdelight.cake;

import com.google.common.collect.Multimap;
import com.simibubi.create.content.schematics.SchematicWorld;
import net.minecraft.nbt.NbtByteArray;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import phoupraw.mcmod.createsdelight.misc.BlockPosVoxelCake;
import phoupraw.mcmod.createsdelight.misc.EmptyVoxelCake;
import phoupraw.mcmod.createsdelight.registry.CSDRegistries;

import java.util.Objects;

public interface VoxelCake {
    static VoxelCake empty() {
        return EmptyVoxelCake.INSTANCE;
    }
    static @Nullable VoxelCake of(NbtCompound nbt) {
        return BlockPosVoxelCake.of(nbt);
    }
    static VoxelCake of(World world, BlockBox bound) {
        return BlockPosVoxelCake.of(world, bound);
    }
    static VoxelCake of(StructureTemplate st, World world) {
        Vec3i size = st.getSize();
        SchematicWorld sw = new SchematicWorld(world);
        st.place(sw, BlockPos.ORIGIN, BlockPos.ORIGIN, new StructurePlacementData(), sw.getRandom(), 0);
        return of(world, BlockBox.create(BlockPos.ORIGIN, size));
    }
    default NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        Vec3i size = getSize();
        if (getContent() == null || size == null) return nbt;
        NbtCompound nbtContent = new NbtCompound();
        for (var entry : getContent().asMap().entrySet()) {
            byte[] bytesBoxes = new byte[entry.getValue().size() * 6];
            int i = 0;
            for (BlockBox box : entry.getValue()) {
                bytesBoxes[i * 6] = (byte) box.getMinX();
                bytesBoxes[i * 6 + 1] = (byte) box.getMinY();
                bytesBoxes[i * 6 + 2] = (byte) box.getMinZ();
                bytesBoxes[i * 6 + 3] = (byte) box.getMaxX();
                bytesBoxes[i * 6 + 4] = (byte) box.getMaxY();
                bytesBoxes[i * 6 + 5] = (byte) box.getMaxZ();
                i++;
            }
            nbtContent.put(Objects.requireNonNull(CSDRegistries.CAKE_INGREDIENT.getId(entry.getKey()), entry.toString()).toString(), new NbtByteArray(bytesBoxes));
        }
        nbt.put("content", nbtContent);
        nbt.putByteArray("size", new byte[]{(byte) size.getX(), (byte) size.getY(), (byte) size.getZ()});
        return nbt;
    }
    @UnmodifiableView Multimap<CakeIngredient, BlockBox> getContent();
    Vec3i getSize();

}
