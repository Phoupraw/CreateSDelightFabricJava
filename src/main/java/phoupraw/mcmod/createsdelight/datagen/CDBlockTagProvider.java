package phoupraw.mcmod.createsdelight.datagen;

import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import com.simibubi.create.AllBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import phoupraw.mcmod.createsdelight.registry.CDBlocks;

public final class CDBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public CDBlockTagProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateTags() {
        getOrCreateTagBuilder(TagsRegistry.HEAT_SOURCES).add(AllBlocks.LIT_BLAZE_BURNER.get());
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(CDBlocks.CAKE_OVEN);
        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(new Block[]{});
    }
}
