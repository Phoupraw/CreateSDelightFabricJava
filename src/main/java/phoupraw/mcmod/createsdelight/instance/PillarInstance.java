package phoupraw.mcmod.createsdelight.instance;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.PartialModel;
import com.jozufozu.flywheel.core.materials.oriented.OrientedData;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.relays.encased.ShaftInstance;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import phoupraw.mcmod.createsdelight.block.entity.InstanceOffset;
public abstract class PillarInstance<T extends KineticTileEntity & InstanceOffset> extends ShaftInstance implements DynamicInstance {
    public static void setRotationOfX(OrientedData part, BlockState blockState) {
        part.setRotation(Vec3f.POSITIVE_Y.getDegreesQuaternion(AngleHelper.horizontalAngle(blockState.get(Properties.HORIZONTAL_AXIS) == Direction.Axis.X ? Direction.EAST : Direction.SOUTH)));
    }

    public final OrientedData vertical;

    public PillarInstance(MaterialManager dispatcher, T tile, PartialModel vertical) {
        super(dispatcher, tile);
        this.vertical = dispatcher.defaultSolid()
          .material(Materials.ORIENTED)
          .getModel(vertical, blockState)
          .createInstance();
        transformVertical();
    }

    @Override
    public void beginFrame() {
        transformVertical();
    }

    public void transformVertical() {
        vertical.setPosition(getInstancePosition()).nudge(0, (float) getBlockEntity().getOffset(AnimationTickHolder.getPartialTicks()), 0);
    }

    @SuppressWarnings("unchecked")
    public T getBlockEntity() {
        return (T) blockEntity;
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
}
