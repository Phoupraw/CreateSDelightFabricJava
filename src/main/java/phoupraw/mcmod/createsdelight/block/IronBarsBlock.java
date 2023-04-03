package phoupraw.mcmod.createsdelight.block;

import com.simibubi.create.content.contraptions.wrench.IWrenchable;
import com.simibubi.create.foundation.utility.recipe.RecipeConditions;
import com.simibubi.create.foundation.utility.recipe.RecipeFinder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
public class IronBarsBlock extends PaneBlock implements IWrenchable {
    public static final IWrenchable I_WRENCHABLE = new IWrenchable() {};

    @SuppressWarnings("deprecation")
    public static ActionResult onUse(Block subject, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.getEntries().equals(subject.getDefaultState().getEntries())) {
            ItemStack stackInHand = player.getStackInHand(hand);
            if (RecipeFinder.get(IronBarsBlock.class, world, RecipeConditions.isOfType(RecipeType.CAMPFIRE_COOKING)).stream().anyMatch(RecipeConditions.firstIngredientMatches(stackInHand))) {
                BlockState skewerState = MyBlocks.IRON_BAR_SKEWER.getDefaultState();
                world.setBlockState(pos, skewerState);
                return MyBlocks.IRON_BAR_SKEWER.onUse(skewerState, world, pos, player, hand, hit);
            }
        }
        return ActionResult.PASS;
    }

    public static BlockState getRotatedBlockState(Block subject, BlockState originalState, Direction targetedFace) {
        if (targetedFace.getAxis().isVertical() || !originalState.getEntries().equals(subject.getDefaultState().getEntries())) return originalState;
        return I_WRENCHABLE.getRotatedBlockState(MyBlocks.IRON_BAR_SKEWER.getDefaultState(), targetedFace);
    }

    public IronBarsBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        var result = onUse(this, state, world, pos, player, hand, hit);
        if (result != ActionResult.PASS) return result;
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
        return getRotatedBlockState(this, originalState, targetedFace);
    }


}
