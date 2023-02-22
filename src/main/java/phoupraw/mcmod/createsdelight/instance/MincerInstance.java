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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import phoupraw.mcmod.createsdelight.block.entity.MincerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyPartialModels;
/**
 * 从{@link MixerInstance}和{@link PillarInstance}抄的。
 */
public class MincerInstance extends EncasedCogInstance implements DynamicInstance {
    public final OrientedData whisk;
    public final OrientedData vertical;

    public MincerInstance(MaterialManager dispatcher, MincerBlockEntity tile) {
        super(dispatcher, tile, false);
        whisk = dispatcher.defaultSolid()
          .material(Materials.ORIENTED)
          .getModel(MyPartialModels.MINCER_WHISK, blockState)
          .createInstance();
        this.vertical = dispatcher.defaultSolid()
          .material(Materials.ORIENTED)
          .getModel(MyPartialModels.MINCER_LID, blockState)
          .createInstance();
        transform();
    }

    public void transform() {
        MincerBlockEntity blockEntity = getBlockEntity();
        long time = blockEntity.getWorld().getTime();
        float speed = blockEntity.getSpeed();
        float partialTicks = AnimationTickHolder.getPartialTicks();
        float lerped = MathHelper.lerp(partialTicks, time, time + 1);
        float angle = lerped % 360 * speed;
        float offset = (float) blockEntity.getOffset(partialTicks);
        whisk
          .setPosition(getInstancePosition())
          .nudge(0, offset, 0)
          .setRotation(new Quaternion(Direction.UP.getUnitVector(), angle, true));
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
        relight(pos, vertical);
    }

    @Override
    public void remove() {
        super.remove();
        vertical.delete();
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    protected Instancer<RotatingData> getCogModel() {
        return materialManager.defaultSolid()
          .material(AllMaterialSpecs.ROTATING)
          .getModel(AllBlockPartials.SHAFTLESS_COGWHEEL, blockEntity.getCachedState());
    }
}
