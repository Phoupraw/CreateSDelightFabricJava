package phoupraw.mcmod.createsdelight.misc;

import com.simibubi.create.AllItems;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import org.apache.commons.lang3.tuple.Pair;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;
import phoupraw.mcmod.createsdelight.registry.CSDIdentifiers;

import java.util.HashMap;
import java.util.Map;

public final class BlockFoods {
    @Deprecated
    @SuppressWarnings("unchecked")
    public static final BlockApiLookup<FoodComponent, Pair<Map<BlockPos, BlockState>, Vec3i>> VOXEL = BlockApiLookup.get(CSDIdentifiers.of("block_food"), FoodComponent.class, (Class<Pair<Map<BlockPos, BlockState>, Vec3i>>) (Object) Pair.class);
    public static final Map<BlockState/*TODO 改成Block*/, FoodBehaviour> BLOCK_STATE = new HashMap<>();
    static {
        //VOXEL.registerForBlocks((world, pos, state, blockEntity, context) -> new FoodComponent.Builder().hunger(2 * ((CakeBlock.MAX_BITES + 1 - state.get(CakeBlock.BITES)) * 2)).saturationModifier(0.1f).build(), Blocks.CAKE);
        //BLOCK_STATE.put(Blocks.CAKE.getDefaultState(), LinearFoodBehaviour.CAKE);
        addBuiltins();
    }
    @Deprecated
    public static FoodComponent combinedHunger(Iterable<FoodComponent> foodComponents) {
        int hunger = 0;
        float saturation = 0;
        for (FoodComponent foodComponent : foodComponents) {
            hunger += foodComponent.getHunger();
            saturation += foodComponent.getHunger() * foodComponent.getSaturationModifier();
        }
        return new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation / hunger).build();
    }
    @SuppressWarnings("ConstantConditions")
    private static void addBuiltins() {
        BLOCK_STATE.put(Blocks.HONEY_BLOCK.getDefaultState(), LinearFoodBehaviour.subtract(AllItems.HONEYED_APPLE.get().getFoodComponent(), FoodComponents.APPLE, 1.0 / 4));//(蜜渍苹果-苹果)×(1桶÷1瓶(蜂蜜))
        BLOCK_STATE.put(CSDBlocks.CHOCOLATE_BLOCK.getDefaultState(), LinearFoodBehaviour.of(AllItems.BAR_OF_CHOCOLATE.get().getFoodComponent(), 1.0 / 3));//巧克力棒×(1块÷1巧克力棒)
        BLOCK_STATE.put(CSDBlocks.CREAM_BLOCK.getDefaultState(), LinearFoodBehaviour.subtract(AllItems.SWEET_ROLL.get().getFoodComponent(), FoodComponents.BREAD, 1.0 / 3));//(奶油甜甜卷-面包)×(1桶÷1瓶)
        BLOCK_STATE.put(CSDBlocks.APPLE_JAM_BLOCK.getDefaultState(), LinearFoodBehaviour.subtract())
    }
    private BlockFoods() {}
}
