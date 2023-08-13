package phoupraw.mcmod.createsdelight.block.entity.renderer;

import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import phoupraw.mcmod.createsdelight.block.entity.VerticalCutterBlockEntity;
@Environment(EnvType.CLIENT)
public class VerticalCutterRenderer extends SmartBlockEntityRenderer<VerticalCutterBlockEntity> {

    public VerticalCutterRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }
}
