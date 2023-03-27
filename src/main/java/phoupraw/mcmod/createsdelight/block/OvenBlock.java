package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.block.ITE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import phoupraw.mcmod.createsdelight.block.entity.OvenBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class OvenBlock extends Block implements ITE<OvenBlockEntity> {
    public OvenBlock() {
        this(FabricBlockSettings.copyOf(AllBlocks.ITEM_VAULT.get()));
    }

    public OvenBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Class<OvenBlockEntity> getTileEntityClass() {
        return OvenBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends OvenBlockEntity> getTileEntityType() {
        return MyBlockEntityTypes.OVEN;
    }
}
