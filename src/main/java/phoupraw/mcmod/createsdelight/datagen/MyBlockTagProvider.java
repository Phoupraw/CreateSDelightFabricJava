package phoupraw.mcmod.createsdelight.datagen;

import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import com.simibubi.create.AllBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.tag.BlockTags;
import phoupraw.mcmod.createsdelight.registry.CDBlocks;

public final class MyBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public MyBlockTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(TagsRegistry.HEAT_SOURCES).add(AllBlocks.LIT_BLAZE_BURNER.get());
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(CDBlocks.PAN, CDBlocks.GRILL, CDBlocks.SPRINKLER, CDBlocks.SMART_DRAIN, CDBlocks.COPPER_TUNNEL, CDBlocks.MULTIFUNC_BASIN, CDBlocks.VERTICAL_CUTTER, CDBlocks.PRESSURE_COOKER, CDBlocks.MINCER, CDBlocks.SKEWER, CDBlocks.BASIN, CDBlocks.SKEWER_PLATE, CDBlocks.OVEN, CDBlocks.IRON_BAR_SKEWER);
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(CDBlocks.SPRINKLER, CDBlocks.BAMBOO_STEAMER, CDBlocks.VERTICAL_CUTTER, CDBlocks.PRESSURE_COOKER, CDBlocks.MINCER, CDBlocks.SKEWER_PLATE);
    }
}
