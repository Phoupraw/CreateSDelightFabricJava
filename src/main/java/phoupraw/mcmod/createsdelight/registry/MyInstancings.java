package phoupraw.mcmod.createsdelight.registry;

import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;
import com.jozufozu.flywheel.backend.instancing.blockentity.SimpleBlockEntityInstancingController;
import phoupraw.mcmod.createsdelight.block.entity.PressureCookerBlockEntity;
import phoupraw.mcmod.createsdelight.block.entity.SprinklerBlockEntity;
import phoupraw.mcmod.createsdelight.block.entity.VerticalCutterBlockEntity;
import phoupraw.mcmod.createsdelight.instance.PressureCookerInstance;
import phoupraw.mcmod.createsdelight.instance.VerticalCutterInstance;
public final class MyInstancings {
    public static final SimpleBlockEntityInstancingController<SprinklerBlockEntity> SPRINKLER = InstancedRenderRegistry.configure(MyBlockEntityTypes.SPRINKLER).factory(SprinklerBlockEntity.Instance::new).apply();
    public static final SimpleBlockEntityInstancingController<VerticalCutterBlockEntity> VERTICAL_CUTTING = InstancedRenderRegistry.configure(MyBlockEntityTypes.VERTICAL_CUTTER).factory(VerticalCutterInstance::new).apply();
    public static final SimpleBlockEntityInstancingController<PressureCookerBlockEntity> PRESSURE_COOKER = InstancedRenderRegistry.configure(MyBlockEntityTypes.PRESSURE_COOKER).factory(PressureCookerInstance::new).apply();

    private MyInstancings() {}
}
