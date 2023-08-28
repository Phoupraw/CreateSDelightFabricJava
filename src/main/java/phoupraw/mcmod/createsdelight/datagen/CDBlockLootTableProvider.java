package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.CopyNameLootFunction;
import net.minecraft.loot.function.CopyNbtLootFunction;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import phoupraw.mcmod.createsdelight.registry.CDBlocks;
import phoupraw.mcmod.createsdelight.registry.CDItems;
public final class CDBlockLootTableProvider extends FabricBlockLootTableProvider {

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

    public CDBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }


    @Override
    public void generate() {
        addDrop(CDBlocks.PRINTED_CAKE, LootTable.builder().pool(new LootPool.Builder()
          .rolls(ConstantLootNumberProvider.create(1))
          .with(ItemEntry.builder(CDItems.PRINTED_CAKE)
            .apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY))
            .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
              .withOperation("content", "BlockEntityTag.content"))
            .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
              .withOperation("size", "BlockEntityTag.size")))));
        //for (Block block : new Block[]{CDBlocks.JELLY_BEANS, CDBlocks.BROWNIE, CDBlocks.SMALL_CHOCOLATE_CREAM_CAKE, CDBlocks.MILK, CDBlocks.CHOCOLATE, CDBlocks.CAKE_OVEN}) {
        //    addDrop(block);
        //}
        //addDropAge3(this, CDBlocks.JELLY_BEANS_CAKE, CDItems.JELLY_BEANS, 1);
        //addDropAge3(this, CDBlocks.BASQUE_CAKE, Items.AIR, 0);
        //addDropAge3(this, CDBlocks.SWEET_BERRIES_CAKE_S, CDItems.SWEET_BERRIES_CAKE, 3);
        //addDropAge3(this, CDBlocks.APPLE_CREAM_CAKE, Items.APPLE, 1);
        //addDropAge3(this, CDBlocks.APPLE_CAKE, Items.APPLE, 1);
        //addDropAge3(this, CDBlocks.CARROT_CREAM_CAKE, Items.CARROT, 1);
        //addDropAge3(this, CDBlocks.MEDIUM_CHOCOLATE_CREAM_CAKE, Items.AIR, 0);
        //addDropAge3(this, CDBlocks.BIG_CHOCOLATE_CREAM_CAKE, Items.AIR, 0);
        //addDropAge3(this, CDBlocks.CHOCOLATE_ANTHEMY_CAKE, Items.AIR, 0);
        //{
        //    List<LeafEntry.Builder<?>> list = new ArrayList<>();
        //    for (int i = 0; i <= Properties.AGE_3_MAX; i++) {
        //        LeafEntry.Builder<?> apply = ItemEntry.builder(CDItems.SWEET_BERRIES_CAKE)
        //          .conditionally(BlockStatePropertyLootCondition.builder(CDBlocks.SWEET_BERRIES_CAKE).properties(StatePredicate.Builder.create().exactMatch(Properties.AGE_3, i)))
        //          .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(i + 1)));
        //        list.add(apply);
        //    }
        //    addDrop(CDBlocks.SWEET_BERRIES_CAKE, LootTable.builder().pool(new LootPool.Builder()
        //      .rolls(ConstantLootNumberProvider.create(1))
        //      .with(AlternativeEntry.builder(list.toArray(new LootPoolEntry.Builder[0])))));
        //}
    }
}
