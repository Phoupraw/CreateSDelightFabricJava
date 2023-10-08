package phoupraw.mcmod.createsdelight.mixin.client;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(MinecraftClient.class)
class MMinecraftClient {
    static {
        //MJsonUnbakedModel.LOCAL_setParents_unbakedModel.get();
    }
}
