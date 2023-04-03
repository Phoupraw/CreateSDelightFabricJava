package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class IronBarSkewerBlockEntity extends KineticTileEntity {
    public IronBarSkewerBlockEntity(BlockPos pos, BlockState state) {
        this(MyBlockEntityTypes.IRON_BAR_SKEWER, pos, state);
    }

    public IronBarSkewerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }
}
