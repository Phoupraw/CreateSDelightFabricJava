package phoupraw.mcmod.createsdelight.misc;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;
import org.joml.Vector3i;
import org.joml.Vector3ic;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.registry.CSDRegistries;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BlockPosVoxelCake implements VoxelCake, BlockVoxelCake {
    public static @Nullable BlockPosVoxelCake of(NbtCompound nbt) {
        return ofGzip(nbt);
    }
    public static @Nullable BlockPosVoxelCake ofOld(NbtCompound nbt) {
        try {
            int edgeLen = nbt.getInt("edgeLen");
            if (edgeLen == 0) return null;
            NbtList nbtPallete = nbt.getList("pallete", NbtElement.STRING_TYPE);
            if (nbtPallete.isEmpty()) return null;
            CakeIngredient[] pallete = new CakeIngredient[nbtPallete.size()];
            for (int i = 0; i < nbtPallete.size(); i++) {
                pallete[i] = CSDRegistries.get(CSDRegistries.CAKE_INGREDIENT, new Identifier(nbtPallete.getString(i)));
            }
            byte[] cells = nbt.getByteArray("cells");
            if (cells.length == 0) return null;
            ListMultimap<CakeIngredient, BlockPos> content0 = MultimapBuilder.treeKeys(BlockPosVoxelCake::compare).arrayListValues().build();
            for (int x = 0; x < edgeLen; x++) {
                for (int y = 0; y < edgeLen; y++) {
                    for (int z = 0; z < edgeLen; z++) {
                        byte palleteId = cells[(x * edgeLen + y) * edgeLen + z];
                        if (palleteId == 0) continue;
                        CakeIngredient cakeIngredient = pallete[palleteId - 1];
                        content0.put(cakeIngredient, new BlockPos(x, y, z));
                    }
                }
            }
            return of(edgeLen, content0);
        } catch (Exception e) {
            CreateSDelight.LOGGER.catching(e);
            return null;
        }
    }
    public static @Nullable BlockPosVoxelCake ofGzip(NbtCompound nbt) {
        var triple = BlockVoxelCake.read(nbt);
        if (triple.getLeft().minComponent() == 0 || triple.getMiddle().isEmpty()) return null;
        Multimap<CakeIngredient, BlockPos> blockPosContent = MultimapBuilder.hashKeys().arrayListValues().build();
        for (Map.Entry<CakeIngredient, Vector3ic> entry : triple.getMiddle().entries()) {
            blockPosContent.put(entry.getKey(), new BlockPos(entry.getValue().x(), entry.getValue().y(), entry.getValue().z()));
        }
        return of(triple.getLeft().x(), blockPosContent, null, triple.getRight());
    }
    public static int compare(CakeIngredient a, CakeIngredient b) {
        return CSDRegistries.getId(CSDRegistries.CAKE_INGREDIENT, a).compareTo(CSDRegistries.getId(CSDRegistries.CAKE_INGREDIENT, b));
    }
    public static BlockPosVoxelCake of(World world, BlockBox bound) {
        ListMultimap<CakeIngredient, BlockPos> content = MultimapBuilder.treeKeys(BlockPosVoxelCake::compare).arrayListValues().build();
        BlockPos origin = new BlockPos(bound.getMinX(), bound.getMinY(), bound.getMinZ());
        for (int x = bound.getMinX(); x <= bound.getMaxX(); x++) {
            for (int y = bound.getMinY(); y <= bound.getMaxY(); y++) {
                for (int z = bound.getMinZ(); z <= bound.getMaxZ(); z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    CakeIngredient cakeIngredient = CakeIngredient.LOOKUP.find(world, pos, null);
                    if (cakeIngredient != null) {
                        content.put(cakeIngredient, pos.subtract(origin));
                    }
                }
            }
        }
        for (CakeIngredient cakeIngredient : content.keySet()) {
            Collections.sort(content.get(cakeIngredient));
        }
        return of(bound.getBlockCountX(), content);
    }
    public static BlockPosVoxelCake of(int edgeLen, Multimap<CakeIngredient, BlockPos> blockPosContent) {
        return of(edgeLen, blockPosContent, null, null);
    }
    public static BlockPosVoxelCake of(int edgeLen, Multimap<CakeIngredient, BlockPos> blockPosContent, @Nullable Multimap<CakeIngredient, BlockBox> content, @Nullable Map<Vector3ic, CakeIngredient> pos2ingredient) {
        if (content == null) {
            content = MultimapBuilder.linkedHashKeys(blockPosContent.keySet().size()).arrayListValues().build();
            for (Map.Entry<CakeIngredient, BlockPos> entry : blockPosContent.entries()) {
                BlockPos pos = entry.getValue();
                content.put(entry.getKey(), BlockBox.create(pos, pos.add(1, 1, 1)));
            }
        }
        if (pos2ingredient == null) {
            pos2ingredient = new HashMap<>(edgeLen * edgeLen * edgeLen);
            for (Map.Entry<CakeIngredient, BlockPos> entry : blockPosContent.entries()) {
                pos2ingredient.put(new Vector3i(entry.getValue().getX(), entry.getValue().getY(), entry.getValue().getZ()), entry.getKey());
            }
        }
        return new BlockPosVoxelCake(edgeLen, blockPosContent, content, pos2ingredient);
    }
    public final int edgeLen;
    public final Multimap<CakeIngredient, BlockPos> blockPosContent;
    public final Multimap<CakeIngredient, BlockBox> content;
    public final Map<Vector3ic, CakeIngredient> pos2ingredient;
    public static NbtCompound toNbt(BlockPosVoxelCake blockPosVoxelCake) {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("edgeLen", blockPosVoxelCake.edgeLen);
        NbtList nbtPallete = new NbtList();
        Map<CakeIngredient, Integer> pallete = new HashMap<>();
        for (CakeIngredient cakeIngredient : blockPosVoxelCake.blockPosContent.keySet()) {
            nbtPallete.add(NbtString.of(CSDRegistries.getId(CSDRegistries.CAKE_INGREDIENT, cakeIngredient).toString()));
            pallete.put(cakeIngredient, pallete.size());
        }
        nbt.put("pallete", nbtPallete);
        byte[] cells = new byte[blockPosVoxelCake.edgeLen * blockPosVoxelCake.edgeLen * blockPosVoxelCake.edgeLen];
        for (Map.Entry<CakeIngredient, BlockPos> entry : blockPosVoxelCake.blockPosContent.entries()) {
            BlockPos pos = entry.getValue();
            cells[(pos.getX() * blockPosVoxelCake.edgeLen + pos.getY()) * blockPosVoxelCake.edgeLen + pos.getZ()] = (byte) (pallete.get(entry.getKey()) + 1);
        }
        nbt.putByteArray("cells", cells);
        return nbt;
    }
    @ApiStatus.Internal
    public BlockPosVoxelCake(int edgeLen, Multimap<CakeIngredient, BlockPos> blockPosContent, Multimap<CakeIngredient, BlockBox> content, Map<Vector3ic, CakeIngredient> pos2ingredient) {
        this.edgeLen = edgeLen;
        this.blockPosContent = blockPosContent;
        this.content = content;
        this.pos2ingredient = pos2ingredient;
    }
    @Override
    public NbtCompound toNbt() {
        return BlockVoxelCake.super.write(new NbtCompound());
    }
    @Override
    public Multimap<CakeIngredient, BlockBox> getContent() {
        return content;
    }
    @Override
    public Vec3i getSize() {
        return new Vec3i(edgeLen, edgeLen, edgeLen);
    }
    @Override
    public Vector3ic size() {
        return new Vector3i(edgeLen);
    }
    @Override
    public @UnmodifiableView Collection<CakeIngredient> ingredients() {
        return blockPosContent.keySet();
    }
    @Override
    public @Nullable @UnmodifiableView Multimap<CakeIngredient, Vector3ic> ingredient2pos() {
        return null;
    }
    @Override
    public CakeIngredient getIngredient(int x, int y, int z) {
        return pos2ingredient.get(new Vector3i(x, y, z));
    }
    @Override
    public double hunger() {
        throw new UnsupportedOperationException("未实现");
    }
}
