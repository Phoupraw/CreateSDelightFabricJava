package phoupraw.mcmod.createsdelight.datagen;

import com.simibubi.create.AllBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import phoupraw.mcmod.common.api.DataGenerations;
import phoupraw.mcmod.createsdelight.block.CopperTunnelBlock;
import phoupraw.mcmod.createsdelight.registry.CDBlocks;
import phoupraw.mcmod.createsdelight.registry.CDItems;

import static net.minecraft.data.client.VariantSettings.MODEL;
import static net.minecraft.data.client.VariantSettings.Y;
@Environment(EnvType.CLIENT)
public final class MyModelProvider extends FabricModelProvider {

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

    public MyModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    /**
     {@link BlockStateModelGenerator#registerSimpleState(Block)}会自动生成物品模型。
     */
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        DataGenerations.registerSimpleState(generator, CDBlocks.PAN, CDBlocks.GRILL, CDBlocks.SPRINKLER, CDBlocks.BAMBOO_STEAMER, CDBlocks.MULTIFUNC_BASIN, CDBlocks.BASIN, CDBlocks.SKEWER_PLATE, CDBlocks.JELLY_BEANS, CDBlocks.BROWNIE, CDBlocks.OVEN, CDBlocks.SMALL_CHOCOLATE_CREAM_CAKE);
        horizontalAxis(generator, CDBlocks.VERTICAL_CUTTER, ModelIds.getBlockSubModelId(AllBlocks.MECHANICAL_PRESS.get(), "/block"));
        horizontalAxis(generator, CDBlocks.PRESSURE_COOKER, ModelIds.getBlockModelId(CDBlocks.PRESSURE_COOKER));
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
        generator.registerAxisRotated(CDBlocks.IRON_BAR_SKEWER, ModelIds.getBlockModelId(CDBlocks.IRON_BAR_SKEWER));
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(CDBlocks.MINCER, ModelIds.getBlockSubModelId(AllBlocks.MECHANICAL_MIXER.get(), "/block")));
        generator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(CDBlocks.SKEWER, ModelIds.getBlockModelId(CDBlocks.SKEWER)));
        generator.blockStateCollector.accept(MultipartBlockStateSupplier.create(CDBlocks.SMART_DRAIN)
          .with(BlockStateVariant.create()
            .put(MODEL, ModelIds.getBlockModelId(CDBlocks.SMART_DRAIN)))
          .with(When.create().set(Properties.LIT, true), BlockStateVariant.create()
            .put(MODEL, ModelIds.getBlockSubModelId(CDBlocks.SMART_DRAIN, "_fire"))));
        {
            var sup = MultipartBlockStateSupplier.create(CDBlocks.COPPER_TUNNEL)
              .with(BlockStateVariant.create()
                .put(MODEL, ModelIds.getBlockSubModelId(CDBlocks.COPPER_TUNNEL, "_frame")));
            for (Direction side : Direction.Type.HORIZONTAL) {
                EnumProperty<CopperTunnelBlock.Model> property = CopperTunnelBlock.Model.HORIZONTALS.get(side);
                VariantSettings.Rotation rotation = VariantSettings.Rotation.values()[side.getHorizontal()];
                sup.with(When.create().set(property, CopperTunnelBlock.Model.GLASS), BlockStateVariant.create()
                    .put(MODEL, ModelIds.getBlockSubModelId(CDBlocks.COPPER_TUNNEL, "_glass"))
                    .put(VariantSettings.Y, rotation))
                  .with(When.create().set(property, CopperTunnelBlock.Model.COPPER), BlockStateVariant.create()
                    .put(MODEL, ModelIds.getBlockSubModelId(CDBlocks.COPPER_TUNNEL, "_copper"))
                    .put(VariantSettings.Y, rotation));
            }
            generator.blockStateCollector.accept(sup);
        }
        DataGenerations.excludeFromSimpleItemModelGeneration(generator, CDBlocks.COPPER_TUNNEL, CDBlocks.VERTICAL_CUTTER, CDBlocks.PRESSURE_COOKER, CDBlocks.MINCER, CDBlocks.JELLY_BEANS, CDBlocks.JELLY_BEANS_CAKE, CDBlocks.SWEET_BERRIES_CAKE, CDBlocks.BASQUE_CAKE, CDBlocks.IRON_BAR_SKEWER);

        for (Item item : new Item[]{CDItems.BUCKETED_SUNFLOWER_OIL, CDItems.BOTTLED_SUNFLOWER_OIL, CDItems.PAN_FRIED_BEEF_PATTY, CDItems.THICK_PORK_SLICE, CDItems.PAN_FRIED_PORK_SLICE, CDItems.THIN_PORK_SLICE, CDItems.GRILLED_PORK_SLICE, CDItems.SUGAR_PORK, CDItems.LEAVES_RICE, CDItems.VANILLA, CDItems.VANILLA_SWEET_ROLL, CDItems.STEAMED_BUNS, CDItems.COOKED_RICE, CDItems.VEGETABLE_BIG_STEW, CDItems.ROSE_MILK_TEA, CDItems.CORAL_COLORFULS, CDItems.POPPY_RUSSIAN_SOUP, CDItems.EGG_SHELL, CDItems.EGG_DOUGH, CDItems.CRUSHED_ICE, CDItems.WHEAT_BLACK_TEA, CDItems.ICED_MELON_JUICE, CDItems.THICK_HOT_COCOA, CDItems.SALT, CDItems.KELP_ASH, CDItems.JELLY_BEANS,
          CDItems.YEAST, CDItems.CAKE_BASE, CDItems.CAKE_BASE_SLICE, CDItems.SUNFLOWER_KERNELS, CDItems.BUCKETED_PUMPKIN_OIL, CDItems.JELLY_BEANS_CAKE, CDItems.SWEET_BERRIES_CAKE, CDItems.BASQUE_CAKE, CDItems.MASHED_POTATO, CDItems.CHOCOLATE_CAKE_BASE, CDItems.CAKE_BLUEPRINT}) {
            generator.registerItemModel(item);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
//generator.register(MyFluids.SUNFLOWER_OIL.getBucketItem(), );
    }
}
