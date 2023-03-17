package phoupraw.mcmod.createsdelight.item;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.nhoryzon.mc.farmersdelight.item.ConsumableItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
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
    public static Optional<net.minecraft.client.item.TooltipData> getTooltipData(Item self, ItemStack stack) {
        FoodComponent foodComponent = self.getFoodComponent();
        return foodComponent != null && !foodComponent.getStatusEffects().isEmpty() ? Optional.of(new TooltipData(foodComponent.getStatusEffects())) : Optional.empty();
    }

    public StatusEffectsItem(Settings settings) {
        super(settings, false/*不用农夫乐事的模仿药水的工具提示，而是用我们自己的工具提示*/, false);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Optional<net.minecraft.client.item.TooltipData> getTooltipData(ItemStack stack) {
        return getTooltipData(this, stack);
    }

    @Environment(EnvType.CLIENT)
    public record TooltipData(List<Pair<StatusEffectInstance, Float>> statusEffects) implements net.minecraft.client.item.TooltipData {}

    @Environment(EnvType.CLIENT)
    public static class TooltipComponent implements net.minecraft.client.gui.tooltip.TooltipComponent {
        public static @NotNull String symbolToSeconds(@Range(from = Long.MIN_VALUE, to = Long.MAX_VALUE) long ticks) {
            return new DecimalFormat("#.##").format(ticks / 20.0) + "s";
        }

        public final List<Pair<StatusEffectInstance, Float>> statusEffects;
        public final List<MutableText> texts = new ArrayList<>();

        public TooltipComponent(List<Pair<StatusEffectInstance, Float>> statusEffects) {
            this.statusEffects = statusEffects;
            for (Pair<StatusEffectInstance, Float> pair : statusEffects) {
                StatusEffectInstance effect = pair.getFirst();
                StatusEffect type = effect.getEffectType();
                MutableText text = Text.empty()
                  .append(type.getName())
                  .append(Text.translatable("potion.potency." + effect.getAmplifier()))
                  .formatted(type.getCategory().getFormatting());
                if (effect.getDuration() > 1) {
                    text.append(Text.empty()
                      .append(" " + symbolToSeconds(effect.getDuration()))
                      .formatted(Formatting.GRAY));
                }
                if (pair.getSecond() < 1) {
                    text.append(Text.empty()
                      .append(" " + DecimalFormat.getNumberInstance().format(pair.getSecond() * 100) + "%")
                      .formatted(Formatting.GRAY));
                }
                texts.add(text);
            }
        }

        @Override
        public int getHeight() {
            return statusEffects.size() * MinecraftClient.getInstance().textRenderer.fontHeight;
        }

        @Override
        public int getWidth(TextRenderer textRenderer) {
            boolean seen = false;
            int best = 0;
            for (MutableText text : texts) {
                int width = textRenderer.getWidth(text);
                if (!seen || width > best) {
                    seen = true;
                    best = width;
                }
            }
            return seen ? best : 0;
        }

        @Override
        public void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
            int fontSize = textRenderer.fontHeight;
            int i = 0;
            for (MutableText text : texts) {
                textRenderer.draw(text, x + fontSize, y + i * fontSize, 0xffffffff, false, matrix, vertexConsumers, false, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
                i++;
            }
        }

        @Override
        public void drawItems(TextRenderer textRenderer, int x, int y, MatrixStack matrices, ItemRenderer itemRenderer, int z) {
            int fontSize = textRenderer.fontHeight;
            int i = 0;
            for (Pair<StatusEffectInstance, Float> pair : statusEffects) {
                var effect = pair.getFirst();
                StatusEffect type = effect.getEffectType();
                var sprite = MinecraftClient.getInstance().getStatusEffectSpriteManager().getSprite(type);
                RenderSystem.setShaderTexture(0, sprite.getAtlas().getId());
                RenderSystem.setShaderColor(1, 1, 1, 1);
                DrawableHelper.drawSprite(matrices, x, y + i * fontSize, 0, fontSize, fontSize, sprite);
                i++;
            }
        }
    }

}
