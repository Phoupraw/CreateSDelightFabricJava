package phoupraw.mcmod.createsdelight.item;

import io.github.tropheusj.milk.Milk;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Box;
import phoupraw.mcmod.createsdelight.registry.CDBlocks;
import phoupraw.mcmod.createsdelight.registry.CDItems;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class PrintedCakeItem extends StatusEffectsBlockItem{

    public PrintedCakeItem() {
        this(CDBlocks.PRINTED_CAKE, CDItems.newSettings());
    }

    public PrintedCakeItem(Block block, Settings settings) {
        super(block, settings);
    }
    //@Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
//        super.appendStacks(group, stacks);
        var stack = getDefaultStack();
        Map<FluidVariant, Collection<Box>> content = new HashMap<>();
        content.put(FluidVariant.of(Milk.STILL_MILK), List.of(new Box(0,0,0,1,1,1)));
        CakeBlueprintItem.setContent(stack.getOrCreateNbt(),content);
        stacks.add(stack);
    }
}
