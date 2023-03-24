package phoupraw.mcmod.createsdelight.item;

import com.nhoryzon.mc.farmersdelight.item.ConsumableItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.item.TooltipData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import phoupraw.mcmod.common.api.StatusEffectsTooltipData;

import java.util.Optional;
public class StatusEffectsItem extends ConsumableItem {
    public static ItemStack finishUsing(Item self, ItemStack stack, World world, LivingEntity user) {
//        if (!world.isClient()) {
//            self.affectConsumer(stack, world, user);
//        }

        ItemStack container = new ItemStack(stack.getItem().getRecipeRemainder());
        PlayerEntity player;
        if (stack.isFood()) {
            user.eatFood(world, stack);
        } else if (user instanceof PlayerEntity) {
            player = (PlayerEntity) user;
            if (player instanceof ServerPlayerEntity serverPlayer) {
                Criteria.CONSUME_ITEM.trigger(serverPlayer, stack);
            }

            player.incrementStat(Stats.USED.getOrCreateStat(self));
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
        }

        if (stack.isEmpty()) {
            return container;
        } else {
            if (user instanceof PlayerEntity) {
                player = (PlayerEntity) user;
                if (!player.getAbilities().creativeMode && !player.getInventory().insertStack(container)) {
                    player.dropItem(container, false);
                }
            }

            return stack;
        }
    }

    @Environment(EnvType.CLIENT)
    public static Optional<TooltipData> getTooltipData(Item self, ItemStack stack) {
        FoodComponent foodComponent = self.getFoodComponent();
        return foodComponent != null && !foodComponent.getStatusEffects().isEmpty() ? Optional.of(new StatusEffectsTooltipData(foodComponent.getStatusEffects())) : Optional.empty();
    }

    public StatusEffectsItem(Settings settings) {
        super(settings, false/*不用农夫乐事的模仿药水的工具提示，而是用我们自己的工具提示*/, false);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        return getTooltipData(this, stack);
    }

}
