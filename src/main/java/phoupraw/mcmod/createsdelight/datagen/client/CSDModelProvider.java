package phoupraw.mcmod.createsdelight.datagen.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.RailShape;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.createsdelight.block.CakeOvenBlock;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;
import phoupraw.mcmod.createsdelight.registry.CSDItems;

public final class CSDModelProvider extends FabricModelProvider {

    public CSDModelProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        for (Block block : new Block[]{CSDBlocks.PRINTED_CAKE, CSDBlocks.MADE_VOXEL}) {
            generator.registerSimpleState(block);//该方法会生成方块状态、物品模型。
        }
        for (Block block : new Block[]{CSDBlocks.CHOCOLATE_BLOCK, CSDBlocks.CREAM_BLOCK}) {
            generator.registerSimpleCubeAll(block);//该方法会生成方块状态、方块模型、物品模型。
        }
        for (Block block : new Block[]{CSDBlocks.PRINTED_CAKE, CSDBlocks.MADE_VOXEL}) {
            generator.excludeFromSimpleItemModelGeneration(block);//设置在registerSimpleState和registerSimpleCubeAll中不需要生成物品模型的方块
        }
        for (Item item : new Item[]{CSDItems.BUCKETED_EGG_LIQUID, CSDItems.EGG_SHELL, CSDItems.KELP_ASH}) {
            generator.registerItemModel(item);//生成平面物品模型
        }
        for (Block block : new Block[]{CSDBlocks.VOXEL_MAKER, CSDBlocks.CAKE_OVEN}) {
            Identifier modelId = ModelIds.getBlockModelId(Blocks.BROWN_GLAZED_TERRACOTTA);
            generator.registerParentedItemModel(block, modelId);
            generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
              .coordinate(BlockStateVariantMap.create(CakeOvenBlock.FACING)
                .register(RailShape.SOUTH_EAST, BlockStateVariant.create()
                  .put(VariantSettings.MODEL, modelId)
                  .put(VariantSettings.Y, VariantSettings.Rotation.R0))
                .register(RailShape.SOUTH_WEST, BlockStateVariant.create()
                  .put(VariantSettings.MODEL, modelId)
                  .put(VariantSettings.Y, VariantSettings.Rotation.R90))
                .register(RailShape.NORTH_WEST, BlockStateVariant.create()
                  .put(VariantSettings.MODEL, modelId)
                  .put(VariantSettings.Y, VariantSettings.Rotation.R180))
                .register(RailShape.NORTH_EAST, BlockStateVariant.create()
                  .put(VariantSettings.MODEL, modelId)
                  .put(VariantSettings.Y, VariantSettings.Rotation.R270))));
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        //generator.register(MyFluids.SUNFLOWER_OIL.getBucketItem(), );
    }

}
