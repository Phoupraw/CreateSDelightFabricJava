package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.block.ITE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.block.entity.OvenBlockEntity;
import phoupraw.mcmod.createsdelight.registry.CDBlockEntityTypes;
public class OvenBlock extends Block implements ITE<OvenBlockEntity> {
    public OvenBlock() {
        this(FabricBlockSettings.copyOf(AllBlocks.ITEM_VAULT.get()).nonOpaque());
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
        return CDBlockEntityTypes.OVEN;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        ITE.onRemove(state, world, pos, newState);
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
