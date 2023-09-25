package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.CopyNameLootFunction;
import net.minecraft.loot.function.CopyNbtLootFunction;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;
import phoupraw.mcmod.createsdelight.registry.CSDItems;

public final class CSDBlockLootTableProvider extends FabricBlockLootTableProvider {

    //public static void addDropAge3(BlockLootTableGenerator generator, Block block, Item item, int count) {
    //    var alternative = AlternativeEntry.builder(
    //      ItemEntry.builder(block)
    //        .conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(Properties.AGE_3, 0))));
    //    if (item != Items.AIR && count != 0) {
    //        var itemEntry = ItemEntry.builder(item);
    //        if (count != 1) {
    //            itemEntry.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(count)));
    //        }
    //        alternative.alternatively(itemEntry);
    //    }
    //    generator.addDrop(block, LootTable.builder().pool(new LootPool.Builder()
    //      .rolls(ConstantLootNumberProvider.create(1))
    //      .with(alternative)));
    //}

    public CSDBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        for (Block block : new Block[]{CSDBlocks.CAKE_OVEN, CSDBlocks.VOXEL_MAKER, CSDBlocks.CHOCOLATE_BLOCK, CSDBlocks.WHEAT_CAKE_BASE_BLOCK}) {
            addDrop(block);//方块无条件掉落自身
        }
        addDrop(CSDBlocks.PRINTED_CAKE, LootTable.builder().pool(new LootPool.Builder()
          .rolls(ConstantLootNumberProvider.create(1))
          .with(ItemEntry.builder(CSDItems.PRINTED_CAKE)
            .apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY))
            .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
              .withOperation("content", "BlockEntityTag.content"))
            .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
              .withOperation("size", "BlockEntityTag.size")))));
        addDrop(CSDBlocks.MADE_VOXEL, LootTable.builder().pool(new LootPool.Builder()
          .rolls(ConstantLootNumberProvider.create(1))
          .with(ItemEntry.builder(CSDItems.MADE_VOXEL)
            .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
              .withOperation("{}", "BlockEntityTag")))));
    }

}
