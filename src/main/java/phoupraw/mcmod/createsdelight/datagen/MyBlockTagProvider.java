package phoupraw.mcmod.createsdelight.datagen;

import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import com.simibubi.create.AllBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tag.BlockTags;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
public class MyBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public MyBlockTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(TagsRegistry.HEAT_SOURCES)
          .add(AllBlocks.LIT_BLAZE_BURNER.get());
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
          .add(MyBlocks.PAN, MyBlocks.GRILL, MyBlocks.SPRINKLER, MyBlocks.SMART_DRAIN, MyBlocks.COPPER_TUNNEL, MyBlocks.MULTIFUNC_BASIN);
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
          .add(MyBlocks.SPRINKLER, MyBlocks.BAMBOO_STEAMER);
    }
}
