package phoupraw.mcmod.createsdelight.block.entity;

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
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.registry.CDBETypes;
import phoupraw.mcmod.createsdelight.registry.CDRegistries;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class PrintedCakeBE extends SmartTileEntity implements Nameable {

public static final Comparator<BlockBox> BLOCK_BOX_COMPARATOR = Comparator
  .comparingInt(BlockBox::getMinY)
  .reversed()
  .thenComparingInt(BlockBox::getMinX)
  .thenComparingInt(BlockBox::getMinZ);

public static @Nullable VoxelCake nbt2content(ItemStack itemStack) {
    NbtCompound blockEntityTag = BlockItem.getBlockEntityNbt(itemStack);
    if (blockEntityTag == null) return null;
    return nbt2content(blockEntityTag);
}

public static @Nullable VoxelCake nbt2content(NbtCompound blockEntityTag) {
    if (blockEntityTag.contains("predefined", NbtElement.STRING_TYPE)) {
        Identifier id = new Identifier(blockEntityTag.getString("predefined"));
        //StructureTemplate st =
        return CDRegistries.PREDEFINED_CAKE.get(id);
    }
    return VoxelCake.of(blockEntityTag.getCompound("voxelCake"));
}

public static BlockBox nbt2box(int[] ar) {
    return new BlockBox(ar[0], ar[1], ar[2], ar[3], ar[4], ar[5]);
}

public static void writeContent(PrintedCakeBE be, NbtCompound tag, boolean clientPacket) {
    Identifier predefined = be.predefined;
    if (predefined != null) {
        tag.putString("predefined", predefined.toString());
        return;
    }
    if (be.getVoxelCake() != null) {
        tag.put("voxelCake", be.getVoxelCake().toNbt());
    }
    //var content = be.getContent();
    //Vec3i size = be.getSize();
    //if (content == null || size == null) return;
    //NbtCompound nbtContent = new NbtCompound();
    //for (var entry : content.asMap().entrySet()) {
    //    NbtList nbtBoxes = new NbtList();
    //    for (BlockBox box : entry.getValue()) {
    //        nbtBoxes.add(box2nbt(box));
    //    }
    //    nbtContent.put(Objects.requireNonNull(CDRegistries.CAKE_INGREDIENT.getId(entry.getKey()), entry.toString()).toString(), nbtBoxes);
    //}
    //tag.put("content", nbtContent);
    //tag.putIntArray("size", new int[]{size.getX(), size.getY(), size.getZ()});
}

public static NbtIntArray box2nbt(BlockBox box) {
    return new NbtIntArray(new int[]{box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ()});
}

public static void readContent(PrintedCakeBE be, NbtCompound tag, boolean clientPacket) {
    be.setVoxelCake(nbt2content(tag));
    be.setShape(null);
    var world = be.getWorld();
    if (world == null) return;
    if (world.isClient()) {
        be.setBakedModel(null);
    }
    world.updateListeners(be.getPos(), be.getCachedState(), be.getCachedState(), Block.REDRAW_ON_MAIN_THREAD);
}

public @Nullable Identifier predefined;
@Nullable
private VoxelCake voxelCake;
private @Nullable Text customName;

private @Nullable VoxelShape shape = null;
@Environment(EnvType.CLIENT)

private @Nullable BakedModel bakedModel;

//private @Nullable Vec3i size;
//
//private @Nullable Multimap<CakeIngredient, BlockBox> content;

public PrintedCakeBE(BlockPos pos, BlockState state) {
    this(CDBETypes.PRINTED_CAKE, pos, state);
}

public PrintedCakeBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
    super(type, pos, state);
}

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

//public @Nullable Multimap<CakeIngredient, BlockBox> getContent() {
//    return content;
//}
//
//public void setContent(@Nullable Multimap<CakeIngredient, BlockBox> content) {
//    this.content = content;
//    if (content == null) return;
//    for (CakeIngredient key : content.keySet()) {
//        if (content.get(key) instanceof List<BlockBox> list) {
//            list.sort(BLOCK_BOX_COMPARATOR);
//        }
//    }
//    sendData();
//}
//
//public @Nullable Vec3i getSize() {
//    return size;
//}
//
//public void setSize(@Nullable Vec3i size) {
//    this.size = size;
//}

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

public @Nullable VoxelCake getVoxelCake() {
    return voxelCake;
}

public void setVoxelCake(@Nullable VoxelCake voxelCake) {
    this.voxelCake = voxelCake;
    if (voxelCake == null) return;
    for (CakeIngredient key : voxelCake.getContent().keySet()) {
        if (voxelCake.getContent().get(key) instanceof List<BlockBox> list) {
            list.sort(BLOCK_BOX_COMPARATOR);
        }
    }
    sendData();
}

}
