package phoupraw.mcmod.createsdelight.misc;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CakeBlock;
import net.minecraft.item.FoodComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.apache.commons.lang3.tuple.Pair;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;
import phoupraw.mcmod.createsdelight.registry.CSDIdentifiers;

import java.util.HashMap;
import java.util.Map;

public final class BlockFoods {
    @SuppressWarnings("unchecked") public static final BlockApiLookup<FoodComponent, Pair<Map<BlockPos, BlockState>, Vec3i>> VOXEL = BlockApiLookup.get(CSDIdentifiers.of("block_food"), FoodComponent.class, (Class<Pair<Map<BlockPos, BlockState>, Vec3i>>) (Object) Pair.class);
    public static final Map<BlockState, FoodBehaviour> BLOCK_STATE = new HashMap<>();
    public static FoodComponent combinedHunger(Iterable<FoodComponent> foodComponents) {
        int hunger = 0;
        float saturation = 0;
        for (FoodComponent foodComponent : foodComponents) {
            hunger += foodComponent.getHunger();
            saturation += foodComponent.getHunger() * foodComponent.getSaturationModifier();
        }
        return new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation / hunger).build();
    }
    static {
        VOXEL.registerForBlocks((world, pos, state, blockEntity, context) -> new FoodComponent.Builder().hunger(2 * ((CakeBlock.MAX_BITES + 1 - state.get(CakeBlock.BITES)) * 2)).saturationModifier(0.1f).build(), Blocks.CAKE);
        BLOCK_STATE.put(Blocks.CAKE.getDefaultState(), LinearFoodBehaviour.CAKE);
        BLOCK_STATE.put(Blocks.HONEY_BLOCK.getDefaultState(), new LinearFoodBehaviour((8 - 4) * 4, (8 * 0.8 - 4 * 0.3) * 2 * 4, 0, 0));//(蜜渍苹果-苹果)×(1桶÷1瓶(蜂蜜))
        BLOCK_STATE.put(CSDBlocks.CHOCOLATE_BLOCK.getDefaultState(), new LinearFoodBehaviour(6 * 3, 6 * 0.3 * 2 * 3, 0, 0));//巧克力棒×(1块÷1巧克力棒)
        BLOCK_STATE.put(CSDBlocks.CREAM_BLOCK.getDefaultState(), new LinearFoodBehaviour((6 - 5) * 3, (6 * 0.8 - 5 * 0.6) * 2 * 3, 0, 0));//(奶油甜甜卷-面包)×(1桶÷1瓶)
    }
    private BlockFoods() {}
}
