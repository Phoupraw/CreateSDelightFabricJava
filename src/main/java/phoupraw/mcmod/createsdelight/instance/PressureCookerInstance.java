package phoupraw.mcmod.createsdelight.instance;

import com.jozufozu.flywheel.api.MaterialManager;
import phoupraw.mcmod.createsdelight.block.entity.PressureCookerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyPartialModels;
public class PressureCookerInstance extends PillarInstance<PressureCookerBlockEntity> {

    public PressureCookerInstance(MaterialManager dispatcher, PressureCookerBlockEntity tile) {
        super(dispatcher, tile, MyPartialModels.PRESSURE_COOKER_LID);
        setRotationOfX(vertical, blockState);
    }

}
