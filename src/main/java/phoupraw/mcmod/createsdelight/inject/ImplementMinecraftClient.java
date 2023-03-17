package phoupraw.mcmod.createsdelight.inject;

import net.minecraft.loot.LootManager;
public interface ImplementMinecraftClient {
    LootManager getLootManager();
    void setLootManager(LootManager lootManager);
}
