package phoupraw.mcmod.createsdelight.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;

public class PrintedCakeItem extends BlockItem {

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
