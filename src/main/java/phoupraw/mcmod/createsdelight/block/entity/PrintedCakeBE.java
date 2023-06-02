package phoupraw.mcmod.createsdelight.block.entity;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.registry.CDBETypes;
import phoupraw.mcmod.createsdelight.registry.CDRegistries;

import java.util.*;

public class PrintedCakeBE extends SmartTileEntity implements Nameable {

private @Nullable Text customName;

@Override
public Text getName() {
    return Objects.requireNonNullElse(getCustomName(), getCachedState().getBlock().getName());
}

@Nullable
@Override
public Text getCustomName() {
    return customName;
}

public void setCustomName(@Nullable Text customName) {
    this.customName = customName;
}
//public static void writeContent(PrintedCakeBE be, NbtCompound tag, boolean clientPacket) {
//    if (be._content == null) return;
//    NbtCompound nbtContent = new NbtCompound();
//    for (Map.Entry<CakeIngredient, Collection<Box>> entry : be._content.entrySet()) {
//        NbtList nbtBoxes = new NbtList();
//        for (Box box : entry.getValue()) {
//            nbtBoxes.add(CakeBlueprintItem.box2nbt(box));
//        }
//        nbtContent.put(Objects.requireNonNull(CDRegistries.CAKE_INGREDIENT.getId(entry.getKey()), entry.toString()).toString(), nbtBoxes);
//    }
//    tag.put("content", nbtContent);
//}
//
//public static void readContent(PrintedCakeBE be, NbtCompound tag, boolean clientPacket) {
//    if (!tag.contains("content", NbtElement.COMPOUND_TYPE)) {
//        be._content = null;
//        return;
//    }
//    be._content = new HashMap<>();
//    NbtCompound nbtContent = tag.getCompound("content");
//    for (String key : nbtContent.getKeys()) {
//        CakeIngredient cakeIngredient = CDRegistries.CAKE_INGREDIENT.get(new Identifier(key));
//        Collection<Box> boxes = new LinkedList<>();
//        be._content.put(cakeIngredient, boxes);
//        NbtList nbtBoxes = nbtContent.getList(key, NbtElement.LIST_TYPE);
//        for (int i = 0; i < nbtBoxes.size(); i++) {
//            NbtList nbtBox = nbtBoxes.getList(i);
//            boxes.add(CakeBlueprintItem.nbt2box(nbtBox));
//        }
//    }
//}

public static NbtIntArray box2nbt(BlockBox box) {
    return new NbtIntArray(new int[]{box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ()});
}

public static BlockBox nbt2box(int[] ar) {
    return new BlockBox(ar[0], ar[1], ar[2], ar[3], ar[4], ar[5]);
}

public static void writeContent(PrintedCakeBE be, NbtCompound tag, boolean clientPacket) {
    var content = be.getContent();
    Vec3i size = be.getSize();
    if (content == null || size == null) return;
    NbtCompound nbtContent = new NbtCompound();
    for (var entry : content.asMap().entrySet()) {
        NbtList nbtBoxes = new NbtList();
        for (BlockBox box : entry.getValue()) {
            nbtBoxes.add(box2nbt(box));
        }
        nbtContent.put(Objects.requireNonNull(CDRegistries.CAKE_INGREDIENT.getId(entry.getKey()), entry.toString()).toString(), nbtBoxes);
    }
    tag.put("content", nbtContent);
    tag.putIntArray("size", new int[]{size.getX(), size.getY(), size.getZ()});
}

public static void readContent(PrintedCakeBE be, NbtCompound tag, boolean clientPacket) {
    var pair = nbt2content(tag);
    if (pair == null) {
        be.setContent(null);
        be.setSize(null);
    } else {
        be.setContent(pair.getKey());
        be.setSize(pair.getValue());
    }
    be.setShape(null);
    var world = be.getWorld();
    if (world == null) return;
    if (world.isClient()) {
        be.setBakedModel(null);
    }
    world.updateListeners(be.getPos(), be.getCachedState(), be.getCachedState(), Block.REDRAW_ON_MAIN_THREAD);
}

public static @Nullable Map.Entry<Multimap<CakeIngredient, BlockBox>, Vec3i> nbt2content(ItemStack itemStack) {
    NbtCompound blockEntityTag = BlockItem.getBlockEntityNbt(itemStack);
    if (blockEntityTag == null) return null;
    return nbt2content(blockEntityTag);
}

public static @Nullable Map.Entry<Multimap<CakeIngredient, BlockBox>, Vec3i> nbt2content(NbtCompound blockEntityTag) {
    if (!blockEntityTag.contains("content", NbtElement.COMPOUND_TYPE)
      || !blockEntityTag.contains("size", NbtElement.INT_ARRAY_TYPE)) {
        return null;
    }
    NbtCompound nbtContent = blockEntityTag.getCompound("content");
    Multimap<CakeIngredient, BlockBox> content = HashMultimap.create(nbtContent.getSize(), 16);
    for (String key : nbtContent.getKeys()) {
        CakeIngredient cakeIngredient = CDRegistries.CAKE_INGREDIENT.get(new Identifier(key));
        NbtList nbtBoxes = nbtContent.getList(key, NbtElement.INT_ARRAY_TYPE);
        for (int i = 0; i < nbtBoxes.size(); i++) {
            content.put(cakeIngredient, nbt2box(nbtBoxes.getIntArray(i)));
        }
    }
    int[] nbtSize = blockEntityTag.getIntArray("size");
    var size = new BlockPos(nbtSize[0], nbtSize[1], nbtSize[2]);
    return Map.entry(content, size);
}

public UUID uuid = new UUID(0, 0);
//@Nullable
//@Deprecated
//public Map<CakeIngredient, Collection<Box>> _content = null;
@Nullable
private VoxelShape shape = null;
@Environment(EnvType.CLIENT)
@Nullable
private BakedModel bakedModel;
//@Environment(EnvType.CLIENT)
public boolean caching;
@Nullable
private Vec3i size;
@Nullable
private Multimap<CakeIngredient, BlockBox> content;

public PrintedCakeBE(BlockPos pos, BlockState state) {
    this(CDBETypes.PRINTED_CAKE, pos, state);
}

public PrintedCakeBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
}

@Override
public void addBehaviours(List<TileEntityBehaviour> behaviours) {

}

@Override
protected void write(NbtCompound tag, boolean clientPacket) {
    super.write(tag, clientPacket);
    writeContent(this, tag, clientPacket);
    if (getCustomName() != null) {
        tag.putString("CustomName", Text.Serializer.toJson(getCustomName()));
    }
}

@Override
protected void read(NbtCompound tag, boolean clientPacket) {
    super.read(tag, clientPacket);
    readContent(this, tag, clientPacket);
    if (tag.contains("CustomName", NbtElement.STRING_TYPE)) {
        setCustomName(Text.Serializer.fromJson(tag.getString("CustomName")));
    } else {
        setCustomName(null);
    }
}

public @Nullable VoxelShape getShape() {
    return shape;
}

public void setShape(@Nullable VoxelShape shape) {
    this.shape = shape;
}

public @Nullable BakedModel getBakedModel() {
    return bakedModel;
}

public void setBakedModel(@Nullable BakedModel bakedModel) {
    this.bakedModel = bakedModel;
}

public @Nullable Vec3i getSize() {
    return size;
}

public void setSize(@Nullable Vec3i size) {
    this.size = size;
}

public @Nullable Multimap<CakeIngredient, BlockBox> getContent() {
    return content;
}

public void setContent(@Nullable Multimap<CakeIngredient, BlockBox> content) {
    this.content = content;
    if (content == null) return;
    for (CakeIngredient key : content.keySet()) {
        if (content.get(key) instanceof List<BlockBox> list) {
            list.sort(BLOCK_BOX_COMPARATOR);
        }
    }
}

public static final Comparator<BlockBox> BLOCK_BOX_COMPARATOR = Comparator
  .comparingInt(BlockBox::getMinY)
  .reversed()
  .thenComparingInt(BlockBox::getMinX)
  .thenComparingInt(BlockBox::getMinZ);

}
