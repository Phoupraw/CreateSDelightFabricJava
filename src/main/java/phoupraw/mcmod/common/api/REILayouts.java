package phoupraw.mcmod.common.api;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.display.Display;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
/**
 @since 1.0.0 */
public final class REILayouts {

    /**
     @since 1.0.0
     背景框的边框的宽度
     */
    public static final int BACKGROUND_BORDER_BREADTH = 4;
    /**
     @since 1.0.0
     普通格子的边长
     */
    public static final int SLOT_LENGTH = 18;
    /**
     @since 1.0.0
     大格子的边长
     */
    public static final int BIG_SLOT_LENGTH = 26;
    /**
     @since 1.0.0
     箭头的宽度
     */
    public static final int ARROW_WIDTH = 24;

    /**
     @param slots 普通格子的行数
     @param bigSlots 大格子的行数
     @return 总高度，即<code>{@value BACKGROUND_BORDER_BREADTH}+{@value SLOT_LENGTH}*slots+{@value BIG_SLOT_LENGTH}*bigSlots+{@value BACKGROUND_BORDER_BREADTH}</code>
     @since 1.0.0
     计算总高度。考虑了背景框的边框。
     */
    @Contract(pure = true)
    public static int calcHeight(int slots, int bigSlots) {
        return BACKGROUND_BORDER_BREADTH + SLOT_LENGTH * slots + BIG_SLOT_LENGTH * bigSlots + BACKGROUND_BORDER_BREADTH;
    }

    /**
     @param slots 普通格子的列数
     @param bigSlots 大格子的列数
     @param arrows 箭头的列数
     @return 总宽度，即<code>{@value BACKGROUND_BORDER_BREADTH} + {@value SLOT_LENGTH}*slots + {@value BIG_SLOT_LENGTH}*bigSlots + {@value ARROW_WIDTH}*arrows + {@value BACKGROUND_BORDER_BREADTH}</code>
     @since 1.0.0
     计算总宽度。考虑了背景框的边框。
     */
    @Contract(pure = true)
    public static int calcWidth(int slots, int bigSlots, int arrows) {
        return BACKGROUND_BORDER_BREADTH + SLOT_LENGTH * slots + BIG_SLOT_LENGTH * bigSlots + ARROW_WIDTH * arrows + BACKGROUND_BORDER_BREADTH;
    }

    /**
     @param backgroundX 背景框的x坐标
     @param backgroundY 背景框的y坐标
     @return slot的坐标，即<code>new Point(<i>backgroundX</i>+{@value BACKGROUND_BORDER_BREADTH}+1, <i>backgroundY</i>+{@value BACKGROUND_BORDER_BREADTH}+1)</code>
     @see #slotAlignSlot(Point, int, int)
     @since 1.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull Point slotAlignBackground(int backgroundX, int backgroundY) {
        return new Point(backgroundX + BACKGROUND_BORDER_BREADTH + 1, backgroundY + BACKGROUND_BORDER_BREADTH + 1);
    }

    /**
     @param background 背景框的坐标
     @return 槽位的坐标
     @see #slotAlignSlot(int, int, int, int)
     @see DisplayCategory#setupDisplay(Display, Rectangle)
     @since 1.0.0
     如下代码可以计算紧贴着背景框的边框位于左上角的槽位的坐标：
     <blockquote><pre>
     public List&lt;Widget&gt; setupDisplay(T display, Rectangle bounds) {
     ...
     var slot0 = REILayouts.slotAlignBackground(bounds);
     ...
     }</pre></blockquote>
     */
    @SuppressWarnings("OverrideOnly")
    @Contract("_ -> new")
    public static @NotNull Point slotAlignBackground(@NotNull Rectangle background) {
        return slotAlignBackground(background.getX(), background.getY());
    }

    /**
     @param slotX 基准槽位的x坐标
     @param slotY 基准槽位的x坐标
     @param column 要计算的槽位所在的列号，如果和基准槽位同一列，则填0
     @param row 要计算的槽位所在的行号，如果和基准槽位同一行，则填0
     @return 要计算的槽位的坐标，即<code>new Point(<i>slotX</i> + <i>column</i> * {@value SLOT_LENGTH}, <i>slotY</i> + <i>row</i> * {@value SLOT_LENGTH})</code>
     @see #slotAlignSlot(Point, int, int)
     @since 1.0.0
     */
    @Contract(value = "_, _, _, _ -> new", pure = true)
    public static @NotNull Point slotAlignSlot(int slotX, int slotY, int column, int row) {
        return new Point(slotX + column * SLOT_LENGTH, slotY + row * SLOT_LENGTH);
    }

    /**
     @param slot 基准槽位的坐标
     @param column 要计算的槽位所在的列号，如果和基准槽位同一列，则填0
     @param row 要计算的槽位所在的行号，如果和基准槽位同一行，则填0
     @return 要计算的槽位的坐标
     @see #slotAlignSlot(int, int, int, int)
     @since 1.0.0
     如下代码可以计算紧贴着slot0位于下方的槽位的坐标：
     <blockquote><pre>
     var slot0 = ...
     var slot1 = REILayouts.slotAlignSlot(slot0, 1, 0);
     </pre></blockquote>
     */
    @Contract("_, _, _ -> new")
    public static @NotNull Point slotAlignSlot(@NotNull Point slot, int column, int row) {
        return slotAlignSlot(slot.getX(), slot.getY(), column, row);
    }

    /**
     @param slotX 基准槽位的x坐标
     @param slotY 基准槽位的y坐标
     @return 箭头的坐标
     @see #arrowAlignSlot(Point)
     @since 1.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull Point arrowAlignSlot(int slotX, int slotY) {
        return new Point(slotX + SLOT_LENGTH - 1, slotY);
    }

    /**
     @param slot 基准槽位的坐标
     @return 箭头的坐标
     @see #arrowAlignSlot(int, int)
     @since 1.0.0
     */
    @Contract("_ -> new")
    public static @NotNull Point arrowAlignSlot(@NotNull Point slot) {
        return arrowAlignSlot(slot.getX(), slot.getY());
    }

    /**
     @param slotX 基准槽位的x坐标
     @param slotY1 左上的基准槽位的y坐标
     @param slotY2 左下的基准槽位1的y坐标
     @return 箭头的坐标
     @see #arrowAlignSlot(int, int)
     @since 1.0.0
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull Point arrowAlignSlot(int slotX, int slotY1, int slotY2) {
        return arrowAlignSlot(slotX, (slotY1 + slotY2) / 2);
    }

    /**
     @param arrowX 箭头的x坐标
     @param arrowY 箭头的y坐标
     @return 槽位的坐标
     @see #slotAlignArrow(Point)
     @since 1.0.0
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull Point slotAlignArrow(int arrowX, int arrowY) {
        return new Point(arrowX + ARROW_WIDTH, arrowY);
    }

    /**
     @param arrow 箭头的坐标
     @return 槽位的坐标
     @see #slotAlignArrow(int, int)
     @since 1.0.0
     */
    @Contract("_ -> new")
    public static @NotNull Point slotAlignArrow(@NotNull Point arrow) {
        return slotAlignArrow(arrow.getX() + 1, arrow.getY());
    }

    /**
     @param arrowX 箭头的x坐标
     @param arrowY 箭头的y坐标
     @return 大槽位的坐标
     @see #bigSlotAlignArrow(Point)
     @since 1.0.0
     与{@link #slotAlignArrow(int, int)}基本相同，但是是为大槽位（{@link Widgets#createResultSlotBackground}）提供的。
     */
    @Contract(value = "_, _ -> new", pure = true)
    public static @NotNull Point bigSlotAlignArrow(int arrowX, int arrowY) {
        return new Point(arrowX + ARROW_WIDTH + 5, arrowY);
    }

    /**
     @param arrow 箭头的坐标
     @return 大槽位的坐标
     @see #bigSlotAlignArrow(int, int)
     @since 1.0.0
     */
    @Contract("_ -> new")
    public static @NotNull Point bigSlotAlignArrow(@NotNull Point arrow) {
        return bigSlotAlignArrow(arrow.getX(), arrow.getY());
    }

    private REILayouts() {}
}
