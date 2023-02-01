package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.block.entity.PanBlockEntity;
public final class MyBlockEntityTypes {
	public static final BlockEntityType<PanBlockEntity> PAN = FabricBlockEntityTypeBuilder.create(PanBlockEntity::new, MyBlocks.PAN).build();
	static {
		Registry.register(Registry.BLOCK_ENTITY_TYPE, Registry.BLOCK.getId(MyBlocks.PAN), PAN);
	}
	private MyBlockEntityTypes() {}
}
