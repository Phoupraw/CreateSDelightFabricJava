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
import phoupraw.mcmod.createsdelight.registry.CDBlocks;
import phoupraw.mcmod.createsdelight.registry.CDItems;

import java.util.ArrayList;
import java.util.List;
public final class MyBlockLootTableProvider extends FabricBlockLootTableProvider {

    public static void addDropAge3(BlockLootTableGenerator generator, Block block, Item item, int count) {
        var alternative = AlternativeEntry.builder(
          ItemEntry.builder(block)
            .conditionally(BlockStatePropertyLootCondition.builder(block).properties(StatePredicate.Builder.create().exactMatch(Properties.AGE_3, 0))));
        if (item != Items.AIR && count != 0) {
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
        DataGenerations.addDrop(this, CDBlocks.PAN, CDBlocks.GRILL, CDBlocks.SPRINKLER, CDBlocks.BAMBOO_STEAMER, CDBlocks.SMART_DRAIN, CDBlocks.COPPER_TUNNEL, CDBlocks.MULTIFUNC_BASIN, CDBlocks.VERTICAL_CUTTER, CDBlocks.PRESSURE_COOKER, CDBlocks.MINCER, CDBlocks.SKEWER, CDBlocks.BASIN, CDBlocks.SKEWER_PLATE, CDBlocks.JELLY_BEANS, CDBlocks.BROWNIE, CDBlocks.OVEN, CDBlocks.SMALL_CHOCOLATE_CREAM_CAKE);
        addDropAge3(this, CDBlocks.JELLY_BEANS_CAKE, CDItems.JELLY_BEANS, 1);
        addDropAge3(this, CDBlocks.BASQUE_CAKE, Items.AIR, 0);
        addDropAge3(this, CDBlocks.SWEET_BERRIES_CAKE_S, CDItems.SWEET_BERRIES_CAKE, 3);
        addDropAge3(this, CDBlocks.APPLE_CREAM_CAKE, Items.APPLE, 1);
        addDropAge3(this, CDBlocks.APPLE_CAKE, Items.APPLE, 1);
        addDropAge3(this, CDBlocks.CARROT_CREAM_CAKE, Items.CARROT, 1);
        addDropAge3(this, CDBlocks.MEDIUM_CHOCOLATE_CREAM_CAKE, Items.AIR, 0);
        addDropAge3(this, CDBlocks.BIG_CHOCOLATE_CREAM_CAKE, Items.AIR, 0);
        addDropAge3(this, CDBlocks.CHOCOLATE_ANTHEMY_CAKE, Items.AIR, 0);
        addDrop(CDBlocks.IRON_BAR_SKEWER, Items.IRON_BARS);
        {
            List<LeafEntry.Builder<?>> list = new ArrayList<>();
            for (int i = 0; i <= Properties.AGE_3_MAX; i++) {
                LeafEntry.Builder<?> apply = ItemEntry.builder(CDItems.SWEET_BERRIES_CAKE)
                  .conditionally(BlockStatePropertyLootCondition.builder(CDBlocks.SWEET_BERRIES_CAKE).properties(StatePredicate.Builder.create().exactMatch(Properties.AGE_3, i)))
                  .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(i + 1)));
                list.add(apply);
            }
            addDrop(CDBlocks.SWEET_BERRIES_CAKE, LootTable.builder().pool(new LootPool.Builder()
              .rolls(ConstantLootNumberProvider.create(1))
              .with(AlternativeEntry.builder(list.toArray(new LootPoolEntry.Builder[0])))));
        }
    }
}
