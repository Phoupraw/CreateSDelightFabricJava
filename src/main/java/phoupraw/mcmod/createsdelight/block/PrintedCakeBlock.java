package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.foundation.block.ITE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import phoupraw.mcmod.createsdelight.block.entity.PrintedCakeBE;
import phoupraw.mcmod.createsdelight.registry.CDBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.CDBlocks;
public class PrintedCakeBlock extends Block implements ITE<PrintedCakeBE> {
    public PrintedCakeBlock() {
        this(FabricBlockSettings.copyOf(CDBlocks.JELLY_BEANS_CAKE));
    }

    public PrintedCakeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Class<PrintedCakeBE> getTileEntityClass() {
        return PrintedCakeBE.class;
    }

    @Override
    public BlockEntityType<? extends PrintedCakeBE> getTileEntityType() {
        return CDBlockEntityTypes.PRINTED_CAKE;
    }
}
