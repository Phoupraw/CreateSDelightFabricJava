package phoupraw.mcmod.createsdelight.mixin;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.impl.transfer.context.PlayerContainerItemContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import phoupraw.mcmod.createsdelight.api.GetWorld;
@Mixin(PlayerContainerItemContext.class)
public class MixinPlayerContainerItemContext implements GetWorld {
    private World world;

    @Inject(method = "<init>(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)V", at = @At("RETURN"))
    private void setWorldOnInit(PlayerEntity player, Hand hand, CallbackInfo ci) {
        setWorld(player.getWorld());
    }

    @Inject(method = "<init>(Lnet/minecraft/entity/player/PlayerEntity;Lnet/fabricmc/fabric/api/transfer/v1/storage/base/SingleSlotStorage;)V", at = @At("RETURN"))
    private void setWorldOnInit(PlayerEntity player, SingleSlotStorage<ItemVariant> slot, CallbackInfo ci) {
        setWorld(player.getWorld());
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public World getWorld() {
        return world;
    }
}
