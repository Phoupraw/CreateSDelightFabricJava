package phoupraw.mcmod.createsdelight;

import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
public class FakeSmartTileEntity extends SmartTileEntity {
    public FakeSmartTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {

    }

    public static SmartTileEntity of(BlockEntity blockEntity) {
        return of(Objects.requireNonNull(blockEntity.getWorld()), blockEntity.getPos(), blockEntity.getCachedState(), blockEntity.getType());
    }

    public static SmartTileEntity of(@NotNull World world, BlockPos blockPos, @Nullable BlockState blockState, @Nullable BlockEntityType<?> type) {
        if (blockState == null) blockState = world.getBlockState(blockPos);
        if (type == null) type = BlockEntityType.BARREL;
        FakeSmartTileEntity fake = new FakeSmartTileEntity(type, blockPos, blockState);
        fake.setWorld(world);
        return fake;
    }
}
