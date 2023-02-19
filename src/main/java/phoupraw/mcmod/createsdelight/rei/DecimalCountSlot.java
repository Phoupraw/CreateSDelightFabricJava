package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.impl.client.gui.widget.EntryWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Contract;
public class DecimalCountSlot extends EntryWidget {
    @Contract(pure = true)
    public static boolean approx(double a, double b) {
        return Math.abs(a - b) < 0.001;
    }

    @Contract(pure = true)
    public static String shortDecimal(double decimal) {
        if (approx(decimal, (long) decimal)) return "%.0f".formatted(decimal);
        if (approx(decimal * 10, ((long) (decimal * 10)))) return "%.1f".formatted(decimal);
//        System.out.println(decimal + " " + (long) (decimal * 10) / 10);
        return "%.2f".formatted(decimal);
    }

    private double count = 1;

    public DecimalCountSlot(Point point) {
        super(point);
    }

    public DecimalCountSlot(Rectangle bounds) {
        super(bounds);
    }

    @Contract("_->this")
    public DecimalCountSlot withCount(double chance) {
        this.count = chance;
        return this;
    }

    public double getCount() {
        return count;
    }

    @Override
    protected void drawExtra(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.drawExtra(matrices, mouseX, mouseY, delta);
        if(count==1) return;
        matrices.push();
        String decimal = shortDecimal(count);
        Text text = Text.literal(decimal);
        int width = font.getWidth(text);
        if (decimal.length()<=3) {
            matrices.translate(getBounds().getX() + 19 - width, getBounds().getY() + 11, 200);
        }else{
            float scale = 0.8f;
            matrices.translate(getBounds().getX() + 19 - width * scale, getBounds().getY() + 12, 200);
            matrices.scale(scale, scale, 1);
        }
        drawTextWithShadow(matrices, font, text, 0, 0, 0xffffffff);
        matrices.pop();
    }
}
