package phoupraw.mcmod.createsdelight.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.ModelIds;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;

public final class CSDCakeIngredients {

    public static final BlockApiLookup<CakeIngredient, @Nullable Void> LOOKUP = BlockApiLookup.get(CSDIdentifiers.CAKE_INGREDIENT, CakeIngredient.class, Void.class);
    public static final BiMap<Block, CakeIngredient> BLOCK = HashBiMap.create();

    public static final CakeIngredient HONEY = CakeIngredient.of(14.4/*从蜜渍苹果计算*/ * 4, new Identifier("block/honey_block_side"));
    public static final CakeIngredient CREAM_BLOCK = CakeIngredient.of(4.6/*从奶油甜甜卷计算*/ * 3, ModelIds.getBlockModelId(CSDBlocks.CREAM_BLOCK));
    public static final CakeIngredient CHOCOLATE = CakeIngredient.of(9.6/*从巧克力棒计算*/ * 3, ModelIds.getBlockModelId(CSDBlocks.CHOCOLATE_BLOCK));
    public static final CakeIngredient PUMPKIN = CakeIngredient.of(3 * (1 + 0.3 * 2)/*从农夫乐事南瓜片计算*/ * 4, new Identifier("block/pumpkin_side"));
    public static final CakeIngredient MELON = CakeIngredient.of(2 * (1 + 0.3 * 2)/*从西瓜片计算*/ * 9, new Identifier("block/melon_side"));
    public static final CakeIngredient HAY = CakeIngredient.of(5 * (1 + 0.6 * 2)/*从面包计算*/ * 3, new Identifier("block/hay_block_top"));
    static {
        register(CSDIdentifiers.HONEY, HONEY);
        register(CSDIdentifiers.CREAM_BLOCK, CREAM_BLOCK);
        register(CSDIdentifiers.CHOCOLATE, CHOCOLATE);
        register(CSDIdentifiers.PUMPKIN, PUMPKIN);
        register(CSDIdentifiers.MELON, MELON);
        register(CSDIdentifiers.HAY, HAY);

        BLOCK.put(Blocks.HONEY_BLOCK, HONEY);
        BLOCK.put(CSDBlocks.CREAM_BLOCK, CREAM_BLOCK);
        BLOCK.put(CSDBlocks.CHOCOLATE_BLOCK, CHOCOLATE);
        BLOCK.put(Blocks.PUMPKIN, PUMPKIN);
        BLOCK.put(Blocks.MELON, MELON);
        BLOCK.put(Blocks.HAY_BLOCK, HAY);

        LOOKUP.registerFallback((world, pos, state, blockEntity, context) -> BLOCK.get(state.getBlock()));
    }

    public static BlockApiLookup.BlockApiProvider<CakeIngredient, @Nullable Void> constant(CakeIngredient theConst) {
        return (world, pos, state, blockEntity, context) -> theConst;
    }

    private static void register(Identifier id, CakeIngredient cakeIngredient) {
        Registry.register(CDRegistries.CAKE_INGREDIENT, id, cakeIngredient);
    }

}
