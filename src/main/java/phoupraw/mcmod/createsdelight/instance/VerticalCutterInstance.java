package phoupraw.mcmod.createsdelight.instance;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.oriented.OrientedData;
import com.simibubi.create.content.contraptions.relays.encased.ShaftInstance;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import phoupraw.mcmod.createsdelight.block.VerticalCutterBlock;
import phoupraw.mcmod.createsdelight.block.entity.VerticalCutterBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyPartialModels;
public class VerticalCutterInstance extends ShaftInstance implements DynamicInstance {
    public static final double ALTITUDE = 17 / 16.0;
    public final OrientedData knife;

    public VerticalCutterInstance(MaterialManager dispatcher, VerticalCutterBlockEntity tile) {
        super(dispatcher, tile);
        knife = dispatcher.defaultSolid()
          .material(Materials.ORIENTED)
          .getModel(MyPartialModels.VERTICAL_CUTTER_KNIFE, blockState)
          .createInstance();
        knife.setRotation(Vec3f.POSITIVE_Y.getDegreesQuaternion(AngleHelper.horizontalAngle(blockState.get(VerticalCutterBlock.X) ? Direction.EAST : Direction.SOUTH)));
        transformModels();
    }

    @Override
    public void beginFrame() {
        transformModels();
    }

    private void transformModels() {
        knife.setPosition(getInstancePosition()).nudge(0, (float) getBlockEntity().getKnifeOffset(AnimationTickHolder.getPartialTicks()), 0);
    }

    public VerticalCutterBlockEntity getBlockEntity() {
        return (VerticalCutterBlockEntity) blockEntity;
    }@Override
    public void updateLight() {
        super.updateLight();
        relight(pos, knife);
    }

    @Override
    public void remove() {
        super.remove();
        knife.delete();
    }
}
