package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.block.entity.GrillBlockEntity;
import phoupraw.mcmod.createsdelight.block.entity.PanBlockEntity;
public final class MyBlockEntityTypes {
	public static final BlockEntityType<PanBlockEntity> PAN = FabricBlockEntityTypeBuilder.create(PanBlockEntity::new, MyBlocks.PAN).build();
	public static final BlockEntityType<GrillBlockEntity> GRILL = FabricBlockEntityTypeBuilder.create(GrillBlockEntity::new, MyBlocks.GRILL).build();
	static {
		Registry.register(Registry.BLOCK_ENTITY_TYPE, MyIdentifiers.PAN, PAN);
		Registry.register(Registry.BLOCK_ENTITY_TYPE,MyIdentifiers.GRILL, GRILL);
	}
	private MyBlockEntityTypes() {}
}
