package phoupraw.mcmod.createsdelight.registry;

import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.block.GrillBlock;
import phoupraw.mcmod.createsdelight.block.PanBlock;
public final class MyBlocks {
	public static final PanBlock PAN = new PanBlock(FabricBlockSettings.copyOf(BlocksRegistry.SKILLET.get()));
	public static final GrillBlock GRILL = new GrillBlock(FabricBlockSettings.copyOf(BlocksRegistry.STOVE.get()));
	static {
		Registry.register(Registry.BLOCK, MyIdentifiers.PAN, PAN);
		Registry.register(Registry.BLOCK, MyIdentifiers.GRILL, GRILL);
	}
	private MyBlocks() {}
}
