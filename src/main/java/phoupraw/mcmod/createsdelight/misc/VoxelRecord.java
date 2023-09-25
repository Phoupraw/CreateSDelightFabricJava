package phoupraw.mcmod.createsdelight.misc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.*;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Property;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.registry.CSDRegistries;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public record VoxelRecord(Map<BlockPos, Block> blocks/*TODO 键改成Block*/, Vec3i size, BlockBox boundary) {
    public static VoxelRecord of(Map<BlockPos, Block> blocks, Vec3i size) {
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
    public static int compare(Block a, Block b) {
        return CSDRegistries.getId(Registries.BLOCK, a).compareTo(CSDRegistries.getId(Registries.BLOCK, b));
    }
    public static VoxelRecord of(NbtCompound nbt) {
        Vec3i size = NbtHelper.toBlockPos(nbt.getCompound("size"));
        NbtList nbtPallete = nbt.getList("pallete", NbtElement.STRING_TYPE);
        List<Block> pallete = new ArrayList<>(nbtPallete.size());
        for (int i = 0; i < nbtPallete.size(); i++) {
            pallete.add(CSDRegistries.get(Registries.BLOCK, new Identifier(nbtPallete.getString(i))));
        }
        Map<BlockPos, Block> blocks = new HashMap<>();
        try (var input = new ByteArrayInputStream(nbt.getByteArray("gzip")); var gzip = new GZIPInputStream(input)) {
            for (int i = 0; i < size.getX(); i++) {
                for (int j = 0; j < size.getY(); j++) {
                    for (int k = 0; k < size.getZ(); k++) {
                        int code = gzip.read();
                        if (code == 0) continue;
                        blocks.put(new BlockPos(i, j, k), pallete.get(code - 1));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return of(blocks, size);
    }
    public NbtCompound write(NbtCompound nbt) {
        List<Block> blocks = new ArrayList<>(new HashSet<>(this.blocks.values()));
        blocks.sort(VoxelRecord::compare);
        Map<Block, Integer> pallete = new HashMap<>();
        NbtList nbtPallete = new NbtList();
        for (Block block : blocks) {
            nbtPallete.add(NbtString.of(CSDRegistries.getId(Registries.BLOCK, block).toString()));
            pallete.put(block, pallete.size() + 1);
        }
        nbt.put("pallete", nbtPallete);
        try (var output = new ByteArrayOutputStream(); var gzip = new GZIPOutputStream(output)) {
            for (int i = 0; i < size.getX(); i++) {
                for (int j = 0; j < size.getY(); j++) {
                    for (int k = 0; k < size.getZ(); k++) {
                        BlockPos pos = new BlockPos(i, j, k);
                        Block block = this.blocks.get(pos);
                        if (block == null) {
                            gzip.write(0);
                        } else {
                            gzip.write(pallete.get(block));
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
