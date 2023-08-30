package phoupraw.mcmod.createsdelight.item;

import io.github.tropheusj.milk.Milk;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Box;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrintedCakeItem extends StatusEffectsBlockItem {

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
            nbtContent.put(Registries.FLUID.getId(fluidVariant.getFluid()).toString(), nbtBoxes);
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

    public PrintedCakeItem() {
        this(CSDBlocks.PRINTED_CAKE, new FabricItemSettings());
    }

    public PrintedCakeItem(Block block, Settings settings) {
        super(block, settings);
    }

    //@Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
//        super.appendStacks(group, stacks);
        var stack = getDefaultStack();
        Map<FluidVariant, Collection<Box>> content = new HashMap<>();
        content.put(FluidVariant.of(Milk.STILL_MILK), List.of(new Box(0, 0, 0, 1, 1, 1)));
        setContent(stack.getOrCreateNbt(), content);
        stacks.add(stack);
    }
}
