package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.foundation.block.IBE;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.block.entity.OvenBlockEntity;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class OvenBlock extends Block implements IBE<OvenBlockEntity> {
    public OvenBlock() {
        this(FabricBlockSettings.copyOf(AllBlocks.ITEM_VAULT.get()).nonOpaque());
    }

    public OvenBlock(Settings settings) {
        super(settings);
    }

    @Override
    public Class<OvenBlockEntity> getBlockEntityClass() {
        return OvenBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends OvenBlockEntity> getBlockEntityType() {
        return MyBlockEntityTypes.OVEN;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        IBE.onRemove(state, world, pos, newState);
        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
