package phoupraw.mcmod.createsdelight.mixin;

import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
/**
 * 修复<a href="https://github.com/Phoupraw/CreateSDelightFabricJava/issues/1">Can't check the recipe of chocolate pie in jei alone #1</a>、<a href="https://github.com/Fabricators-of-Create/Create/issues/788">[Crash] Open REI recipe screen #788</a>
 */
@Mixin(targets = "com.simibubi.create.compat.rei.category.WidgetUtil$1")
public abstract class MixinWidgetUtil_1 implements ParentElement ,Drawable{
    @Shadow(remap = false)
    public abstract void method_25394(MatrixStack matrices, int mouseX, int mouseY, float delta);

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        method_25394(matrices, mouseX, mouseY, delta);
    }

    @Shadow(remap = false)
    public abstract List<? extends Element> method_25396();

    @Override
    public List<? extends Element> children() {
        return method_25396();
    }
}
