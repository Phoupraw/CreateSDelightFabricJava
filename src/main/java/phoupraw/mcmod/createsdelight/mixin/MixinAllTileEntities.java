package phoupraw.mcmod.createsdelight.mixin;

import com.simibubi.create.AllTileEntities;
import com.simibubi.create.content.contraptions.components.crusher.CrushingWheelControllerTileEntity;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import phoupraw.mcmod.createsdelight.block.entity.renderer.CrushingWheelControllerRenderer;
@Mixin(AllTileEntities.class)
public class MixinAllTileEntities {
    @ModifyArg(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=millstone")), at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/data/CreateTileEntityBuilder;instance(Lcom/tterrag/registrate/util/nullness/NonNullSupplier;Z)Lcom/simibubi/create/foundation/data/CreateTileEntityBuilder;", ordinal = 0, remap = false))
    private static boolean millstoneRenderNormally(boolean renderNormally) {
        return true;
    }

    @Redirect(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=crushing_wheel_controller")), at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/builders/BlockEntityBuilder;register()Lcom/tterrag/registrate/util/entry/BlockEntityEntry;", ordinal = 0, remap = false))
    private static BlockEntityEntry<CrushingWheelControllerTileEntity> crushingControllerRenderer(BlockEntityBuilder<CrushingWheelControllerTileEntity, CreateRegistrate> instance) {
        return instance.renderer(() -> CrushingWheelControllerRenderer::new).register();
    }
}
