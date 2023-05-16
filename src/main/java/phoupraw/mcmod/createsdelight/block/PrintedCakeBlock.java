package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.foundation.block.ITE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import phoupraw.mcmod.createsdelight.block.entity.PrintedCakeBE;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
public class PrintedCakeBlock extends Block implements ITE<PrintedCakeBE> {
    public PrintedCakeBlock() {
        this(FabricBlockSettings.copyOf(MyBlocks.JELLY_BEANS_CAKE));
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
        return MyBlockEntityTypes.PRINTED_CAKE;
    }
}
