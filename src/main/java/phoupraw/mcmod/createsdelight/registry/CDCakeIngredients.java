package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;

public final class CDCakeIngredients {

public static final BlockApiLookup<CakeIngredient, @Nullable Void> BLOCK = BlockApiLookup.get(CDIdentifiers.CAKE_INGREDIENT, CakeIngredient.class, Void.class);

public static final CakeIngredient HONEY = CakeIngredient.of(14.4/*从蜜渍苹果计算*/ * 4, new Identifier("block/honey_block_side"));
public static final CakeIngredient MILK = CakeIngredient.of(4.6/*从奶油甜甜卷计算*/ * 3, CDIdentifiers.of("block/milk"));
public static final CakeIngredient CHOCOLATE = CakeIngredient.of(9.6/*从巧克力棒计算*/ * 3, CDIdentifiers.of("block/chocolate"));
static {
    register(CDIdentifiers.HONEY, HONEY);
    register(CDIdentifiers.MILK, MILK);
    register(CDIdentifiers.CHOCOLATE, CHOCOLATE);

    BLOCK.registerForBlocks(constant(HONEY), Blocks.HONEY_BLOCK);
    BLOCK.registerForBlocks(constant(MILK), CDBlocks.MILK);
    BLOCK.registerForBlocks(constant(CHOCOLATE), CDBlocks.CHOCOLATE);
}
private static void register(Identifier id, CakeIngredient cakeIngredient) {
    Registry.register(CDRegistries.CAKE_INGREDIENT, id, cakeIngredient);
}

public static BlockApiLookup.BlockApiProvider<CakeIngredient, @Nullable Void> constant(CakeIngredient theConst) {
    return (world, pos, state, blockEntity, context) -> theConst;
}

}
