package phoupraw.mcmod.createsdelight.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Optional;
public class StatusEffectsBlockItem extends BlockItem {
    public StatusEffectsBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        return StatusEffectsItem.finishUsing(this, stack, world, user);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        return StatusEffectsItem.getTooltipData(this, stack);
    }

}
