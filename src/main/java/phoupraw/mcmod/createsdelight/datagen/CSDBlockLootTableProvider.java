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
    public CSDBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        for (Block block : new Block[]{CSDBlocks.VOXEL_MAKER, CSDBlocks.CHOCOLATE_BLOCK, CSDBlocks.WHEAT_CAKE_BASE_BLOCK, CSDBlocks.BUTTER_BLOCK}) {
            addDrop(block);//方块无条件掉落自身
        }
        addDrop(CSDBlocks.MADE_VOXEL, LootTable.builder().pool(new LootPool.Builder()
          .rolls(ConstantLootNumberProvider.create(1))
          .with(ItemEntry.builder(CSDItems.MADE_VOXEL)
            .apply(CopyNameLootFunction.builder(CopyNameLootFunction.Source.BLOCK_ENTITY))//方块实体需实现Nameable
            .apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY)
              .withOperation("voxelRecord", "BlockEntityTag.voxelRecord")))));
    }

}
