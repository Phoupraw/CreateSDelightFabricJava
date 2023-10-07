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
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.registry.CSDRegistries;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public record VoxelRecord(Map<BlockPos, Block> blocks, Vec3i size, BlockBox boundary) {
    public static final Map<String, VoxelRecord> ID_2_CAKE = new HashMap<>();
    public static final Map<VoxelRecord, String> CAKE_2_ID = new IdentityHashMap<>();
    public static final BlockBox EMPTY_BOX = BlockBox.create(Vec3i.ZERO, Vec3i.ZERO);
    public static final VoxelRecord EMPTY = new VoxelRecord(Map.of(), Vec3i.ZERO, EMPTY_BOX);
    public static VoxelRecord of(Map<BlockPos, Block> blocks, Vec3i size) {
        if (blocks.isEmpty()) return EMPTY;
        return new VoxelRecord(blocks, size, BlockBox.encompassPositions(blocks.keySet()).orElse(EMPTY_BOX));
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
    public static Pair<VoxelRecord, Iterable<BlockPos>> of(@NotNull World world, BlockBox bound, Iterable<BlockPos> posIterable) {
        return of(world, new BlockPos(bound.getMinX(), bound.getMinY(), bound.getMinZ()), new Vec3i(bound.getBlockCountX(), bound.getBlockCountY(), bound.getBlockCountZ()), posIterable);
    }
    public static Pair<VoxelRecord, Iterable<BlockPos>> of(@NotNull World world, BlockPos vertex000, Vec3i size, Iterable<BlockPos> posIterable) {
        Map<BlockPos, Block> blocks = new HashMap<>();
        Collection<BlockPos> got = new ArrayList<>();
        for (BlockPos pos : posIterable) {
            if (blocks.containsKey(pos)) continue;
            BlockState blockState = world.getBlockState(pos);
            if (blockState.isAir()) continue;
            blocks.put(pos.subtract(vertex000), blockState.getBlock());
            got.add(pos.toImmutable());
        }
        return Pair.of(of(blocks, size), got);
    }
    public static VoxelRecord of(@Nullable World world, NbtCompound nbt) {
        if (nbt.contains("id", NbtElement.STRING_TYPE)) {
            String id = nbt.getString("id");
            VoxelRecord voxelRecord = ID_2_CAKE.get(id);
            if (voxelRecord != null) {
                return voxelRecord;
            } else {
                CreateSDelight.LOGGER.error("找不到ID=%s对应的预定义体素糕点".formatted(id));
            }
        }
        Vec3i size = NbtHelper.toBlockPos(nbt.getCompound("size"));
        if (size.getX() <= 0 || size.getY() <= 0 || size.getZ() <= 0) {
            CreateSDelight.LOGGER.error("由于尺寸有维度不大于0，已返回空。");
            printNbt(world, nbt);
            return EMPTY;
        }
        NbtList nbtPallete = nbt.getList("pallete", NbtElement.STRING_TYPE);
        if (nbtPallete.isEmpty()) {
            CreateSDelight.LOGGER.error("由于调色盘为空，已返回空。");
            printNbt(world, nbt);
            return EMPTY;
        }
        List<Block> pallete = new ArrayList<>(nbtPallete.size());
        for (int i = 0; i < nbtPallete.size(); i++) {
            pallete.add(CSDRegistries.get(Registries.BLOCK, new Identifier(nbtPallete.getString(i))));
        }
        Map<BlockPos, Block> blocks = new HashMap<>();
        try (var input = new ByteArrayInputStream(nbt.getByteArray("gzip"));
             var gzip = new GZIPInputStream(input)) {
            for (int i = 0; i < size.getX(); i++) {
                for (int j = 0; j < size.getY(); j++) {
                    for (int k = 0; k < size.getZ(); k++) {
                        int code = gzip.read();
                        if (code == 0) continue;
                        if (code - 1 >= pallete.size()) {
                            CreateSDelight.LOGGER.catching(new IndexOutOfBoundsException("索引(%d)超出调色盘(%s)范围。".formatted(code - 1, pallete)));
                            CreateSDelight.LOGGER.error("已返回空。");
                            printNbt(world, nbt);
                            return EMPTY;
                        }
                        blocks.put(new BlockPos(i, j, k), pallete.get(code - 1));
                    }
                }
            }
        } catch (IOException e) {
            CreateSDelight.LOGGER.catching(e);
            CreateSDelight.LOGGER.error("已返回空。");
            printNbt(world, nbt);
            return EMPTY;
        }
        return of(blocks, size);
    }
    public static void printNbt(@Nullable World world, NbtCompound nbt) {
        CreateSDelight.LOGGER.error("nbt=" + nbt);
        if (world != null && world.isClient()) {
            nbt.getKeys().clear();
            CreateSDelight.LOGGER.error("检测到处于客户端，为防止错误消息刷屏，已将NBT清空。");
        }
    }
    public NbtCompound write(NbtCompound nbt) {
        if (CAKE_2_ID.containsKey(this)) {
            nbt.putString("id", CAKE_2_ID.get(this));
            return nbt;
        }
        List<Block> blocks = new ArrayList<>(new HashSet<>(this.blocks.values()));
        blocks.sort(VoxelRecord::compare);
        Map<Block, Integer> pallete = new HashMap<>();
        NbtList nbtPallete = new NbtList();
        for (Block block : blocks) {
            nbtPallete.add(NbtString.of(CSDRegistries.getId(Registries.BLOCK, block).toString()));
            pallete.put(block, pallete.size() + 1);
        }
        nbt.put("pallete", nbtPallete);
        try (var output = new ByteArrayOutputStream();
             var gzip = new GZIPOutputStream(output)) {
            for (int i = 0; i < size.getX(); i++) {
                for (int j = 0; j < size.getY(); j++) {
                    for (int k = 0; k < size.getZ(); k++) {
                        BlockPos pos = new BlockPos(i, j, k);
                        Block block = this.blocks.get(pos);
                        gzip.write(block == null ? 0 : pallete.get(block));
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
