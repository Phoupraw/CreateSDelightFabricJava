package phoupraw.mcmod.createsdelight.rei;

import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Arrow;
import me.shedaniel.rei.api.client.gui.widgets.Tooltip;
import me.shedaniel.rei.api.client.gui.widgets.TooltipContext;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Element;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

import java.util.List;
@Environment(EnvType.CLIENT)
public class LootTablePrinterWidget extends Arrow {
    public final Arrow delegate;
    public final LootTable lootTable;

    public LootTablePrinterWidget(Point point, LootTable lootTable) {
        this.delegate = Widgets.createArrow(point);
        this.lootTable = lootTable;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        delegate.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public List<? extends Element> children() {
        return delegate.children();
    }

    @Override
    public @Nullable Tooltip getTooltip(TooltipContext context) {
        return Tooltip.create(Text.literal("左键单击以打印战利品表，右键单击以复制战利品表"));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) return true;
        String lootTableJson = LootManager.toJson(lootTable).toString();
        if (button == 0) {
            if (minecraft.player != null) {
                minecraft.player.sendMessage(Text.literal(lootTableJson));
                return true;
            }
        } else if (button == 1) {
            minecraft.keyboard.setClipboard(lootTableJson);
        }
        return false;
    }

    @Override
    public double getAnimationDuration() {
        return delegate.getAnimationDuration();
    }

    @Override
    public void setAnimationDuration(double animationDurationMS) {
        delegate.setAnimationDuration(animationDurationMS);
    }

    @Override
    public Rectangle getBounds() {
        return delegate.getBounds();
    }

}
