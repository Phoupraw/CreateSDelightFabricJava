package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.state.property.Properties;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
import phoupraw.mcmod.createsdelight.registry.MyItems;

import java.util.ArrayList;
import java.util.List;

public final class MyBlockLootTableProvider extends FabricBlockLootTableProvider {
    public static void addDrop(BlockLootTableGenerator generator, Block... blocks) {
        for (Block block : blocks) generator.addDrop(block);
    }

    public MyBlockLootTableProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        addDrop(this, MyBlocks.PAN, MyBlocks.GRILL, MyBlocks.SPRINKLER, MyBlocks.BAMBOO_STEAMER, MyBlocks.SMART_DRAIN, MyBlocks.COPPER_TUNNEL, MyBlocks.MULTIFUNC_BASIN, MyBlocks.VERTICAL_CUTTER, MyBlocks.PRESSURE_COOKER, MyBlocks.MINCER, MyBlocks.SKEWER, MyBlocks.BASIN, MyBlocks.SKEWER_PLATE, MyBlocks.JELLY_BEANS, MyBlocks.BROWNIE);
        addDrop(MyBlocks.JELLY_BEANS_CAKE, LootTable.builder().pool(new LootPool.Builder()
          .rolls(ConstantLootNumberProvider.create(1))
          .with(AlternativeEntry.builder(
            ItemEntry.builder(MyItems.JELLY_BEANS_CAKE)
              .conditionally(BlockStatePropertyLootCondition.builder(MyBlocks.JELLY_BEANS_CAKE).properties(StatePredicate.Builder.create().exactMatch(Properties.AGE_3, 0))),
            ItemEntry.builder(MyItems.JELLY_BEANS)
          ))));
        addDrop(MyBlocks.BASQUE_CAKE, LootTable.builder().pool(new LootPool.Builder()
          .rolls(ConstantLootNumberProvider.create(1))
          .with(ItemEntry.builder(MyItems.BASQUE_CAKE)
            .conditionally(BlockStatePropertyLootCondition.builder(MyBlocks.BASQUE_CAKE).properties(StatePredicate.Builder.create().exactMatch(Properties.AGE_3, 0))))));
        addDrop(MyBlocks.SWEET_BERRIES_CAKE_S, LootTable.builder().pool(new LootPool.Builder()
          .rolls(ConstantLootNumberProvider.create(1))
          .with(AlternativeEntry.builder(
            ItemEntry.builder(MyItems.SWEET_BERRIES_CAKE_S)
              .conditionally(BlockStatePropertyLootCondition.builder(MyBlocks.SWEET_BERRIES_CAKE_S).properties(StatePredicate.Builder.create().exactMatch(Properties.AGE_3, 0))),
            ItemEntry.builder(MyItems.SWEET_BERRIES_CAKE)
              .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(3)))
          ))));
        {
            List<LeafEntry.Builder<?>> list = new ArrayList<>();
            for (int i = 0; i <= Properties.AGE_3_MAX; i++) {
                LeafEntry.Builder<?> apply = ItemEntry.builder(MyItems.SWEET_BERRIES_CAKE)
                  .conditionally(BlockStatePropertyLootCondition.builder(MyBlocks.SWEET_BERRIES_CAKE).properties(StatePredicate.Builder.create().exactMatch(Properties.AGE_3, i)))
                  .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(i + 1)));
                list.add(apply);
            }
            addDrop(MyBlocks.SWEET_BERRIES_CAKE, LootTable.builder().pool(new LootPool.Builder()
              .rolls(ConstantLootNumberProvider.create(1))
              .with(AlternativeEntry.builder(list.toArray(new LootPoolEntry.Builder[0])))));
        }
    }
}
