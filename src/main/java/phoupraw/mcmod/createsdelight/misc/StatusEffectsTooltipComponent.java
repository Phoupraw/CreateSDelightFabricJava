package phoupraw.mcmod.createsdelight.misc;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 显示带有概率的状态效果。当时长等于1游戏刻时，不会显示时长；当时长大于1游戏刻时，会以秒为单位显示精确到游戏刻的时长。当等级等于1时，不会显示等级。当概率等于100%时，不会显示概率。
 * @see StatusEffectsTooltipData
 * @see FoodComponent#getStatusEffects()
 * @since 1.0.0
 */
@Environment(EnvType.CLIENT)
public class StatusEffectsTooltipComponent implements TooltipComponent {

    public static final String MINUTE = "text.time.minute";
    public static final String SECOND = "text.time.second";
    public static final String TICK = "text.time.tick";
    public static final Set<StatusEffect> NO_AMPLIFIER = new HashSet<>(List.of(StatusEffects.NIGHT_VISION, StatusEffects.BLINDNESS, StatusEffects.NAUSEA, StatusEffects.FIRE_RESISTANCE, StatusEffects.WATER_BREATHING, StatusEffects.INVISIBILITY, StatusEffects.GLOWING, StatusEffects.SLOW_FALLING, StatusEffects.DOLPHINS_GRACE));
    public static final int BREADTH = 9;
    public static boolean gradientColor = false;
    public static boolean gradientColor2 = false;
    public static boolean outline = true;

    public static MutableText translate(int ticks) {
        boolean trim = true;
        if (ticks == 0) return Text.literal("0");
        int ticksAbs = Math.abs(ticks);
        int m = ticksAbs / 20 / 60, s = ticksAbs / 20 % 60, t = ticksAbs % 20;
        MutableText text = Text.empty();
        if (ticks < 0) text.append("-");
        if (m != 0 || !trim) text.append(Text.translatableWithFallback(MINUTE, m + "m", m));
        if (s != 0 || !trim) text.append(Text.translatableWithFallback(SECOND, s + "s", s));
        if (t != 0 || !trim) text.append(Text.translatableWithFallback(TICK, t + "t", t));
        return text;
    }

    public static double toDoubleExact(long value) {
        if ((long) (double) value == value) {return (double) value;}
        throw new ArithmeticException("long->double precision overflow");
    }

    public static int gradientRgb(int rgb1, int rgb2, double timePeriod, double initPhase) {
        return gradientRgb(rgb1, rgb2, timePeriod, initPhase, toDoubleExact(System.currentTimeMillis()));
    }

    public static int gradientRgb(int rgb1, int rgb2, double timePeriod, double initPhase, double currentTime) {
        double delta = (Math.sin((currentTime % (timePeriod * 2) / timePeriod + initPhase) * Math.PI) + 1) / 2;
        int r = (int) MathHelper.lerp(delta, rgb1 >>> 16 & 0xff, rgb2 >>> 16 & 0xff);
        int g = (int) MathHelper.lerp(delta, rgb1 >>> 8 & 0xff, rgb2 >>> 8 & 0xff);
        int b = (int) MathHelper.lerp(delta, rgb1 & 0xff, rgb2 & 0xff);
        return (r << 16) | (g << 8) | b;
    }

    public static MutableText verbatimRgb(Text text, int rgb1, int rgb2) {
        return verbatimRgb(text, rgb1, rgb2, 1000, 0, 100.0);
    }

    public static MutableText verbatimRgb(Text text, int rgb1, int rgb2, double timePeriod, double initPhase, double spacePeriod) {
        final double[] initPhase1 = {initPhase};
        MutableText text1 = Text.empty();
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        text.asOrderedText().accept((index, style, codePoint) -> {
            MutableText ch = Text
              .literal(String.valueOf((char) codePoint))
              .setStyle(style.withColor(gradientRgb(rgb1, rgb2, timePeriod, initPhase1[0])));
            text1.append(ch);
            initPhase1[0] += textRenderer.getWidth(ch) / spacePeriod;
            return true;
        });
        return text1;
    }

    @Contract(mutates = "param1")
    public static void append(@NotNull List<Text> lines, @NotNull List<Pair<StatusEffectInstance, Float>> statusEffects) {
        for (var pair : statusEffects) {
            StatusEffectInstance instance = pair.getLeft();
            StatusEffect effect = instance.getEffectType();
            Formatting categoryColor = effect.getCategory().getFormatting();
            MutableText line = Text.literal("").formatted(categoryColor);
            int amplifier = instance.getAmplifier();
            int rgb1 = effect.getColor();
            int rgb2 = categoryColor.getColorValue();
            MutableText name = ((MutableText) effect.getName());
            if (gradientColor2) {
                name = verbatimRgb(name, rgb1, rgb2);
            } else {
                int rgb;
                if (gradientColor) {
                    rgb = gradientRgb(rgb1, rgb2, 2000, 0);
                } else {
                    rgb = rgb1;
                }
                name.setStyle(name.getStyle().withColor(rgb));
            }
            line.append(name);
            if (!NO_AMPLIFIER.contains(effect) || amplifier != 0) {
                line = Text.translatable("potion.withAmplifier", line, Text.translatable("potion.potency." + amplifier));
            }
            int duration = instance.getDuration();
            if (!effect.isInstant() || duration != 1) {
                line = Text.translatable("potion.withDuration", line, instance.isInfinite() ? "∞" : translate(duration));
            }
            if (pair.getRight() != 1) {
                line.append(Text.literal(" " + DecimalFormat.getNumberInstance().format(pair.getRight() * 100) + "%"));
            }
            lines.add(line.formatted(Formatting.GRAY));
        }
    }

    public static float[] rgb2hsb(int rgb) {
        return Color.RGBtoHSB(rgb >>> 16 & 0xff, rgb >>> 8 & 0xff, rgb & 0xff, null);
    }

    public static int getWidth(List<Text> lines, TextRenderer textRenderer) {
        int best = 0;
        for (Text text : lines) {
            int width = textRenderer.getWidth(text);
            if (best < width) {
                best = width;
            }
        }
        return BREADTH + best;
    }

    private final List<Pair<StatusEffectInstance, Float>> chancedEffects;
    private final List<Text> lines;

    public StatusEffectsTooltipComponent(@NotNull List<Pair<StatusEffectInstance, Float>> chancedEffects) {
        this.chancedEffects = chancedEffects;
        append(lines = new ArrayList<>(chancedEffects.size()), chancedEffects);
    }

    @Override
    public int getHeight() {
        return getChancedEffects().size() * BREADTH + 2;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {return getWidth(getLines(), textRenderer);}

    @Override
    public void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        int fontHeight = textRenderer.fontHeight;
        int i = 0;
        int x1 = x + BREADTH;
        for (var text : getLines()) {
            int y1 = y + i * BREADTH + (BREADTH - fontHeight) / 2;
            if (outline) {
                textRenderer.drawWithOutline(text.asOrderedText(), x1, y1, 0xffffffff, 0x3f000000 | getChancedEffects().get(i).getLeft().getEffectType().getCategory().getFormatting().getColorValue(), matrix, vertexConsumers, LightmapTextureManager.MAX_LIGHT_COORDINATE);
            } else {
                textRenderer.draw(text, x1, y1, 0xffffffff, false, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, LightmapTextureManager.MAX_LIGHT_COORDINATE);
            }
            i++;
        }
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        //RenderSystem.setShaderColor(1, 1, 1, 1); TODO 删掉这句会有问题吗？
        int i = 0;
        for (var pair : getChancedEffects()) {
            var effect = pair.getLeft();
            StatusEffect type = effect.getEffectType();
            var sprite = MinecraftClient.getInstance().getStatusEffectSpriteManager().getSprite(type);
            RenderSystem.setShaderTexture(0, sprite.getAtlasId());
            context.drawSprite(x, y + i * BREADTH, 0, BREADTH, BREADTH, sprite);
            //int alpha = (int) ((1 - (Math.sin(System.currentTimeMillis() % 2000 / 1000.0 * Math.PI) + 1) / 2) * 0x7f);
            //context.fill(x + fontSize, y + i * fontSize, x+fontSize + textRenderer.getWidth(getLines().get(i)), y + (i + 1) * fontSize - 1, (alpha<<24) | type.getCategory().getFormatting().getColorValue());
            i++;
        }
    }

    public List<Pair<StatusEffectInstance, Float>> getChancedEffects() {
        return chancedEffects;
    }

    public List<Text> getLines() {
        return lines;
    }

    public static class NextLineImpl implements TooltipComponent {

        public final List<Pair<StatusEffectInstance, Float>> chancedEffects;
        public final List<Text> lines;

        public NextLineImpl(@NotNull List<Pair<StatusEffectInstance, Float>> chancedEffects) {
            this.chancedEffects = chancedEffects;
            lines = new ArrayList<>(chancedEffects.size());
            for (var pair : chancedEffects) {
                StatusEffectInstance instance = pair.getLeft();
                StatusEffect effect = instance.getEffectType();
                Formatting categoryColor = effect.getCategory().getFormatting();
                MutableText line = Text.literal("").formatted(categoryColor);
                int amplifier = instance.getAmplifier();
                int rgb1 = effect.getColor();
                MutableText name = ((MutableText) effect.getName());
                name.setStyle(name.getStyle().withColor(rgb1));
                line.append(name);
                if (!NO_AMPLIFIER.contains(effect) || amplifier != 0) {
                    line = Text.translatable("potion.withAmplifier", line, Text.translatable("potion.potency." + amplifier));
                }
                int duration = instance.getDuration();
                if (!effect.isInstant() || duration != 1) {
                    line = Text.translatable("potion.withDuration", line, instance.isInfinite() ? "∞" : translate(duration));
                }
                if (pair.getRight() != 1) {
                    line.append(Text.literal(" " + DecimalFormat.getNumberInstance().format(pair.getRight() * 100) + "%"));
                }
                lines.add(line.formatted(Formatting.GRAY));
            }
        }

        @Override
        public int getHeight() {
            return chancedEffects.size() * MinecraftClient.getInstance().textRenderer.fontHeight * 2 + 2;
        }

        @Override
        public int getWidth(TextRenderer textRenderer) {
            int best = 0;
            for (Text text : lines) {
                int width = textRenderer.getWidth(text);
                if (best < width) {
                    best = width;
                }
            }
            return textRenderer.fontHeight + best;
        }

        @Override
        public void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {

        }

        @Override
        public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {

        }

    }

}