package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.mixin.MixinTileEntityBehaviour;

import java.util.List;
import java.util.Objects;
/**
 * @see MixinTileEntityBehaviour
 */
public class FakeSmartTileEntity extends SmartBlockEntity {
	public FakeSmartTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

	}

	/**
	 *
	 * @throws NullPointerException {@code blockEntity}的{@link BlockEntity#getWorld()}返回{@code null}时
	 */
	public static SmartBlockEntity of(BlockEntity blockEntity) {
		return of(Objects.requireNonNull(blockEntity.getWorld()), blockEntity.getPos(), blockEntity.getCachedState(), blockEntity.getType());
	}

	/**
	 * 即使不是方块实体也可以创建实例
	 *
	 * @param blockState 如果为{@code null}，则调用{@link World#getBlockState(BlockPos)}获取。
	 * @param type       如果为{@code null}，则默认为{@link BlockEntityType#BARREL}。
	 */
	public static SmartBlockEntity of(@NotNull World world, BlockPos blockPos, @Nullable BlockState blockState, @Nullable BlockEntityType<?> type) {
		if (blockState == null) blockState = world.getBlockState(blockPos);
		if (type == null) type = BlockEntityType.BARREL;
		FakeSmartTileEntity fake = new FakeSmartTileEntity(type, blockPos, blockState);
		fake.setWorld(world);
		return fake;
	}
}
