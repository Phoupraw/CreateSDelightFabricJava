package phoupraw.mcmod.createsdelight.registry;

import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.block.PanBlock;
public final class MyBlocks {
	public static final PanBlock PAN = new PanBlock(FabricBlockSettings.copyOf(BlocksRegistry.SKILLET.get()));
	static {
		Registry.register(Registry.BLOCK, new Identifier(CreateSDelight.MOD_ID, "pan"), PAN);
	}
	private MyBlocks() {}
}
