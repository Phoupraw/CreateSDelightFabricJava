package phoupraw.mcmod.createsdelight.datagen.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.Item;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;
import phoupraw.mcmod.createsdelight.registry.CSDItems;

public final class CSDModelProvider extends FabricModelProvider {

    public CSDModelProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        for (Block block : new Block[]{CSDBlocks.PRINTED_CAKE, CSDBlocks.CAKE_OVEN}) {
            generator.registerSimpleState(block);//该方法会生成方块状态、物品模型。
        }
        for (Block block : new Block[]{CSDBlocks.CHOCOLATE_BLOCK}) {
            generator.registerSimpleCubeAll(block);//该方法会生成方块状态、方块模型、物品模型。
        }
        for (Block block : new Block[]{CSDBlocks.PRINTED_CAKE}) {
            generator.excludeFromSimpleItemModelGeneration(block);//设置不需要生成物品模型的方块
        }
        for (Item item : new Item[]{CSDItems.EGG_SHELL, CSDItems.BUCKETED_EGG_LIQUID}) {
            generator.registerItemModel(item);//生成平面物品模型
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
//generator.register(MyFluids.SUNFLOWER_OIL.getBucketItem(), );
    }
}
