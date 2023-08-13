package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import phoupraw.mcmod.createsdelight.block.entity.MyBasinBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class MyBasinBlock extends Block implements IBE<MyBasinBlockEntity>, IWrenchable {
    public MyBasinBlock(Settings settings) {
        super(settings);
    }

    public MyBasinBlock() {
        super(FabricBlockSettings.copyOf(AllBlocks.BASIN.get()));
    }

    @Override
    public Class<MyBasinBlockEntity> getBlockEntityClass() {
        return MyBasinBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends MyBasinBlockEntity> getBlockEntityType() {
        return MyBlockEntityTypes.BASIN;
    }
}
