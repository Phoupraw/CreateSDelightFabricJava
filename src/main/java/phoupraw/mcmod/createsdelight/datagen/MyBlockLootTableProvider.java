package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.BlockLootTableGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
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
import phoupraw.mcmod.common.api.DataGenerations;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
import phoupraw.mcmod.createsdelight.registry.MyItems;

import java.util.ArrayList;
import java.util.List;
public final class MyBlockLootTableProvider extends FabricBlockLootTableProvider {

    public static void addDropAge3(BlockLootTableGenerator generator, Block block, Item item, int count) {
        var alternative = AlternativeEntry.builder(
          ItemEntry.builder(block)
            .conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(Properties.AGE_3, 0))));
        if (item != Items.AIR) {
            var itemEntry = ItemEntry.builder(item);
            if (count != 1) {
                itemEntry.apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(count)));
            }
            alternative.alternatively(itemEntry);
        }
        generator.addDrop(block, LootTable.builder().pool(new LootPool.Builder()
          .rolls(ConstantLootNumberProvider.create(1))
          .with(alternative)));
    }

    public MyBlockLootTableProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        DataGenerations.addDrop(this, MyBlocks.PAN, MyBlocks.GRILL, MyBlocks.SPRINKLER, MyBlocks.BAMBOO_STEAMER, MyBlocks.SMART_DRAIN, MyBlocks.COPPER_TUNNEL, MyBlocks.MULTIFUNC_BASIN, MyBlocks.VERTICAL_CUTTER, MyBlocks.PRESSURE_COOKER, MyBlocks.MINCER, MyBlocks.SKEWER, MyBlocks.BASIN, MyBlocks.SKEWER_PLATE, MyBlocks.JELLY_BEANS, MyBlocks.BROWNIE, MyBlocks.OVEN);
        addDropAge3(this, MyBlocks.JELLY_BEANS_CAKE, MyItems.JELLY_BEANS, 1);
        addDropAge3(this, MyBlocks.BASQUE_CAKE, Items.AIR, 0);
        addDropAge3(this, MyBlocks.SWEET_BERRIES_CAKE_S, MyItems.SWEET_BERRIES_CAKE, 3);
        addDropAge3(this, MyBlocks.APPLE_CREAM_CAKE, Items.APPLE, 1);
        addDropAge3(this, MyBlocks.APPLE_CAKE, Items.APPLE, 1);
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
