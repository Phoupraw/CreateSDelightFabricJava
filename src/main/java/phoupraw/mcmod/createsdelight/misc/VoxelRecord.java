package phoupraw.mcmod.createsdelight.misc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.CreateSDelight;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public record VoxelRecord(Map<BlockPos, BlockState> blocks, Vec3i size, BlockBox boundary) {
    public static VoxelRecord of(Map<BlockPos, BlockState> blocks, Vec3i size) {
        return new VoxelRecord(blocks, size, BlockBox.encompassPositions(blocks.keySet()).orElseThrow());
    }
    public static int compare(BlockState a, BlockState b) {
        int r = a.getRegistryEntry().getKey().orElseThrow().getValue().compareTo(b.getRegistryEntry().getKey().orElseThrow().getValue());
        if (r != 0) return r;
        List<Property<?>> properties = new ArrayList<>(a.getProperties());
        properties.sort(Comparator.comparing(Property::getName));
        for (Property<?> property : properties) {
            //已知a和b是同一种类型，且都是Comparable，但是不知道具体是什么类型，所以用嵌套Comparable泛型强制类型转换来避免类型检查和转换失败
            //noinspection unchecked
            r = ((Comparable<Comparable<Integer>>) a.get(property)).compareTo((Comparable<Integer>) b.get(property));
            if (r != 0) return r;
        }
        return 0;
    }
    public static VoxelRecord of(NbtCompound nbt, @Nullable RegistryEntryLookup<Block> blockLookup) {
        if (blockLookup == null) {
            blockLookup = Registries.BLOCK.getReadOnlyWrapper();
        }
        Vec3i size = NbtHelper.toBlockPos(nbt.getCompound("size"));
        NbtList nbtPallete = nbt.getList("pallete", NbtElement.COMPOUND_TYPE);
        List<BlockState> pallete = new ArrayList<>(nbtPallete.size());
        for (int i = 0; i < nbtPallete.size(); i++) {
            pallete.add(NbtHelper.toBlockState(blockLookup, nbtPallete.getCompound(i)));
        }
        Map<BlockPos, BlockState> blocks = new HashMap<>();
        try (var input = new ByteArrayInputStream(nbt.getByteArray("gzip")); var gzip = new GZIPInputStream(input)) {
            for (int i = 0; i < size.getX(); i++) {
                for (int j = 0; j < size.getY(); j++) {
                    for (int k = 0; k < size.getZ(); k++) {
                        int code = gzip.read();
                        if (code == 0) continue;
                        BlockState blockState = pallete.get(code - 1);
                        BlockPos pos = new BlockPos(i, j, k);
                        blocks.put(pos, blockState);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return of(blocks, size);
    }
    /**
     {@link WeakHashMap}没有{@link IdentityHashMap}的功能，我得手动重写防止编译器给我自动生成比较内容的{@link Object#equals}。
     @param obj the reference object with which to compare.
     @return 两者引用相等
     */
    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
    /**
     {@link WeakHashMap}没有{@link IdentityHashMap}的功能，我得手动重写防止编译器给我自动生成根据内容生成散列码的{@link Object#hashCode()}。
     @return {@link System#identityHashCode}
     */
    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
    public NbtCompound write(NbtCompound nbt) {
        List<BlockState> blocks = new ArrayList<>(new HashSet<>(this.blocks.values()));
        blocks.sort(VoxelRecord::compare);
        Map<BlockState, Integer> pallete = new HashMap<>();
        NbtList nbtPallete = new NbtList();
        for (BlockState blockState : blocks) {
            nbtPallete.add(NbtHelper.fromBlockState(blockState));
            pallete.put(blockState, pallete.size() + 1);
        }
        nbt.put("pallete", nbtPallete);
        try (var output = new ByteArrayOutputStream(); var gzip = new GZIPOutputStream(output)) {
            for (int i = 0; i < size.getX(); i++) {
                for (int j = 0; j < size.getY(); j++) {
                    for (int k = 0; k < size.getZ(); k++) {
                        BlockPos pos = new BlockPos(i, j, k);
                        BlockState blockState = this.blocks.get(pos);
                        if (blockState == null) {
                            gzip.write(0);
                        } else {
                            gzip.write(pallete.get(blockState));
                        }
                    }
                }
            }
            gzip.finish();
            nbt.putByteArray("gzip", output.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            CreateSDelight.LOGGER.fatal("pallete=" + pallete);
            CreateSDelight.LOGGER.fatal("blocks=" + this.blocks);
            throw e;
        }
        nbt.put("size", NbtHelper.fromBlockPos(new BlockPos(size)));
        return nbt;
    }
}
