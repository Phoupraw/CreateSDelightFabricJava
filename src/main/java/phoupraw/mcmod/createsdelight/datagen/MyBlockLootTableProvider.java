package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.state.property.Properties;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
import phoupraw.mcmod.createsdelight.registry.MyItems;
public class MyBlockLootTableProvider extends FabricBlockLootTableProvider {
    public MyBlockLootTableProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        addDrop(MyBlocks.PAN);
        addDrop(MyBlocks.GRILL);
        addDrop(MyBlocks.SPRINKLER);
        addDrop(MyBlocks.BAMBOO_STEAMER);
        addDrop(MyBlocks.SMART_DRAIN);
        addDrop(MyBlocks.COPPER_TUNNEL);
        addDrop(MyBlocks.MULTIFUNC_BASIN);
        addDrop(MyBlocks.VERTICAL_CUTTER);
        addDrop(MyBlocks.PRESSURE_COOKER);
        addDrop(MyBlocks.MINCER);
        addDrop(MyBlocks.SKEWER);
        addDrop(MyBlocks.BASIN);
        addDrop(MyBlocks.SKEWER_PLATE);
        addDrop(MyBlocks.JELLY_BEANS);
        addDrop(MyBlocks.JELLY_BEANS_CAKE, LootTable.builder().pool(new LootPool.Builder()
          .rolls(ConstantLootNumberProvider.create(1))
          .with(AlternativeEntry.builder(
            ItemEntry.builder(MyItems.JELLY_BEANS_CAKE).conditionally(BlockStatePropertyLootCondition.builder(MyBlocks.JELLY_BEANS_CAKE).properties(StatePredicate.Builder.create().exactMatch(Properties.AGE_3, 0))),
            ItemEntry.builder(MyItems.JELLY_BEANS)
          ))));

    }
}
