package phoupraw.mcmod.createsdelight.misc;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.google.common.collect.Multimaps;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.Vec3i;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;

public class EmptyVoxelCake implements VoxelCake {
    public static final Multimap<Object, Object> EMPTY_MULTIMAP = Multimaps.unmodifiableMultimap(MultimapBuilder.hashKeys(0).linkedListValues().build());
    public static final EmptyVoxelCake INSTANCE = new EmptyVoxelCake();
    @SuppressWarnings("unchecked")
    public static <K, V> Multimap<K, V> emptyMultimap() {
        return (Multimap<K, V>) EMPTY_MULTIMAP;
    }
    @Override
    public NbtCompound toNbt() {
        return new NbtCompound();
    }
    @Override
    public Multimap<CakeIngredient, BlockBox> getContent() {
        return emptyMultimap();
    }
    @Override
    public Vec3i getSize() {
        return Vec3i.ZERO;
    }
}
