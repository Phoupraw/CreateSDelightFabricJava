package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.content.contraptions.processing.BasinOperatingTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.math.BlockPos;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class PressureCookerBlockEntity extends BasinOperatingTileEntity implements InstanceOffset {
    public PressureCookerBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.PRESSURE_COOKER, pos, state);}

    public PressureCookerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }

    @Override
    protected boolean isRunning() {
        return false;
    }

    @Override
    protected void onBasinRemoved() {

    }

    @Override
    protected <C extends Inventory> boolean matchStaticFilters(Recipe<C> recipe) {
        return false;
    }

    @Override
    protected Object getRecipeCacheKey() {
        return PressureCookerBlockEntity.class;
    }

    @Override
    public double getOffset(float partialTicks) {

        return 0;
    }
}
