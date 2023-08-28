package phoupraw.mcmod.createsdelight.registry;

import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import org.jetbrains.annotations.ApiStatus;
@ApiStatus.Internal
public final class CDREIServerPlugin implements REIServerPlugin {
    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        //registry.register(LootTableCategory.ID, new DisplaySerializer<>() {
        //    @Override
        //    public NbtCompound save(NbtCompound tag, LootTableDisplay display) {
        //        if (display.owner instanceof Block block) {
        //            NbtCompound ownerNbt = new NbtCompound();
        //            ownerNbt.putString("type", "block");
        //            ownerNbt.putString("id", Registries.BLOCK.getId(block).toString());
        //            tag.put("owner", ownerNbt);
        //        }
        //        //tag.putString("lootTableJson", LootManager.toJson(display.lootTable).toString());
        //        return tag;
        //    }
        //
        //    @Override
        //    public LootTableDisplay read(NbtCompound tag) {
        //        return LootTableDisplay.of(tag);
        //    }
        //});
    }

}
