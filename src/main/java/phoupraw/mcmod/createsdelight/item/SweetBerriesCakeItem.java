package phoupraw.mcmod.createsdelight.item;

import com.nhoryzon.mc.farmersdelight.registry.EffectsRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
import phoupraw.mcmod.createsdelight.registry.MyItems;
import phoupraw.mcmod.createsdelight.registry.MyStatusEffects;
public class SweetBerriesCakeItem extends StatusEffectsBlockItem {
    public SweetBerriesCakeItem() {
        this(MyBlocks.SWEET_BERRIES_CAKE, MyItems.newSettings()
          .maxCount(64)
          .food(new FoodComponent.Builder()
            .hunger(5)
            .saturationModifier(0.5f)
            .statusEffect(new StatusEffectInstance(MyStatusEffects.SATIATION, 1, 3), 1f)
            .statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 2, 0), 1f)
            .statusEffect(new StatusEffectInstance(EffectsRegistry.COMFORT.get(), 20 * 60 * 2, 0), 1f)
            .build()
          ));
    }

    public SweetBerriesCakeItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return super.useOnBlock(context);
    }

    @Nullable
    @Override
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        BlockPos blockPos = context.getHitResult().getBlockPos();
        while (true) {
            var blockState = context.getWorld().getBlockState(blockPos);
            if (!blockState.isOf(MyBlocks.SWEET_BERRIES_CAKE) || blockState.get(Properties.AGE_3) < Properties.AGE_3_MAX) return new ItemPlacementContext(context.getWorld(), context.getPlayer(), context.getHand(), context.getStack(), context.getHitResult().withBlockPos(blockPos));
            blockPos = blockPos.up();
        }
    }
}
