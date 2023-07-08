package phoupraw.mcmod.common.api;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import phoupraw.mcmod.createsdelight.CreateSDelight;

import java.text.DecimalFormat;
/**
 一些方便本地化的静态方法。
 @since 1.0.0 */
public final class Internationals {
    /**
     <table border=1>
     <caption>翻译对照表</caption>
     <tr><th>中文</th><td>%s秒</td></tr>
     <tr><th>英文</th><td>%s sec</td></tr>
     </table>
     @see #translateToSeconds
     @since 1.0.0
     */
    public static final String SECONDS = "text." + CreateSDelight.MOD_ID + ".seconds";
    /**
     <table border=1>
     <caption>翻译对照表</caption>
     <tr><th>中文</th><td>%s分%s秒%s刻</td></tr>
     <tr><th>英文</th><td>%smin %ssec %s ticks</td></tr>
     </table>
     @see #translateToMST
     @since 1.0.0
     */
    public static final String MST = "text." + CreateSDelight.MOD_ID + ".mst";

    public static @NotNull String keyOfItemGroup(@NotNull Identifier id) {
        return "itemGroup." + id.getNamespace() + "." + id.getPath();
    }

    public static @NotNull String keyOfCategory(@NotNull Identifier recipeTypeId) {
        return "category." + recipeTypeId.getNamespace() + "." + recipeTypeId.getPath();
    }

    /**
     为{@link EntityAttribute}生成本地化键，可以当做{@link EntityAttribute#getTranslationKey()}的返回值。
     @param attribute 属性的ID
     @return 属性的本地化键
     @since 1.0.0
     */
    @Contract(pure = true)
    public static @NotNull String keyOfAttribute(@NotNull Identifier attribute) {
        return "attribute." + attribute;
    }

    /**
     @param source 带有ASCII字符的Unicode字符串
     @return 把Unicode字符转为 {@code \\uXXXX}格式的字符串
     @since 1.0.0
     */
    @Contract(pure = true)
    public static @NotNull String chineseToUnicode(@NotNull String source) {
        StringBuilder builder = new StringBuilder(source.length());
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (c == '\n') builder.append("\\n");
            else if (c < 0x80) builder.append(c);
            else builder.append("\\u%04X".formatted((int) c));
        }
        return builder.toString();
    }

    /**
     @param modId 模组ID
     @return 用于模组菜单里显示的描述的本地化键
     @since 1.0.0
     */
    @Contract(pure = true)
    public static @NotNull String keyOfModDescription(@NotNull String modId) {
        return "modmenu.descriptionTranslation." + modId;
    }

    /**
     @param modId 模组ID
     @return 用于模组菜单里显示的名称的本地化键
     @since 1.0.0
     */
    @Contract(pure = true)
    public static @NotNull String keyOfModName(@NotNull String modId) {
        return "modmenu.nameTranslation." + modId;
    }

    /**
     把以游戏刻为单位的时长翻译为以秒为单位的本地化文本，保留两位小数，例如：135 -> 6.75秒
     @param ticks 时长，单位：游戏刻
     @return 本地化的文本
     @see #SECONDS
     @since 1.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MutableText translateToSeconds(@Range(from = Long.MIN_VALUE, to = Long.MAX_VALUE) long ticks) {
        return Text.translatable(SECONDS, new DecimalFormat("#.##").format(ticks / 20.0));
    }

    /**
     把以游戏刻为单位的时长翻译为以秒(s)为单位的带单位的文本，最多保留两位小数，例如：135 -> 6.75s
     @param ticks 时长，单位：游戏刻
     @return 带单位的文本
     @since 1.0.0
     */
    public static @NotNull String symbolToSeconds(@Range(from = Long.MIN_VALUE, to = Long.MAX_VALUE) long ticks) {
        return new DecimalFormat("#.##").format(ticks / 20.0) + "s";
    }

    /**
     把以游戏刻为单位的时长翻译为以分秒刻为单位的本地化文本，例如：135 -> 0分6秒15刻
     @param ticks 时长，单位：游戏刻
     @return 本地化的文本
     @see #MST
     @since 1.0.0
     */
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull MutableText translateToMST(@Range(from = 0, to = Long.MAX_VALUE) long ticks) {
        return Text.translatable(MST, ticks / 20 / 60, ticks / 20 % 60, ticks % 20);
    }

    /**
     把以游戏刻为单位的时长翻译为以分(m)秒(s)刻(t)为单位的带单位的文本，例如：(135, false) -> 0m6s15t；(135, true) -> 6s15t；0 -> 0
     @param ticks 时长，单位：游戏刻
     @param trim 是否不显示零值
     @return 带单位的文本
     @since 1.0.0
     */
    public static @NotNull String symbolToMST(@Range(from = Long.MIN_VALUE, to = Long.MAX_VALUE) long ticks, boolean trim) {
        if (ticks == 0) return "0";
        long m = Math.abs(ticks / 20 / 60), s = Math.abs(ticks / 20 % 60), t = Math.abs(ticks % 20);
        var b = new StringBuilder();
        if (ticks < 0) b.append('-');
        if (m != 0 || !trim) b.append(m);
        if (s != 0 || !trim) b.append(s);
        if (t != 0 || !trim) b.append(t);
        return b.toString();
    }

    private Internationals() {}
}
