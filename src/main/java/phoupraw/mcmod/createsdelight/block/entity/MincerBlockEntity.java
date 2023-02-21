package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.content.contraptions.processing.BasinOperatingTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.math.BlockPos;
import phoupraw.mcmod.createsdelight.recipe.MincingRecipe;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;
public class MincerBlockEntity extends BasinOperatingTileEntity {
    public MincerBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.MINCER, pos, state);}

    public MincerBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
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
        return MincingRecipe.class;
    }
}
