package phoupraw.mcmod.createsdelight.datagen.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.RailShape;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.model.*;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.createsdelight.block.VoxelMakerBlock;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;
import phoupraw.mcmod.createsdelight.registry.CSDItems;

import java.util.ArrayList;
import java.util.Collection;

public final class CSDModelProvider extends FabricModelProvider {
    public CSDModelProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }
    @SuppressWarnings("RedundantOperationOnEmptyContainer")
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        VariantSettings.Rotation[] rotations = VariantSettings.Rotation.values();
        for (Block block : new Block[]{CSDBlocks.MADE_VOXEL}) {
            //生成单一最简方块状态、继承方块模型的物品模型。
            generator.registerSimpleState(block);
        }
        for (Block block : new Block[]{}) {
            //生成单一最简方块状态、六面相同完整方块方块模型、继承方块模型的物品模型。
            generator.registerSimpleCubeAll(block);
        }
        for (Block block : new Block[]{}) {
            //生成随机旋转模型的单一方块状态、六面相同完整方块方块模型、继承方块模型的物品模型。
            Identifier modelId = TexturedModel.CUBE_ALL.create(block, generator.modelCollector);
            Collection<BlockStateVariant> variants = new ArrayList<>();
            for (VariantSettings.Rotation x : rotations) {
                for (VariantSettings.Rotation y : rotations) {
                    variants.add(BlockStateVariant.create()
                      .put(VariantSettings.MODEL, modelId)
                      .put(VariantSettings.Y, x)
                      .put(VariantSettings.X, y));
                }
            }
            generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block, variants.toArray(BlockStateVariant[]::new)));
        }
        for (Block block : new Block[]{CSDBlocks.CHOCOLATE_BLOCK, CSDBlocks.CREAM, CSDBlocks.APPLE_JAM, CSDBlocks.WHEAT_PASTE, CSDBlocks.WHEAT_CAKE_BASE_BLOCK, CSDBlocks.BUTTER_BLOCK,}) {
            //生成绕竖轴随机旋转模型的单一方块状态、带釉陶瓦方块模型（即侧面旋转的方块模型）、继承方块模型的物品模型。
            generator.blockStateCollector.accept(BlockStateModelGenerator.createBlockStateWithRandomHorizontalRotations(block, TexturedModel.TEMPLATE_GLAZED_TERRACOTTA.create(block, generator.modelCollector)));
        }
        for (Block block : new Block[]{CSDBlocks.MADE_VOXEL,}) {
            //不给方块自动生成继承方块模型的物品模型。
            generator.excludeFromSimpleItemModelGeneration(block);
        }
        for (Item item : new Item[]{CSDItems.BUCKETED_EGG_LIQUID, CSDItems.BUCKETED_APPLE_JAM, CSDItems.BUCKETED_WHEAT_PASTE, CSDItems.BUCKETED_CREAM, CSDItems.EGG_SHELL, CSDItems.KELP_ASH, CSDItems.BUTTER_NUGGET, CSDItems.BUTTER_INGOT,}) {
            //生成用单一纹理最简平面物品模型。
            generator.registerItemModel(item);
        }
        for (Block block : new Block[]{CSDBlocks.VOXEL_MAKER,}) {
            Identifier modelId = ModelIds.getBlockModelId(Blocks.BROWN_GLAZED_TERRACOTTA);
            generator.registerParentedItemModel(block, modelId);
            generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
              .coordinate(BlockStateVariantMap.create(VoxelMakerBlock.FACING)
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
