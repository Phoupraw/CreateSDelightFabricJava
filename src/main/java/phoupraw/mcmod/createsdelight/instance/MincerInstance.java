package phoupraw.mcmod.createsdelight.instance;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.oriented.OrientedData;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.base.flwdata.RotatingData;
import com.simibubi.create.content.contraptions.components.mixer.MixerInstance;
import com.simibubi.create.content.contraptions.relays.encased.EncasedCogInstance;
import com.simibubi.create.foundation.render.AllMaterialSpecs;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.util.math.Direction;
import phoupraw.mcmod.createsdelight.block.entity.MincerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyPartialModels;
/**
 * 从{@link MixerInstance}和{@link PillarInstance}抄的。
 */
public class MincerInstance extends EncasedCogInstance implements DynamicInstance {
    public final RotatingData propeller;
    public final OrientedData vertical;

    public MincerInstance(MaterialManager dispatcher, MincerBlockEntity tile) {
        super(dispatcher, tile, false);
        propeller = dispatcher.defaultCutout()
          .material(AllMaterialSpecs.ROTATING)
          .getModel(MyPartialModels.MINCER_PROPELLER, blockState, Direction.NORTH)
          .createInstance();
        this.vertical = dispatcher.defaultCutout()
          .material(Materials.ORIENTED)
          .getModel(MyPartialModels.MINCER_LID, blockState)
          .createInstance();
        transform();
    }

    public void transform() {
        MincerBlockEntity blockEntity = getBlockEntity();
        float partialTicks = AnimationTickHolder.getPartialTicks();
        float offset = (float) blockEntity.getOffset(partialTicks);
        propeller
          .setPosition(getInstancePosition())
          .nudge(0, offset, 0);
        vertical
          .setPosition(getInstancePosition())
          .nudge(0, offset, 0);

    }

    public MincerBlockEntity getBlockEntity() {
        return (MincerBlockEntity) blockEntity;
    }

    @Override
    public void beginFrame() {
        transform();
    }

    @Override
    public void updateLight() {
        super.updateLight();
        relight(pos, vertical, propeller);
    }

    @Override
    public void remove() {
        super.remove();
        vertical.delete();
        propeller.delete();
    }

    @Override
    public void init() {
        super.init();
        update();
    }

    @Override
    protected Instancer<RotatingData> getCogModel() {
        return materialManager.defaultSolid()
          .material(AllMaterialSpecs.ROTATING)
          .getModel(AllBlockPartials.SHAFTLESS_COGWHEEL, blockState);
    }

    @Override
    public void update() {
        super.update();
        float speed = Math.abs(getBlockEntity().getSpeed());
        updateRotation(propeller, getBlockEntity().getExtension() >= 0.5 ? speed * 3 : speed / 2);
    }
}
