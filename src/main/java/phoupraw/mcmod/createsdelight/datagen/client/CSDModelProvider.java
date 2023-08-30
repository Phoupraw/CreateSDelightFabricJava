package phoupraw.mcmod.createsdelight.datagen.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.Item;
import phoupraw.mcmod.createsdelight.registry.CSDBlocks;
import phoupraw.mcmod.createsdelight.registry.CSDItems;

@Environment(EnvType.CLIENT)
public final class CSDModelProvider extends FabricModelProvider {
    //public static void horizontalAxis(BlockStateModelGenerator generator, Block block, Identifier modelId) {
    //    generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
    //      .coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_AXIS)
    //        .register(Direction.Axis.Z, BlockStateVariant.create()
    //          .put(MODEL, modelId))
    //        .register(Direction.Axis.X, BlockStateVariant.create()
    //          .put(MODEL, modelId)
    //          .put(Y, VariantSettings.Rotation.R90))));
    //}
    //
    //public static void intProperty(BlockStateModelGenerator generator, Block block, IntProperty property) {
    //    int max = property.getValues().size() - 1;
    //    var map = BlockStateVariantMap.create(property)
    //      .register(0, BlockStateVariant.create()
    //        .put(MODEL, ModelIds.getBlockModelId(block)));
    //    for (int i = 1; i <= max; i++) {
    //        map.register(i, BlockStateVariant.create()
    //          .put(MODEL, ModelIds.getBlockSubModelId(block, "_" + i)));
    //    }
    //    generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(map));
    //}

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
        //intProperty(generator, CDBlocks.JELLY_BEANS_CAKE, Properties.AGE_3);
        //intProperty(generator, CDBlocks.SWEET_BERRIES_CAKE, Properties.AGE_3);
        //intProperty(generator, CDBlocks.BASQUE_CAKE, Properties.AGE_3);
        //intProperty(generator, CDBlocks.SWEET_BERRIES_CAKE_S, Properties.AGE_3);
        //intProperty(generator, CDBlocks.APPLE_CREAM_CAKE, Properties.AGE_3);
        //intProperty(generator, CDBlocks.APPLE_CAKE, Properties.AGE_3);
        //intProperty(generator, CDBlocks.CARROT_CREAM_CAKE, Properties.AGE_3);
        //intProperty(generator, CDBlocks.MEDIUM_CHOCOLATE_CREAM_CAKE, Properties.AGE_3);
        //intProperty(generator, CDBlocks.BIG_CHOCOLATE_CREAM_CAKE, Properties.AGE_3);
        //intProperty(generator, CDBlocks.CHOCOLATE_ANTHEMY_CAKE, Properties.AGE_3);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
//generator.register(MyFluids.SUNFLOWER_OIL.getBucketItem(), );
    }
}
