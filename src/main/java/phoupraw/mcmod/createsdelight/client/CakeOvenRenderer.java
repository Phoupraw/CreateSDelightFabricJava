package phoupraw.mcmod.createsdelight.client;

import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import phoupraw.mcmod.createsdelight.block.entity.CakeOvenBlockEntity;

public class CakeOvenRenderer extends KineticBlockEntityRenderer<CakeOvenBlockEntity> {
    public CakeOvenRenderer(BlockEntityRendererFactory.Context context) {
        super(context);
    }
}
