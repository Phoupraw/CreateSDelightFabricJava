package phoupraw.mcmod.createsdelight.client;

import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;
import com.jozufozu.flywheel.backend.instancing.blockentity.SimpleBlockEntityInstancingController;
import phoupraw.mcmod.createsdelight.block.entity.VoxelMakerBlockEntity;
import phoupraw.mcmod.createsdelight.registry.CSDBlockEntityTypes;

public final class CSDInstancings {
    //public static final SimpleBlockEntityInstancingController<SprinklerBlockEntity> SPRINKLER = InstancedRenderRegistry.configure(CDBETypes.SPRINKLER).factory(SprinklerBlockEntity.Instance::new).apply();
    //public static final SimpleBlockEntityInstancingController<VerticalCutterBlockEntity> VERTICAL_CUTTING = InstancedRenderRegistry.configure(CDBETypes.VERTICAL_CUTTER).factory(VerticalCutterInstance::new).apply();
    //public static final SimpleBlockEntityInstancingController<PressureCookerBlockEntity> PRESSURE_COOKER = InstancedRenderRegistry.configure(CDBETypes.PRESSURE_COOKER).factory(PressureCookerInstance::new).apply();
    //public static final SimpleBlockEntityInstancingController<MincerBlockEntity> MINCER = InstancedRenderRegistry.configure(CDBETypes.MINCER).factory(MincerInstance::new).apply();
    //public static final SimpleBlockEntityInstancingController<SkewerBlockEntity> SKEWER = InstancedRenderRegistry.configure(CDBETypes.SKEWER).factory(SingleRotatingInstance::new).apply();
    //public static final SimpleBlockEntityInstancingController<InProdCakeBlockEntity> IN_PROD_CAKE = InstancedRenderRegistry.configure(CSDBlockEntityTypes.IN_PROD_CAKE).factory(InProdCakeInstance::new).apply();
    //public static final SimpleBlockEntityInstancingController<CakeOvenBlockEntity> CAKE_OVEN = InstancedRenderRegistry.configure(CSDBlockEntityTypes.CAKE_OVEN).factory(VerticalCogInstance::new).apply();
    public static final SimpleBlockEntityInstancingController<VoxelMakerBlockEntity> VOXEL_MAKER = InstancedRenderRegistry.configure(CSDBlockEntityTypes.VOXEL_MAKER).factory(VerticalCogInstance::new).apply();
    private CSDInstancings() {
    }
}
