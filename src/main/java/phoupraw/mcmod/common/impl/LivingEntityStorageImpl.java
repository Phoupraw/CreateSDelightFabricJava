package phoupraw.mcmod.common.impl;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.common.api.LivingEntityStorage;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
public class LivingEntityStorageImpl implements LivingEntityStorage {
    private final Map<EquipmentSlot, SingleSlotStorage<ItemVariant>> slots = new EnumMap<>(EquipmentSlot.class);
    private final CombinedStorage<ItemVariant, SingleSlotStorage<ItemVariant>> delegate = new CombinedStorage<>(new ArrayList<>(EquipmentSlot.values().length));
    private final LivingEntity living;

    public LivingEntityStorageImpl(LivingEntity living) {
        this.living = living;
        for (EquipmentSlot value : EquipmentSlot.values()) {
            Slot slot = new Slot(value);
            getSlots().put(value, slot);
            getDelegate().parts.add(slot);
        }
    }

    @Override
    public SingleSlotStorage<ItemVariant> get(@NotNull EquipmentSlot slot) {
        return getSlots().get(slot);
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

    @Override
    public Map<EquipmentSlot, SingleSlotStorage<ItemVariant>> getSlots() {
        return slots;
    }

    public CombinedStorage<ItemVariant, SingleSlotStorage<ItemVariant>> getDelegate() {
        return delegate;
    }

    @Override
    public LivingEntity getLiving() {
        return living;
    }

    public class Slot extends SingleStackStorage {
        private final EquipmentSlot slot;

        public Slot(EquipmentSlot slot) {this.slot = slot;}

        @Override
        protected ItemStack getStack() {
            return getLiving().getEquippedStack(slot);
        }

        @Override
        protected void setStack(ItemStack stack) {
            getLiving().equipStack(slot, stack);
        }
    }
}
