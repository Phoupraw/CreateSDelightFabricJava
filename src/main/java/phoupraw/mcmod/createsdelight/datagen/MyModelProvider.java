package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.item.Item;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
import phoupraw.mcmod.createsdelight.registry.MyFluids;
import phoupraw.mcmod.createsdelight.registry.MyItems;
public class MyModelProvider extends FabricModelProvider {
    public MyModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    /**
     * {@link BlockStateModelGenerator#registerSimpleState(Block)}会自动生成物品模型。
     */
    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generator.registerSimpleState(MyBlocks.PAN);
        generator.registerSimpleState(MyBlocks.GRILL);
        generator.registerSimpleState(MyBlocks.SPRINKLER);
        generator.registerSimpleState(MyBlocks.BAMBOO_STEAMER);
//        generator.excludeFromSimpleItemModelGeneration();

        for (Item item : new Item[]{MyFluids.SUNFLOWER_OIL.getBucketItem(), MyFluids.SUNFLOWER_OIL.getBottle(), MyItems.PAN_FRIED_BEEF_PATTY, MyItems.THICK_PORK_SLICE, MyItems.PAN_FRIED_PORK_SLICE, MyItems.THIN_PORK_SLICE, MyItems.GRILLED_PORK_SLICE, MyItems.SUGAR_PORK,MyItems.LEAVES_RICE,MyItems.VANILLA,MyItems.VANILLA_SWEET_ROLL,MyItems.STEAMED_BUNS}) {
            generator.registerItemModel(item);
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
//generator.register(MyFluids.SUNFLOWER_OIL.getBucketItem(), );
    }
}
