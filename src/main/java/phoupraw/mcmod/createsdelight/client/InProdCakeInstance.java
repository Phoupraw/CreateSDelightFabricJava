package phoupraw.mcmod.createsdelight.client;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.model.ModelData;
import com.jozufozu.flywheel.core.model.BakedModelBuilder;
import com.jozufozu.flywheel.core.model.BlockModel;
import com.jozufozu.flywheel.util.AnimationTickHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;
import phoupraw.mcmod.createsdelight.block.entity.InProdCakeBlockEntity;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;

public class InProdCakeInstance extends BlockEntityInstance<InProdCakeBlockEntity> implements DynamicInstance {
    public ModelData self;
    public InProdCakeInstance(MaterialManager materialManager, InProdCakeBlockEntity blockEntity) {
        super(materialManager, blockEntity);
    }
    @Override
    public void beginFrame() {
        VoxelCake voxelCake = blockEntity.getVoxelCake();
        if (voxelCake == null) return;
        if (self == null) {
            self = materialManager.defaultSolid()
              .material(Materials.TRANSFORMED)
              .model(this, () -> BlockModel.of(new BakedModelBuilder(InProdCakeModel.CACHE.getUnchecked(voxelCake)).withRenderWorld(world).withReferenceState(blockState), voxelCake.toString()))
              .createInstance();
            update();
        }//FIXME 在方块实体的蛋糕更改时这里可能不会跟着改
        self.translate(getInstancePosition());
        BlockPos relative = blockEntity.relative;
        if (relative == null) return;
        Direction direction = blockEntity.direction;
        if (direction == null && false) {
            int edgeLen = blockEntity.edgeLen;
            Vector3f center = Vec3d.of(relative).toVector3f().mul(-1f / edgeLen);
            self.translate(center).scale(1 + (edgeLen - 1) * (blockEntity.getProgress() - 1f / InProdCakeBlockEntity.SHRINKING_TICKS * AnimationTickHolder.getPartialTicks()));
        } else {
            float offset = blockEntity.getProgress();
        }
    }
    @Override
    protected void remove() {
        if (self != null) {
            self.delete();
        }
    }
}
