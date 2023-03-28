package phoupraw.mcmod.createsdelight.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.PaneBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
public class IronBarsBlock extends PaneBlock {
    public IronBarsBlock(Settings settings) {
        super(settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!state.getProperties().equals(getDefaultState().getProperties())) {
            return super.onUse(state, world, pos, player, hand, hit);
        }
//        ItemStack stackInHand = player.getStackInHand(hand);
//        var recipe = RecipeFinder.get(IronBarsBlock.class, world, RecipeConditions.isOfType(RecipeType.CAMPFIRE_COOKING).and(RecipeConditions.firstIngredientMatches(stackInHand))).stream().findAny().orElse(null);
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
