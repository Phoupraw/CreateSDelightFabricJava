package phoupraw.mcmod.createsdelight.misc;

import com.simibubi.create.AllItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.apache.commons.lang3.tuple.Pair;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class BlockFoods {
    public static final FoodComponent CAKE = new FoodComponent.Builder().hunger(2 * 7).saturationModifier(0.1f).build();
    public static final DefaultedMap<Item, FoodComponent> ITEM = new FunctionDefaultedMap<>(new HashMap<>(), Item::getFoodComponent);
    public static final Map<Block, FoodBehaviour> BLOCK = new HashMap<>();
    static {
        //VOXEL.registerForBlocks((world, pos, state, blockEntity, context) -> new FoodComponent.Builder().hunger(2 * ((CakeBlock.MAX_BITES + 1 - state.get(CakeBlock.BITES)) * 2)).saturationModifier(0.1f).build(), Blocks.CAKE);
        //BLOCK_STATE.put(Blocks.CAKE.getDefaultState(), LinearFoodBehaviour.CAKE);
        addBuiltins();
    }
    public static FoodComponent sum(Item... items) {
        return sum(Arrays.stream(items).map(ITEM::get).toArray(FoodComponent[]::new));
    }
    public static FoodComponent sum(FoodComponent... foodComponents) {
        int hunger = 0;
        float saturation = 0;
        for (FoodComponent fc : foodComponents) {
            hunger += fc.getHunger();
            saturation += fc.getHunger() * fc.getSaturationModifier();
        }
        return new FoodComponent.Builder().hunger(hunger).saturationModifier(saturation / hunger).build();
    }
    @SafeVarargs
    public static FoodComponent linear(Pair<FoodComponent, Double>... food2factors) {
        var fb = LinearFoodBehaviour.linear(food2factors);
        return new FoodComponent.Builder().hunger((int) /*Math.round*/(fb.hunger)).saturationModifier((float) (fb.saturation / fb.hunger / 2)).build();
    }
    private static void addBuiltins() {
        ITEM.put(Items.CAKE, CAKE);
        ITEM.put(Items.MILK_BUCKET, linear(
          Pair.of(ITEM.get(AllItems.SWEET_ROLL.get()), 3.0),
          Pair.of(FoodComponents.BREAD, -3.0)));
        ITEM.put(Items.SUGAR, linear(
          Pair.of(ITEM.get(Items.HONEY_BOTTLE), 1.0 / 3)));
        ITEM.put(Items.WHEAT, linear(
          Pair.of(ITEM.get(Items.BREAD), 1.0 / 3)));
        ITEM.put(Items.EGG, linear(
          Pair.of(ITEM.get(Items.CAKE), 1.0),
          Pair.of(ITEM.get(Items.MILK_BUCKET), -3.0),
          Pair.of(ITEM.get(Items.SUGAR), -2.0),
          Pair.of(ITEM.get(Items.WHEAT), -3.0)));
        ITEM.put(Items.PUMPKIN, linear(
          Pair.of(ITEM.get(Items.PUMPKIN_PIE), 1.0),
          Pair.of(ITEM.get(Items.SUGAR), 1.0),
          Pair.of(ITEM.get(Items.EGG), 1.0)));
        BLOCK.put(Blocks.HONEY_BLOCK, LinearFoodBehaviour.of(ITEM.get(Items.HONEY_BOTTLE), 1.0 / 4));
        BLOCK.put(CSDBlocks.CHOCOLATE_BLOCK, LinearFoodBehaviour.of(ITEM.get(AllItems.BAR_OF_CHOCOLATE.get()), 1.0 / 3));
        BLOCK.put(CSDBlocks.WHEAT_CAKE_BASE_BLOCK, LinearFoodBehaviour.linear(Pair.of(ITEM.get(Items.BREAD), 5.0 * 3 / 2)));
        var cream = LinearFoodBehaviour.of(ITEM.get(Items.MILK_BUCKET), 1);
        BLOCK.put(CSDBlocks.CREAM, cream);
        BLOCK.put(CSDBlocks.APPLE_JAM, LinearFoodBehaviour.linear(Pair.of(ITEM.get(Items.APPLE), 9.0), Pair.of(ITEM.get(Items.SUGAR), 1.5)));
        BLOCK.put(CSDBlocks.WHEAT_PASTE, LinearFoodBehaviour.linear(Pair.of(ITEM.get(Items.BREAD), 5.0 * 3 / 2 * 8 / 9)));
        BLOCK.put(CSDBlocks.BUTTER_BLOCK, cream.mul(2));
    }
    private BlockFoods() {}
}
