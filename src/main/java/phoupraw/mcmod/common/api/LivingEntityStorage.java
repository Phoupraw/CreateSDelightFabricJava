package phoupraw.mcmod.common.api;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.common.impl.LivingEntityStorageImpl;

import java.util.Map;
/**
 把生物的盔甲槽位和双手包装成{@link Storage}
 @since 1.0.0 */
public interface LivingEntityStorage extends Storage<ItemVariant> {
    @Contract("_ -> new")
    static @NotNull LivingEntityStorage of(LivingEntity living) {
        return new LivingEntityStorageImpl(living);
    }
    SingleSlotStorage<ItemVariant> get(@NotNull EquipmentSlot slot);

    Map<EquipmentSlot, SingleSlotStorage<ItemVariant>> getSlots();
    LivingEntity getLiving();
    default SingleSlotStorage<ItemVariant> get(@NotNull Hand hand) {
        return get(hand == Hand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
    }
}
