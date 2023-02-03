package phoupraw.mcmod.createsdelight.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import phoupraw.mcmod.createsdelight.registry.MyBlocks;
public class MyBlockLootTableProvider extends FabricBlockLootTableProvider {
    public MyBlockLootTableProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        addDrop(MyBlocks.PAN);
        addDrop(MyBlocks.GRILL);
        addDrop(MyBlocks.SPRINKLER);
    }
}
