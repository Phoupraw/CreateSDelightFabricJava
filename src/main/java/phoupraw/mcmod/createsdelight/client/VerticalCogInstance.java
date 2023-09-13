package phoupraw.mcmod.createsdelight.client;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.flwdata.RotatingData;
import com.simibubi.create.content.kinetics.simpleRelays.encased.EncasedCogInstance;
import com.simibubi.create.foundation.render.AllMaterialSpecs;

public class VerticalCogInstance extends EncasedCogInstance {
    public VerticalCogInstance(MaterialManager materialManager, KineticBlockEntity blockEntity) {
        super(materialManager, blockEntity, false);
    }
    @Override
    protected Instancer<RotatingData> getCogModel() {
        return materialManager.defaultSolid()
          .material(AllMaterialSpecs.ROTATING)
          .getModel(AllPartialModels.SHAFTLESS_COGWHEEL, blockEntity.getCachedState());
    }
}
