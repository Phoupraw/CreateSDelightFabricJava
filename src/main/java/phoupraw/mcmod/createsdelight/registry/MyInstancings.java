package phoupraw.mcmod.createsdelight.registry;

import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;
import com.jozufozu.flywheel.backend.instancing.blockentity.SimpleBlockEntityInstancingController;
import com.simibubi.create.content.kinetics.base.SingleRotatingInstance;
import phoupraw.mcmod.createsdelight.block.entity.*;
import phoupraw.mcmod.createsdelight.instance.MincerInstance;
import phoupraw.mcmod.createsdelight.instance.PressureCookerInstance;
import phoupraw.mcmod.createsdelight.instance.VerticalCutterInstance;
public final class MyInstancings {
    public static final SimpleBlockEntityInstancingController<SprinklerBlockEntity> SPRINKLER = InstancedRenderRegistry.configure(MyBlockEntityTypes.SPRINKLER).factory(SprinklerBlockEntity.Instance::new).apply();
    public static final SimpleBlockEntityInstancingController<VerticalCutterBlockEntity> VERTICAL_CUTTING = InstancedRenderRegistry.configure(MyBlockEntityTypes.VERTICAL_CUTTER).factory(VerticalCutterInstance::new).apply();
    public static final SimpleBlockEntityInstancingController<PressureCookerBlockEntity> PRESSURE_COOKER = InstancedRenderRegistry.configure(MyBlockEntityTypes.PRESSURE_COOKER).factory(PressureCookerInstance::new).apply();
    public static final SimpleBlockEntityInstancingController<MincerBlockEntity> MINCER = InstancedRenderRegistry.configure(MyBlockEntityTypes.MINCER).factory(MincerInstance::new).apply();
    public static final SimpleBlockEntityInstancingController<SkewerBlockEntity> SKEWER = InstancedRenderRegistry.configure(MyBlockEntityTypes.SKEWER).factory(SingleRotatingInstance::new).apply();
    public static final SimpleBlockEntityInstancingController<IronBarSkewerBlockEntity> IRON_BAR_SKEWER = InstancedRenderRegistry.configure(MyBlockEntityTypes.IRON_BAR_SKEWER).factory(SingleRotatingInstance::new).apply();

    private MyInstancings() {}
}
