package phoupraw.mcmod.createsdelight.cake;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.simibubi.create.content.schematics.SchematicWorld;
import net.minecraft.nbt.NbtByteArray;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.block.entity.PrintedCakeBlockEntity;
import phoupraw.mcmod.createsdelight.registry.CDRegistries;
import phoupraw.mcmod.createsdelight.registry.CSDCakeIngredients;

import java.util.Objects;

public interface VoxelCake {

    static VoxelCake of(Multimap<CakeIngredient, BlockBox> content, Vec3i size) {
        return new VoxelCakeRecord(content, size);
    }
    static @Nullable VoxelCake of(NbtCompound nbt) {
        if (!nbt.contains("content", NbtElement.COMPOUND_TYPE)
            || !nbt.contains("size", NbtElement.BYTE_ARRAY_TYPE)) {
            return null;
        }
        NbtCompound nbtContent = nbt.getCompound("content");
        Multimap<CakeIngredient, BlockBox> content = HashMultimap.create(nbtContent.getSize(), 16);
        for (String key : nbtContent.getKeys()) {
            CakeIngredient cakeIngredient = CDRegistries.CAKE_INGREDIENT.get(new Identifier(key));
            NbtList nbtBoxes = nbtContent.getList(key, NbtElement.BYTE_ARRAY_TYPE);
            for (NbtElement nbtBox : nbtBoxes) {
                if (nbtBox instanceof NbtByteArray nbtByteArray) {
                    content.put(cakeIngredient, PrintedCakeBlockEntity.array2box(nbtByteArray.getByteArray()));
                }
            }
        }
        int[] nbtSize = nbt.getIntArray("size");
        var size = new BlockPos(nbtSize[0], nbtSize[1], nbtSize[2]);
        return VoxelCake.of(content, size);
    }
    static VoxelCake of(StructureTemplate st, World world) {
        Vec3i size = st.getSize();
        SchematicWorld sw = new SchematicWorld(world);
        st.place(sw, BlockPos.ORIGIN, BlockPos.ORIGIN, new StructurePlacementData(), sw.getRandom(), 0);
        return of(world, BlockBox.create(BlockPos.ORIGIN, size));
    }
    static VoxelCake of(World world, BlockBox bound) {
        ListMultimap<CakeIngredient, BlockBox> content = MultimapBuilder.hashKeys().arrayListValues().build();
        Vec3i size = bound.getDimensions();
        BlockPos pos0 = new BlockPos(bound.getMinX(), bound.getMinY(), bound.getMinZ());
        for (int i = 0; i < size.getX(); i++) {
            for (int j = 0; j < size.getY(); j++) {
                for (int k = 0; k < size.getZ(); k++) {
                    BlockPos pos1 = pos0.add(i, j, k);
                    CakeIngredient ci = CSDCakeIngredients.LOOKUP.find(world, pos1, null);
                    if (ci != null) {
                        content.put(ci, new BlockBox(i, j, k, i + 1, j + 1, k + 1));
                    }
                }
            }
        }
        for (CakeIngredient ci : content.keySet()) {
            content.get(ci).sort(PrintedCakeBlockEntity.BLOCK_BOX_COMPARATOR);
        }
        return of(content, size);
    }
    Multimap<CakeIngredient, BlockBox> getContent();
    Vec3i getSize();
    default NbtCompound toNbt() {
        NbtCompound nbt = new NbtCompound();
        Vec3i size = getSize();
        if (getContent() == null || size == null) return nbt;
        NbtCompound nbtContent = new NbtCompound();
        for (var entry : getContent().asMap().entrySet()) {
            NbtList nbtBoxes = new NbtList();
            for (BlockBox box : entry.getValue()) {
                nbtBoxes.add(PrintedCakeBlockEntity.box2nbt(box));
            }
            nbtContent.put(Objects.requireNonNull(CDRegistries.CAKE_INGREDIENT.getId(entry.getKey()), entry.toString()).toString(), nbtBoxes);
        }
        nbt.put("content", nbtContent);
        nbt.putByteArray("size", new byte[]{(byte) size.getX(), (byte) size.getY(), (byte) size.getZ()});
        return nbt;
    }
    //default StructureTemplate toStructure() {
    //   var result= new StructureTemplate();
    //
    //   return result;
    //}
    //default String toJavaLiteral() {
    //    StringBuilder sb = new StringBuilder("VoxelCake.of(");
    //    MultimapBuilder.hashKeys().arrayListValues().build();
    //    sb
    //      .append(",new Vec3i(")
    //      .append(getSize().getX())
    //      .append(",")
    //      .append(getSize().getY())
    //      .append(",")
    //      .append(getSize().getZ())
    //      .append("))");
    //    return sb.toString();
    //}

}
