package phoupraw.mcmod.createsdelight.item;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import phoupraw.mcmod.createsdelight.misc.VoxelRecord;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.CSDItems;

public class VoxelCakeItem {
    public static ItemStack of(VoxelRecord voxelRecord) {
        ItemStack itemStack = CSDItems.MADE_VOXEL.getDefaultStack();
        NbtCompound blockEntityNbt = new NbtCompound();
        blockEntityNbt.put("voxelRecord", voxelRecord.write(new NbtCompound()));
        BlockItem.writeBlockEntityNbtToStack(itemStack, CSDBlockEntityTypes.MADE_VOXEL, blockEntityNbt);
        return itemStack;
    }
}
