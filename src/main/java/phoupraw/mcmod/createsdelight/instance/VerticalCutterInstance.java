package phoupraw.mcmod.createsdelight.instance;

import com.jozufozu.flywheel.api.MaterialManager;
import phoupraw.mcmod.createsdelight.block.entity.VerticalCutterBlockEntity;
import phoupraw.mcmod.createsdelight.registry.CDPartialModels;
public class VerticalCutterInstance extends PillarInstance<VerticalCutterBlockEntity> {
    public static final double ALTITUDE = 17 / 16.0;

    public VerticalCutterInstance(MaterialManager dispatcher, VerticalCutterBlockEntity tile) {
        super(dispatcher, tile, CDPartialModels.VERTICAL_CUTTER_KNIFE);
        setRotationOfX(vertical, blockState);
    }
}
