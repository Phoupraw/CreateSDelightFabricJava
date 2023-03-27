package phoupraw.mcmod.createsdelight.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
public class AppleCakeBlock extends Block {
    public AppleCakeBlock() {
        this(FabricBlockSettings.copyOf(MyBlocks.JELLY_BEANS_CAKE));
    }

    public AppleCakeBlock(Settings settings) {
        super(settings);
    }
}
