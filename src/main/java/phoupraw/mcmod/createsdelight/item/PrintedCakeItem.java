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

import java.util.Map;

public class PrintedCakeItem extends BlockItem {
    public static final Map<String, String> PREDEFINEDS = Map.of(
      "brownie", "{voxelCake: {pallete: [\"createsdelight:chocolate\", \"createsdelight:cream_block\"], gzip: [B; 31B, -117B, 8B, 0B, 0B, 0B, 0B, 0B, 0B, -1B, -19B, -108B, 75B, 14B, -128B, 32B, 16B, 67B, -89B, -36B, -1B, -48B, -118B, -63B, 5B, 3B, 12B, 31B, 33B, 74B, -20B, -37B, 53B, -95B, -112B, -50B, 7B, 17B, 66B, -56B, -97B, 65B, -32B, -42B, 46B, 80B, -46B, -64B, 37B, 49B, -22B, -9B, 94B, 68B, -9B, 117B, -6B, -3B, 97B, -61B, -81B, -11B, -20B, -4B, 105B, 61B, 94B, -15B, 23B, -13B, 38B, -7B, 117B, 41B, -108B, -34B, 45B, 63B, 42B, -2B, 92B, -2B, 40B, -78B, -46B, -125B, -17B, 63B, -103B, -1B, 25B, -7B, -37B, -5B, 47B, -10B, 8B, -52B, -98B, -65B, -43B, -11B, -21B, -49B, 111B, 47B, -64B, -41B, -6B, -73B, 36B, -65B, -79B, 0B, -69B, -19B, 127B, -51B, -97B, -101B, 127B, 107B, 4B, -6B, -21B, 127B, 118B, 0B, -21B, -2B, -17B, -90B, -9B, 93B, 57B, 111B, 54B, 63B, 33B, -124B, 40B, 14B, 65B, -80B, -74B, -100B, 0B, 16B, 0B, 0B], size: [B; 16B, 16B, 16B]}, id: \"createsdelight:printed_cake\"}"
    );
    public static ItemStack of(VoxelCake voxelCake) {
        ItemStack itemStack = CSDItems.PRINTED_CAKE.getDefaultStack();
        NbtCompound blockEntityTag = new NbtCompound();
        blockEntityTag.put("voxelCake", voxelCake.toNbt());
        BlockItem.setBlockEntityNbt(itemStack, CSDBlockEntityTypes.PRINTED_CAKE, blockEntityTag);
        return itemStack;
    }
    public static String getTranslationKey(String cakeName) {
        return CSDItems.PRINTED_CAKE.getTranslationKey() + "." + cakeName;
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
