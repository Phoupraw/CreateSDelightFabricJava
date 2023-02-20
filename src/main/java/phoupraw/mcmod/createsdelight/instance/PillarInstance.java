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
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import phoupraw.mcmod.createsdelight.block.VerticalCutterBlock;
import phoupraw.mcmod.createsdelight.block.entity.InstanceOffset;
public abstract class PillarInstance<T extends KineticTileEntity & InstanceOffset> extends ShaftInstance implements DynamicInstance {
    public static void setRotationOfX(OrientedData part, BlockState blockState) {
        part.setRotation(Vec3f.POSITIVE_Y.getDegreesQuaternion(AngleHelper.horizontalAngle(blockState.get(VerticalCutterBlock.X) ? Direction.EAST : Direction.SOUTH)));
    }

    public final OrientedData part;

    public PillarInstance(MaterialManager dispatcher, T tile, PartialModel partialModel) {
        super(dispatcher, tile);
        part = dispatcher.defaultSolid()
          .material(Materials.ORIENTED)
          .getModel(partialModel, blockState)
          .createInstance();
        setRotationOfX(part, blockState);
        transformModels();
    }

    @Override
    public void beginFrame() {
        transformModels();
    }

    protected void transformModels() {
        part.setPosition(getInstancePosition()).nudge(0, (float) getBlockEntity().getOffset(AnimationTickHolder.getPartialTicks()), 0);
    }

    @SuppressWarnings("unchecked")
    public T getBlockEntity() {
        return (T) blockEntity;
    }

    @Override
    public void updateLight() {
        super.updateLight();
        relight(pos, part);
    }

    @Override
    public void remove() {
        super.remove();
        part.delete();
    }
}
