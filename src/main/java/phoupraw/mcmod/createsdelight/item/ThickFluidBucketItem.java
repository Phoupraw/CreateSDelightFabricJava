package phoupraw.mcmod.createsdelight.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class ThickFluidBucketItem extends BlockItem implements FluidModificationItem {
    public ThickFluidBucketItem(Block block, Settings settings) {
        super(block, settings);
    }
    @Override
    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        if (!world.isInBuildLimit(pos) || !world.isAir(pos)) return false;
        BlockState blockState = this.getBlock().getDefaultState();
        world.setBlockState(pos, blockState, 3);
        world.emitGameEvent(player, GameEvent.FLUID_PLACE, pos);
        world.playSound(player, pos, getPlaceSound(blockState), SoundCategory.BLOCKS, 1, 1);
        return true;
    }
    @Override
    public String getTranslationKey() {
        return this.getOrCreateTranslationKey();
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ActionResult result = super.useOnBlock(context);
        PlayerEntity player = context.getPlayer();
        if (result.isAccepted() && player != null) {
            ItemUsage.exchangeStack(player.getStackInHand(context.getHand()), player, Items.BUCKET.getDefaultStack());
        }
        return result;
    }
}
