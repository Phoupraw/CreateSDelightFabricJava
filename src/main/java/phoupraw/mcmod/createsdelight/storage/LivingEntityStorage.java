package phoupraw.mcmod.createsdelight.storage;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
public interface LivingEntityStorage extends Storage<ItemVariant> {
    @Contract("_ -> new")
    static @NotNull LivingEntityStorage of(LivingEntity living) {
        return living instanceof PlayerEntity player ? new PlayerEntityStorage(player) : new LivingEntityStorageImpl(living);
    }
    SingleSlotStorage<ItemVariant> get(@NotNull EquipmentSlot slot);

    Map<EquipmentSlot, SingleSlotStorage<ItemVariant>> getSlots();
    LivingEntity getLiving();
    default SingleSlotStorage<ItemVariant> get(@NotNull Hand hand) {
        return get(hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
    }
}
