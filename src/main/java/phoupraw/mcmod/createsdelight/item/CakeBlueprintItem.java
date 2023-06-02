package phoupraw.mcmod.createsdelight.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.*;
import phoupraw.mcmod.createsdelight.registry.CDItems;

import java.nio.file.Path;
import java.util.*;
public class CakeBlueprintItem extends Item {
    //    public static final LoadingCache<UUID,Map<FluidVariant, Collection<Box>>> CACHE = CacheBuilder.newBuilder().build(new CacheLoader<>() {
    //        @Override
    //        public @NotNull Map<FluidVariant, Collection<Box>> load(@NotNull UUID uuid) {
    //            MinecraftServer server;
    //            return null;
    //        }
    //    });
    public static Path getSchemDir() {
        Path dir = FabricLoader.getInstance().getGameDir().resolve("schematics");
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            dir = dir.resolve("uploaded");
        }
        return dir;
    }

    @Contract(pure = true)
    public static @NotNull Map<FluidVariant, Collection<Box>> getContent(@Nullable NbtCompound root) {
        Map<FluidVariant, Collection<Box>> parsedContent = new HashMap<>();
        if (root == null) return parsedContent;
        var nbtContent = root.getCompound("content");
        for (String key : nbtContent.getKeys()) {
            var nbtBoxes = nbtContent.getList(key, NbtElement.LIST_TYPE);
            Collection<Box> parsedBoxes = new LinkedList<>();
            for (int i = 0; i < nbtBoxes.size(); i++) {
                var box = nbtBoxes.getList(i);
                Box parsedBox = nbt2box(box);
                parsedBoxes.add(parsedBox);
            }
            parsedContent.put(FluidVariant.of(Registry.FLUID.get(new Identifier(key))), parsedBoxes);
        }
        return parsedContent;
    }

    @NotNull
    public static Box nbt2box(NbtList nbtBox) {
        return new Box(nbtBox.getDouble(0), nbtBox.getDouble(1), nbtBox.getDouble(2), nbtBox.getDouble(3), nbtBox.getDouble(4), nbtBox.getDouble(5));
    }

    public static void setContent(@NotNull NbtCompound root, @Nullable Map<FluidVariant, Collection<Box>> content) {
        if (content == null) {
            root.remove("content");
            return;
        }
        var nbtContent = new NbtCompound();
        for (Map.Entry<FluidVariant, Collection<Box>> entry : content.entrySet()) {
            FluidVariant fluidVariant = entry.getKey();
            Collection<Box> boxes = entry.getValue();
            var nbtBoxes = new NbtList();
            for (Box box : boxes) {
                NbtList nbtBox = box2nbt(box);
                nbtBoxes.add(nbtBox);
            }
            nbtContent.put(Registry.FLUID.getId(fluidVariant.getFluid()).toString(), nbtBoxes);
        }
        root.put("content", nbtContent);
    }

    @NotNull
    public static NbtList box2nbt(Box box) {
        var nbtBox = new NbtList();
        nbtBox.add(NbtDouble.of(box.minX));
        nbtBox.add(NbtDouble.of(box.minY));
        nbtBox.add(NbtDouble.of(box.minZ));
        nbtBox.add(NbtDouble.of(box.maxX));
        nbtBox.add(NbtDouble.of(box.maxY));
        nbtBox.add(NbtDouble.of(box.maxZ));
        return nbtBox;
    }

    @Contract(pure = true)
    public static @NotNull Collection<@NotNull Box> getSimplified(@NotNull Collection<@NotNull Box> raw) {
        List<Box> simplified = new LinkedList<>(raw);
        while (true) {
            Box box1 = null, box2 = null, merged = null;
            for (Box t1 : simplified) {
                for (Box t2 : simplified) {
                    if (t1.minY == t2.minY && t1.maxY == t2.maxY && t1.minZ == t2.minZ && t1.maxZ == t2.maxZ && t1.maxX == t2.minX || t1.minX == t2.minX && t1.maxX == t2.maxX && t1.minZ == t2.minZ && t1.maxZ == t2.maxZ && t1.maxY == t2.minY || t1.minX == t2.minX && t1.maxX == t2.maxX && t1.minY == t2.minY && t1.maxY == t2.maxY && t1.maxZ == t2.minZ) {
                        box1 = t1;
                        box2 = t2;
                        merged = new Box(t1.minX, t1.minY, t1.minZ, t2.maxX, t2.maxY, t2.maxZ);
                    }
                }
            }
            if (merged == null) break;
            simplified.remove(box1);
            simplified.remove(box2);
            simplified.add(merged);
        }
        //        simplified.sort(CakeBlueprintItem::compare);
        return simplified;
    }

    @Contract(pure = true)
    public static int compare(@NotNull Box a, @NotNull Box b) {
        return Double.compare(a.minY, b.minY);
    }

    public static @NotNull List<Map.@Unmodifiable Entry<FluidVariant, Box>> getSequence(@NotNull Map<FluidVariant, Collection<Box>> map) {
        List<Map.Entry<FluidVariant, Box>> sequence = new ArrayList<>();
        for (var entry : map.entrySet()) {
            for (Box box : entry.getValue()) {
                sequence.add(Map.entry(entry.getKey(), box));
            }
        }
        sequence.sort(Map.Entry.comparingByValue(CakeBlueprintItem::compare));
        return sequence;
    }

    @Contract("null -> null")
    public static @Nullable Map.@Unmodifiable Entry<FluidVariant, Box> getNextStep(@Nullable NbtCompound root) {
        if (root == null) return null;
        var sequence = CakeBlueprintItem.getSequence(CakeBlueprintItem.getContent(root));
        int index = getIndex(root);
        if (index < 0 || index >= sequence.size()) return null;
        return sequence.get(index);
    }

    public static long getAmount(@NotNull Box box) {
        double amount = box.getXLength() * box.getYLength() * box.getZLength() * FluidConstants.BUCKET;
        double integer = Math.floor(amount);
        double decimal = amount - integer;
        return (long) (integer + (Math.random() > decimal ? 0 : 1));
    }

    public static int getIndex(@UnmodifiableView @NotNull NbtCompound root) {
        return root.getInt("index");
    }

    public static void setIndex(@NotNull NbtCompound root, @Range(from = 0, to = Integer.MAX_VALUE) int index) {
        root.putInt("index", index);
    }

    public CakeBlueprintItem() {
        this(CDItems.newSettings().maxCount(1));
    }

    public CakeBlueprintItem(Settings properties) {
        super(properties);
    }

}
