package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import phoupraw.mcmod.createsdelight.registry.CDBlocks;
import phoupraw.mcmod.createsdelight.registry.CDItems;

import static net.minecraft.data.client.VariantSettings.MODEL;
import static net.minecraft.data.client.VariantSettings.Y;
@Environment(EnvType.CLIENT)
public final class CDModelProvider extends FabricModelProvider {
    public static void horizontalAxis(BlockStateModelGenerator generator, Block block, Identifier modelId) {
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
          .coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_AXIS)
            .register(Direction.Axis.Z, BlockStateVariant.create()
              .put(MODEL, modelId))
            .register(Direction.Axis.X, BlockStateVariant.create()
              .put(MODEL, modelId)
              .put(Y, VariantSettings.Rotation.R90))));
    }

    public static void intProperty(BlockStateModelGenerator generator, Block block, IntProperty property) {
        int max = property.getValues().size() - 1;
        var map = BlockStateVariantMap.create(property)
          .register(0, BlockStateVariant.create()
            .put(MODEL, ModelIds.getBlockModelId(block)));
        for (int i = 1; i <= max; i++) {
            map.register(i, BlockStateVariant.create()
              .put(MODEL, ModelIds.getBlockSubModelId(block, "_" + i)));
        }
        generator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block).coordinate(map));
    }

    public CDModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        for (Block block : new Block[]{ CDBlocks.JELLY_BEANS, CDBlocks.BROWNIE, CDBlocks.SMALL_CHOCOLATE_CREAM_CAKE, CDBlocks.PRINTED_CAKE,CDBlocks.CAKE_OVEN}) {
            generator.registerSimpleState(block);//该方法会生成方块状态、物品模型。
        }
        for (Block block : new Block[]{ CDBlocks.MILK,CDBlocks.CHOCOLATE}) {
            generator.registerSimpleCubeAll(block);//该方法会生成方块状态、方块模型、物品模型。
        }
        intProperty(generator, CDBlocks.JELLY_BEANS_CAKE, Properties.AGE_3);
        intProperty(generator, CDBlocks.SWEET_BERRIES_CAKE, Properties.AGE_3);
        intProperty(generator, CDBlocks.BASQUE_CAKE, Properties.AGE_3);
        intProperty(generator, CDBlocks.SWEET_BERRIES_CAKE_S, Properties.AGE_3);
        intProperty(generator, CDBlocks.APPLE_CREAM_CAKE, Properties.AGE_3);
        intProperty(generator, CDBlocks.APPLE_CAKE, Properties.AGE_3);
        intProperty(generator, CDBlocks.CARROT_CREAM_CAKE, Properties.AGE_3);
        intProperty(generator, CDBlocks.MEDIUM_CHOCOLATE_CREAM_CAKE, Properties.AGE_3);
        intProperty(generator, CDBlocks.BIG_CHOCOLATE_CREAM_CAKE, Properties.AGE_3);
        intProperty(generator, CDBlocks.CHOCOLATE_ANTHEMY_CAKE, Properties.AGE_3);
        for (Block block : new Block[]{ CDBlocks.JELLY_BEANS, CDBlocks.JELLY_BEANS_CAKE, CDBlocks.SWEET_BERRIES_CAKE, CDBlocks.BASQUE_CAKE,  CDBlocks.PRINTED_CAKE}) {
            generator.excludeFromSimpleItemModelGeneration(block);
        }
        for (Item item : new Item[]{CDItems.BUCKETED_SUNFLOWER_OIL, CDItems.BOTTLED_SUNFLOWER_OIL,   CDItems.EGG_SHELL, CDItems.EGG_DOUGH, CDItems.KELP_ASH, CDItems.JELLY_BEANS,CDItems.CAKE_BASE, CDItems.CAKE_BASE_SLICE, CDItems.SUNFLOWER_KERNELS, CDItems.BUCKETED_PUMPKIN_OIL, CDItems.JELLY_BEANS_CAKE, CDItems.SWEET_BERRIES_CAKE, CDItems.BASQUE_CAKE, CDItems.CHOCOLATE_CAKE_BASE, CDItems.CAKE_BLUEPRINT}) {
            generator.registerItemModel(item);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
//generator.register(MyFluids.SUNFLOWER_OIL.getBucketItem(), );
    }
}
