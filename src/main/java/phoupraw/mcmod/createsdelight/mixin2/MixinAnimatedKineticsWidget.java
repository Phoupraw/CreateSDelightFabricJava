package phoupraw.mcmod.createsdelight.mixin2;

import com.simibubi.create.compat.rei.widgets.AnimatedKineticsWidget;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
/**
 * @see MixinWidgetUtil_1
 */
@Mixin(AnimatedKineticsWidget.class)
public abstract class MixinAnimatedKineticsWidget implements ParentElement, Drawable {
//    @Shadow(remap = false)
//    public abstract void method_25394(MatrixStack matrices, int mouseX, int mouseY, float delta);
//
//    @Override
//    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
//        method_25394(matrices, mouseX, mouseY, delta);
//    }
//
//    @Shadow(remap = false)
//    public abstract List<? extends Element> method_25396();
//
//    @Override
//    public List<? extends Element> children() {
//        return method_25396();
//    }
}
