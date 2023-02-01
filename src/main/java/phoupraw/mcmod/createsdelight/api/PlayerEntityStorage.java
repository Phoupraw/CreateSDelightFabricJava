package phoupraw.mcmod.createsdelight.api;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.PlayerInventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
public class PlayerEntityStorage implements LivingEntityStorage {
    private final PlayerEntity player;
    private final PlayerInventoryStorage delegate;
    private final Map<EquipmentSlot,SingleSlotStorage<ItemVariant>> slots = new EnumMap<>(EquipmentSlot.class);

    public PlayerEntityStorage(PlayerEntity player) {
        this.player = player;
        delegate = PlayerInventoryStorage.of(this.player);
        slots.put(EquipmentSlot.MAINHAND, getDelegate().getHandSlot(Hand.MAIN_HAND));
        slots.put(EquipmentSlot.OFFHAND, getDelegate().getHandSlot(Hand.OFF_HAND));
        slots.put(EquipmentSlot.FEET, getDelegate().getSlot(100));
        slots.put(EquipmentSlot.LEGS, getDelegate().getSlot(101));
        slots.put(EquipmentSlot.CHEST, getDelegate().getSlot(102));
        slots.put(EquipmentSlot.HEAD, getDelegate().getSlot(103));
    }

    @Override
    public SingleSlotStorage<ItemVariant> get(@NotNull EquipmentSlot slot) {
        return slots.get(slot);
    }

    @Override
    public Map<EquipmentSlot, SingleSlotStorage<ItemVariant>> getSlots() {
        return slots;
    }

    @Override
    public PlayerEntity getLiving() {
        return player;
    }

    @Override
    public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        return getDelegate().insert(resource, maxAmount, transaction);
    }

    @Override
    public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
        return getDelegate().extract(resource, maxAmount, transaction);
    }

    @Override
    public Iterator<StorageView<ItemVariant>> iterator() {
        return getDelegate().iterator();
    }

    public PlayerInventoryStorage getDelegate() {
        return delegate;
    }
}
