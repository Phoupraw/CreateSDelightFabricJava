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
import phoupraw.mcmod.createsdelight.block.CopperTunnelBlock;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
import phoupraw.mcmod.createsdelight.registry.MyItems;

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

    public static void excludeFromSimpleItemModelGeneration(BlockStateModelGenerator generator, Block... blocks) {
        for (Block block : blocks) generator.excludeFromSimpleItemModelGeneration(block);
    }

    public static void registerSimpleState(BlockStateModelGenerator generator, Block... blocks) {
        for (Block block : blocks) generator.registerSimpleState(block);
    }

    public MyModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    /**
     {@link BlockStateModelGenerator#registerSimpleState(Block)}会自动生成物品模型。
     */
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        registerSimpleState(generator, MyBlocks.PAN, MyBlocks.GRILL, MyBlocks.SPRINKLER, MyBlocks.BAMBOO_STEAMER, MyBlocks.MULTIFUNC_BASIN, MyBlocks.BASIN, MyBlocks.SKEWER_PLATE, MyBlocks.JELLY_BEANS, MyBlocks.BROWNIE);
        horizontalAxis(generator, MyBlocks.VERTICAL_CUTTER, ModelIds.getBlockSubModelId(AllBlocks.MECHANICAL_PRESS.get(), "/block"));
        horizontalAxis(generator, MyBlocks.PRESSURE_COOKER, ModelIds.getBlockModelId(MyBlocks.PRESSURE_COOKER));
        intProperty(generator, MyBlocks.JELLY_BEANS_CAKE, Properties.AGE_3);
        intProperty(generator, MyBlocks.SWEET_BERRIES_CAKE, Properties.AGE_3);
        intProperty(generator, MyBlocks.BASQUE_CAKE, Properties.AGE_3);
        intProperty(generator, MyBlocks.SWEET_BERRIES_CAKE_S, Properties.AGE_3);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(MyBlocks.MINCER, ModelIds.getBlockSubModelId(AllBlocks.MECHANICAL_MIXER.get(), "/block")));
        generator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(MyBlocks.SKEWER, ModelIds.getBlockModelId(MyBlocks.SKEWER)));
        generator.blockStateCollector.accept(MultipartBlockStateSupplier.create(MyBlocks.SMART_DRAIN)
          .with(BlockStateVariant.create()
            .put(MODEL, ModelIds.getBlockModelId(MyBlocks.SMART_DRAIN)))
          .with(When.create().set(Properties.LIT, true), BlockStateVariant.create()
            .put(MODEL, ModelIds.getBlockSubModelId(MyBlocks.SMART_DRAIN, "_fire"))));
        {
            var sup = MultipartBlockStateSupplier.create(MyBlocks.COPPER_TUNNEL)
              .with(BlockStateVariant.create()
                .put(MODEL, ModelIds.getBlockSubModelId(MyBlocks.COPPER_TUNNEL, "_frame")));
            for (Direction side : Direction.Type.HORIZONTAL) {
                EnumProperty<CopperTunnelBlock.Model> property = CopperTunnelBlock.Model.HORIZONTALS.get(side);
                VariantSettings.Rotation rotation = VariantSettings.Rotation.values()[side.getHorizontal()];
                sup.with(When.create().set(property, CopperTunnelBlock.Model.GLASS), BlockStateVariant.create()
                    .put(MODEL, ModelIds.getBlockSubModelId(MyBlocks.COPPER_TUNNEL, "_glass"))
                    .put(VariantSettings.Y, rotation))
                  .with(When.create().set(property, CopperTunnelBlock.Model.COPPER), BlockStateVariant.create()
                    .put(MODEL, ModelIds.getBlockSubModelId(MyBlocks.COPPER_TUNNEL, "_copper"))
                    .put(VariantSettings.Y, rotation));
            }
            generator.blockStateCollector.accept(sup);
        }
        excludeFromSimpleItemModelGeneration(generator, MyBlocks.COPPER_TUNNEL, MyBlocks.VERTICAL_CUTTER, MyBlocks.PRESSURE_COOKER, MyBlocks.MINCER, MyBlocks.JELLY_BEANS, MyBlocks.JELLY_BEANS_CAKE);

        for (Item item : new Item[]{MyItems.BUCKETED_SUNFLOWER_OIL, MyItems.BOTTLED_SUNFLOWER_OIL, MyItems.PAN_FRIED_BEEF_PATTY, MyItems.THICK_PORK_SLICE, MyItems.PAN_FRIED_PORK_SLICE, MyItems.THIN_PORK_SLICE, MyItems.GRILLED_PORK_SLICE, MyItems.SUGAR_PORK, MyItems.LEAVES_RICE, MyItems.VANILLA, MyItems.VANILLA_SWEET_ROLL, MyItems.STEAMED_BUNS, MyItems.COOKED_RICE, MyItems.VEGETABLE_BIG_STEW, MyItems.ROSE_MILK_TEA, MyItems.CORAL_COLORFULS, MyItems.POPPY_RUSSIAN_SOUP, MyItems.EGG_SHELL, MyItems.EGG_DOUGH, MyItems.CRUSHED_ICE, MyItems.WHEAT_BLACK_TEA, MyItems.ICED_MELON_JUICE, MyItems.THICK_HOT_COCOA, MyItems.SALT, MyItems.KELP_ASH, MyItems.JELLY_BEANS, MyItems.JELLY_BEANS_CAKE, MyItems.YEAST, MyItems.CAKE_BASE, MyItems.CAKE_BASE_SLICE}) {
            generator.registerItemModel(item);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
//generator.register(MyFluids.SUNFLOWER_OIL.getBucketItem(), );
    }
}
