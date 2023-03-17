package phoupraw.mcmod.createsdelight.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.loot.LootManager;
import org.spongepowered.asm.mixin.Mixin;
import phoupraw.mcmod.createsdelight.inject.ImplementMinecraftClient;
@Mixin(MinecraftClient.class)
@Environment(EnvType.CLIENT)
public abstract class MixinMinecraftClient implements ImplementMinecraftClient {
    private LootManager lootManager;

    @Override
    public LootManager getLootManager() {
        return lootManager;
    }

    @Override
    public void setLootManager(LootManager lootManager) {
        this.lootManager = lootManager;
    }
}
