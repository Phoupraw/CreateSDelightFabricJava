package phoupraw.mcmod.createsdelight.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;
import phoupraw.mcmod.createsdelight.registry.CSDItems;

public class PrintedCakeItem extends BlockItem {
    public static ItemStack of(VoxelCake voxelCake) {
        ItemStack itemStack = CSDItems.PRINTED_CAKE.getDefaultStack();
        NbtCompound blockEntityTag = new NbtCompound();
        blockEntityTag.put("voxelCake", voxelCake.toNbt());
        BlockItem.setBlockEntityNbt(itemStack, CSDBlockEntityTypes.PRINTED_CAKE, blockEntityTag);
        return itemStack;
    }
    public PrintedCakeItem() {
        this(CSDBlocks.PRINTED_CAKE, new FabricItemSettings());
    }

    public PrintedCakeItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        if (player != null && !player.isSneaking()) return ActionResult.PASS;
        return super.useOnBlock(context);
    }

}
