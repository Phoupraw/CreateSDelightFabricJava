package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.compat.rei.category.CrushingCategory;
import com.simibubi.create.compat.rei.display.CreateDisplay;
import com.simibubi.create.content.contraptions.components.crusher.AbstractCrushingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import phoupraw.mcmod.createsdelight.inject.InjectCrushingCategory;

import java.util.List;
@Mixin(CrushingCategory.class)
@Environment(EnvType.CLIENT)
public class MixinCrushingCategory {
    @Inject(method = "setupDisplay(Lcom/simibubi/create/compat/rei/display/CreateDisplay;Lme/shedaniel/math/Rectangle;)Ljava/util/List;", at = @At(value = "NEW", target = "com/simibubi/create/compat/rei/category/animations/AnimatedCrushingWheels", remap = false), locals = LocalCapture.CAPTURE_FAILHARD)
    private void drawFluidSlot(CreateDisplay<AbstractCrushingRecipe> display, Rectangle bounds, CallbackInfoReturnable<List<Widget>> cir, Point origin, List<Widget> widgets, List<ProcessingOutput> results) {
        InjectCrushingCategory.drawFluidSlot((CrushingCategory) (Object) this, display, bounds, origin, widgets);
    }
}
