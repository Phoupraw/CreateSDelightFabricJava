package phoupraw.mcmod.createsdelight.registry;

import com.jozufozu.flywheel.backend.instancing.InstancedRenderRegistry;
import com.jozufozu.flywheel.backend.instancing.blockentity.SimpleBlockEntityInstancingController;
import phoupraw.mcmod.createsdelight.block.entity.SprinklerBlockEntity;
public final class MyInstancings {
    public static final SimpleBlockEntityInstancingController<SprinklerBlockEntity> SPRINKLER = InstancedRenderRegistry.configure(MyBlockEntityTypes.SPRINKLER).factory(SprinklerBlockEntity.Instance::new).apply();

    private MyInstancings() {}
}
