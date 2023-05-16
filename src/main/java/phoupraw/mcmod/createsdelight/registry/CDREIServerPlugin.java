package phoupraw.mcmod.createsdelight.registry;

import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import net.minecraft.block.Block;
import net.minecraft.loot.LootManager;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.ApiStatus;
import phoupraw.mcmod.createsdelight.rei.LootTableCategory;
import phoupraw.mcmod.createsdelight.rei.LootTableDisplay;
@ApiStatus.Internal
public final class CDREIServerPlugin implements REIServerPlugin {
    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        registry.register(LootTableCategory.ID, new DisplaySerializer<>() {
            @Override
            public NbtCompound save(NbtCompound tag, LootTableDisplay display) {
                if (display.owner instanceof Block block) {
                    NbtCompound ownerNbt = new NbtCompound();
                    ownerNbt.putString("type", "block");
                    ownerNbt.putString("id", Registry.BLOCK.getId(block).toString());
                    tag.put("owner", ownerNbt);
                }
                tag.putString("lootTableJson", LootManager.toJson(display.lootTable).toString());
                return tag;
            }

            @Override
            public LootTableDisplay read(NbtCompound tag) {
                return LootTableDisplay.of(tag);
            }
        });
    }

}
