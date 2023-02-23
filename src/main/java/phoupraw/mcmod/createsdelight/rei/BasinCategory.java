package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Contract;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
public abstract class BasinCategory<T extends BasinDisplay> implements DisplayCategory<T> {
    /**
     * 背景边框的宽度
     */
    public static final int BACKGROUND_BORDER_BREADTH = 4;
    /**
     * 普通格子的边长
     */
    public static final int SLOT_LENGTH = 18;
    /**
     * 大格子的边长
     */
    public static final int BIG_SLOT_LENGTH = 24;
    /**
     * 箭头的宽度
     */
    public static final int ARROW_WIDTH = 24;

    /**
     * 计算总高度。考虑了背景框边界。
     *
     * @param slots    普通格子的行数
     * @param bigSlots 大格子的行数
     * @return 总高度
     */
    @Contract(pure = true)
    public static int calcHeight(int slots, int bigSlots) {
        return BACKGROUND_BORDER_BREADTH + SLOT_LENGTH * slots + BIG_SLOT_LENGTH * bigSlots + BACKGROUND_BORDER_BREADTH;
    }

    /**
     * 计算总宽度。考虑了背景框边界。
     *
     * @param slots    普通格子的列数
     * @param bigSlots 大格子的列数
     * @param arrows   箭头的列数
     * @return 总宽度
     */
    @Contract(pure = true)
    public static int calcWidth(int slots, int bigSlots, int arrows) {
        return BACKGROUND_BORDER_BREADTH + SLOT_LENGTH * slots + BIG_SLOT_LENGTH * bigSlots + ARROW_WIDTH * arrows + BACKGROUND_BORDER_BREADTH;
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category." + getCategoryIdentifier().getNamespace() + "." + getCategoryIdentifier().getPath());
    }

    @Override
    public int getDisplayHeight() {
        return calcHeight(3, 0);
    }

    /**
     * 根据配方的原料和产出数量动态计算格子列数。
     */
    @Override
    public int getDisplayWidth(T display) {
        return calcWidth((display.getInputEntries().size() + 2) / 3 + (display.getOutputEntries().size() + 2) / 3, 0, 1);
    }

    @Override
    public List<Widget> setupDisplay(T display, Rectangle bounds) {
        List<Widget> widgets = new ArrayList<>();
        widgets.add(Widgets.createRecipeBase(bounds));
        int x0 = bounds.getX() + BACKGROUND_BORDER_BREADTH, y0 = bounds.getY() + BACKGROUND_BORDER_BREADTH;
        List<EntryIngredient> inputs = display.getInputEntries();
        for (int i = 0; i < inputs.size(); i++) {
            widgets.add(Widgets.createSlot(new Point(x0 + 1 + SLOT_LENGTH * (i / 3), y0 + 1 + SLOT_LENGTH * ((i + 1) % 3))).entries(inputs.get(i)).markInput());
        }
        x0 += SLOT_LENGTH * ((inputs.size() + 2) / 3);
        int duration = display.getDuration();
        widgets.add(Widgets.createArrow(new Point(x0, y0 + SLOT_LENGTH + 1)).animationDurationTicks(duration));
        widgets.add(Widgets.createLabel(new Point(x0, y0 + SLOT_LENGTH - 5), Text.translatable("category.rei.campfire.time", new DecimalFormat("###.##").format(duration / 20.0))).leftAligned());
        widgets.add(Widgets.createBurningFire(new Point(x0 + 5, y0 + SLOT_LENGTH * 2 - 1)).animationDurationTicks(100));
        x0 += ARROW_WIDTH;
        List<EntryIngredient> outputs = display.getOutputEntries();
        for (int i = 0; i < outputs.size(); i++) {
            widgets.add(Widgets.createSlot(new Point(x0 + 1 + SLOT_LENGTH * (i / 3), y0 + 1 + SLOT_LENGTH * ((i + 1) % 3))).entries(outputs.get(i)).markOutput());
        }
        return widgets;
    }
}
