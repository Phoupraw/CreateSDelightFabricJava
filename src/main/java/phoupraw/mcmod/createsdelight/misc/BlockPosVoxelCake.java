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
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.registry.CSDRegistries;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class BlockPosVoxelCake implements VoxelCake {
    public static @Nullable BlockPosVoxelCake of(NbtCompound nbt) {
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
            return new BlockPosVoxelCake(edgeLen, content0);
        } catch (Exception e) {
            CreateSDelight.LOGGER.catching(e);
            return null;
        }
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
        return new BlockPosVoxelCake(bound.getBlockCountX(), content);
    }
    public final int edgeLen;
    public final Multimap<CakeIngredient, BlockPos> blockPosContent;
    public final Multimap<CakeIngredient, BlockBox> content;
    public BlockPosVoxelCake(int edgeLen, Multimap<CakeIngredient, BlockPos> blockPosContent) {
        this.edgeLen = edgeLen;
        this.blockPosContent = blockPosContent;
        this.content = MultimapBuilder.linkedHashKeys(blockPosContent.keySet().size()).arrayListValues().build();
        for (Map.Entry<CakeIngredient, BlockPos> entry : blockPosContent.entries()) {
            BlockPos pos = entry.getValue();
            content.put(entry.getKey(), BlockBox.create(pos, pos.add(1, 1, 1)));
        }
    }
    @Override
    public NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        nbt.putInt("edgeLen", edgeLen);
        NbtList nbtPallete = new NbtList();
        Map<CakeIngredient, Integer> pallete = new HashMap<>();
        for (CakeIngredient cakeIngredient : blockPosContent.keySet()) {
            nbtPallete.add(NbtString.of(CSDRegistries.getId(CSDRegistries.CAKE_INGREDIENT, cakeIngredient).toString()));
            pallete.put(cakeIngredient, pallete.size());
        }
        nbt.put("pallete", nbtPallete);
        byte[] cells = new byte[edgeLen * edgeLen * edgeLen];
        for (Map.Entry<CakeIngredient, BlockPos> entry : this.blockPosContent.entries()) {
            BlockPos pos = entry.getValue();
            cells[(pos.getX() * edgeLen + pos.getY()) * edgeLen + pos.getZ()] = (byte) (pallete.get(entry.getKey()) + 1);
        }
        nbt.putByteArray("cells", cells);
        return nbt;
    }
    @Override
    public Multimap<CakeIngredient, BlockBox> getContent() {
        return content;
    }
    @Override
    public Vec3i getSize() {
        return new Vec3i(edgeLen, edgeLen, edgeLen);
    }
}
