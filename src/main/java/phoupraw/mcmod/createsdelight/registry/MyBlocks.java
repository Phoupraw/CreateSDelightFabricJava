package phoupraw.mcmod.createsdelight.registry;

import com.nhoryzon.mc.farmersdelight.registry.BlocksRegistry;
import com.simibubi.create.AllBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import phoupraw.mcmod.common.Registries;
import phoupraw.mcmod.createsdelight.block.*;
/**
 * @see MyBlockEntityTypes
 */
public final class MyBlocks {
	public static final PanBlock PAN = new PanBlock(FabricBlockSettings.copyOf(BlocksRegistry.SKILLET.get()));
	public static final GrillBlock GRILL = new GrillBlock(FabricBlockSettings.copyOf(PAN).nonOpaque());
	public static final SprinklerBlock SPRINKLER = new SprinklerBlock(FabricBlockSettings.copyOf(AllBlocks.MECHANICAL_PRESS.get()));
	public static final BambooSteamerBlock BAMBOO_STEAMER = new BambooSteamerBlock(FabricBlockSettings.copyOf(BlocksRegistry.BASKET.get()));
	public static final SmartDrainBlock SMART_DRAIN = new SmartDrainBlock(FabricBlockSettings.copyOf(AllBlocks.ITEM_DRAIN.get()));
	public static final CopperTunnelBlock COPPER_TUNNEL = new CopperTunnelBlock(FabricBlockSettings.copyOf(AllBlocks.BRASS_TUNNEL.get()));
	public static final MultifuncBasinBlock MULTIFUNC_BASIN = new MultifuncBasinBlock(FabricBlockSettings.copyOf(AllBlocks.BASIN.get()));
	public static final VerticalCutterBlock VERTICAL_CUTTER = new VerticalCutterBlock(FabricBlockSettings.copyOf(AllBlocks.MECHANICAL_PRESS.get()));
	public static final PressureCookerBlock PRESSURE_COOKER = new PressureCookerBlock(FabricBlockSettings.copyOf(AllBlocks.MECHANICAL_PRESS.get()));
	public static final Block MINCER = new MincerBlock(FabricBlockSettings.copyOf(AllBlocks.MECHANICAL_MIXER.get()));
    public static final Block SKEWER = new SkewerBlock();
	static {
        Registries.register(MyIdentifiers.PAN, PAN);
        Registries.register(MyIdentifiers.GRILL, GRILL);
        Registries.register(MyIdentifiers.SPRINKLER, SPRINKLER);
        Registries.register(MyIdentifiers.BAMBOO_STEAMER, BAMBOO_STEAMER);
        Registries.register(MyIdentifiers.SMART_DRAIN, SMART_DRAIN);
        Registries.register(MyIdentifiers.COPPER_TUNNEL, COPPER_TUNNEL);
        Registries.register(MyIdentifiers.MULTIFUNC_BASIN, MULTIFUNC_BASIN);
        Registries.register(MyIdentifiers.VERTICAL_CUTTER, VERTICAL_CUTTER);
        Registries.register(MyIdentifiers.PRESSURE_COOKER, PRESSURE_COOKER);
        Registries.register(MyIdentifiers.MINCER, MINCER);
        Registries.register(MyIdentifiers.SKEWER, SKEWER);
    }
	private MyBlocks() {}
}
