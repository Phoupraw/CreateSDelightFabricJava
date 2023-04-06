package phoupraw.mcmod.common.api;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Matrix4f;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
/**
 显示带有概率的状态效果。当时长等于1游戏刻时，不会显示时长；当时长大于1游戏刻时，会以秒为单位显示精确到游戏刻的时长。当等级等于1时，不会显示等级。当概率等于100%时，不会显示概率。
 @see StatusEffectsTooltipData
 @see FoodComponent#getStatusEffects()
 @since 1.0.0 */
@Environment(EnvType.CLIENT)
public class StatusEffectsTooltipComponent implements net.minecraft.client.gui.tooltip.TooltipComponent {
    @Contract(mutates = "param1")
    public static void append(@NotNull List<Text> lines, @NotNull List<Pair<StatusEffectInstance, Float>> statusEffects) {
        for (var pair : statusEffects) {
            StatusEffectInstance effect = pair.getFirst();
            StatusEffect type = effect.getEffectType();
            MutableText line;
            int amplifier = effect.getAmplifier();
            Text name = type.getName();
            if (amplifier == 0) {
                line = Text.empty().append(name);
            } else {
                line = Text.translatable("potion.withAmplifier", name, Text.translatable("potion.potency." + amplifier));
            }
            if (effect.getDuration() > 1) {
                line = Text.translatable("potion.withDuration", line, Text.literal(Internationals.symbolToSeconds(effect.getDuration())));
            }
            if (pair.getSecond() < 1) {
                line.append(Text.literal(" " + DecimalFormat.getNumberInstance().format(pair.getSecond() * 100) + "%"));
            }
            lines.add(line.formatted(type.getCategory().getFormatting()));
        }
    }

    private final List<Pair<StatusEffectInstance, Float>> chancedEffects;
    private final List<Text> lines = new LinkedList<>();

    public StatusEffectsTooltipComponent(List<Pair<StatusEffectInstance, Float>> chancedEffects) {
        this.chancedEffects = chancedEffects;
        append(getLines(), chancedEffects);
    }

    @Override
    public int getHeight() {
        return getChancedEffects().size() * MinecraftClient.getInstance().textRenderer.fontHeight;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        boolean seen = false;
        int best = 0;
        for (var text : getLines()) {
            int width = textRenderer.getWidth(text);
            if (!seen || width > best) {
                seen = true;
                best = width;
            }
        }
        return textRenderer.fontHeight + (seen ? best : 0);
    }

    @Override
    public void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        int fontSize = textRenderer.fontHeight;
        int i = 0;
        for (var text : getLines()) {
            textRenderer.draw(text, x + fontSize, y + i * fontSize, 0xffffffff, false, matrix, vertexConsumers, false, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
            i++;
        }
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, MatrixStack matrices, ItemRenderer itemRenderer, int z) {
        int fontSize = textRenderer.fontHeight;
        int i = 0;
        for (var pair : getChancedEffects()) {
            var effect = pair.getFirst();
            StatusEffect type = effect.getEffectType();
            var sprite = MinecraftClient.getInstance().getStatusEffectSpriteManager().getSprite(type);
            RenderSystem.setShaderTexture(0, sprite.getAtlas().getId());
            RenderSystem.setShaderColor(1, 1, 1, 1);
            DrawableHelper.drawSprite(matrices, x, y + i * fontSize, 0, fontSize, fontSize, sprite);
            i++;
        }
    }

    public List<Pair<StatusEffectInstance, Float>> getChancedEffects() {
        return chancedEffects;
    }

    public List<Text> getLines() {
        return lines;
    }
}