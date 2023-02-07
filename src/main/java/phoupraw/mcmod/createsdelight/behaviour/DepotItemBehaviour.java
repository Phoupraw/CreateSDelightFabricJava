package phoupraw.mcmod.createsdelight.behaviour;

import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.DirectBeltInputBehaviour;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.ExtractionOnlyStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.InsertionOnlyStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.createsdelight.api.ReplaceableStorageView;
import phoupraw.mcmod.createsdelight.block.entity.renderer.SmartDrainRenderer;
import phoupraw.mcmod.createsdelight.storage.BlockingTransportedStorage;

import java.util.*;
public class DepotItemBehaviour extends TileEntityBehaviour implements DirectBeltInputBehaviour.InsertionCallback {
    public static final BehaviourType<DepotItemBehaviour> TYPE = new BehaviourType<>("less_depot");
    public TransportedItemStack main = TransportedItemStack.EMPTY;
    public List<TransportedItemStack> incomings = new ArrayList<>();
    public boolean allowMerge = true;
    public Map<Direction, InsertionStorage> insertions = new EnumMap<>(Direction.class);
    public Storage<ItemVariant> extraction = new ExtractionStorage();

    public DepotItemBehaviour(SmartTileEntity te) {
        super(te);
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    @Override
    public void write(NbtCompound nbt, boolean clientPacket) {
        super.write(nbt, clientPacket);
        if (!main.stack.isEmpty()) {
            nbt.put("main", main.serializeNBT());
        }
        if (!incomings.isEmpty()) {
            var list = new NbtList();
            for (TransportedItemStack incoming : incomings) {
                if (!incoming.stack.isEmpty()) {
                    list.add(incoming.serializeNBT());
                }
            }
            nbt.put("incomings", list);
        }
    }

    @Override
    public void read(NbtCompound nbt, boolean clientPacket) {
        super.read(nbt, clientPacket);
        main = TransportedItemStack.read(nbt.getCompound("main"));
        incomings.clear();
        var list = nbt.getList("incomings", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < list.size(); i++) {
            incomings.add(TransportedItemStack.read(list.getCompound(i)));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!main.stack.isEmpty()) {
            BlockingTransportedStorage.tickMovement(main);
        }
        for (Iterator<TransportedItemStack> iterator = incomings.iterator(); iterator.hasNext(); ) {
            TransportedItemStack incoming = iterator.next();
            if (incoming.stack.isEmpty()) {
                iterator.remove();
                continue;
            }
            if (BlockingTransportedStorage.tickMovement(incoming)) {
                if (main.stack.isEmpty()) {
                    main = incoming;
                } else {
                    main.stack.increment(incoming.stack.getCount());
//                    main.sideOffset = (main.sideOffset + incoming.sideOffset) / 2;
//                    main.angle = (main.angle + incoming.angle) / 2;
                }
                iterator.remove();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        ItemScatterer.spawn(getWorld(), getPos(), new SimpleInventory(getMerged()));
    }

    @Override
    public ItemStack apply(TransportedItemStack transp, Direction side, boolean simulate) {
        if (!allowMerge && !main.stack.isEmpty()) return transp.stack;
        int count = Math.min(transp.stack.getCount(), main.stack.getMaxCount() - main.stack.getCount());
        if (!simulate) {
            var incoming = transp.copy();
            incoming.stack.setCount(count);
            incoming.insertedFrom = side;
            afterInsert(incoming);
            tileEntity.sendData();
        }
        var remainder = transp.stack.copy();
        remainder.decrement(count);
        return remainder;
    }

    public void afterInsert(TransportedItemStack incoming) {
        if (incoming.insertedFrom.getAxis().isHorizontal()) {
            incoming.prevBeltPosition = incoming.beltPosition = 0;
        } else {
            incoming.prevBeltPosition = incoming.beltPosition = 0.5f;
        }
        incomings.add(incoming);
    }

    public ItemStack getMerged() {
        var merged = main.stack.copy();
        for (TransportedItemStack incoming : incomings) {
            if (merged.isEmpty()) {
                merged = incoming.stack;
            } else {
                merged.increment(incoming.stack.getCount());
            }
        }
        return merged;
    }

    public InsertionStorage get(Direction side) {
        var s = insertions.get(side);
        if (s == null) {
            s = new InsertionStorage(side);
            insertions.put(side, s);
        }
        return s;
    }

    @Environment(EnvType.CLIENT)
    public void render(float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        ms.push();
        ms.translate(0, -11 / 16.0, 0);
        if (!main.stack.isEmpty()) {
            SmartDrainRenderer.renderLikeDepot(main, partialTicks, ms, buffer, light, overlay);
        }
        for (TransportedItemStack incoming : incomings) {
            if (!incoming.stack.isEmpty()) {
                SmartDrainRenderer.renderLikeDepot(incoming, partialTicks, ms, buffer, light, overlay);
            }
        }
        ms.pop();
    }

    public class InsertionStorage extends SnapshotParticipant<@NotNull Integer> implements Storage<ItemVariant> {
        public final Direction side;

        public InsertionStorage(Direction side) {this.side = side;}

        @Override
        protected @NotNull Integer createSnapshot() {
            return incomings.size();
        }

        @Override
        protected void readSnapshot(@NotNull Integer snapshot) {
            while (incomings.size() > snapshot) {
                incomings.remove(incomings.size() - 1);
            }
        }

        @Override
        protected void onFinalCommit() {
            super.onFinalCommit();
            tileEntity.sendData();
        }

        @Override
        public synchronized long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
            if (!getMerged().isEmpty() && allowMerge && !resource.equals(ItemVariant.of(getMerged()))) return 0;
            updateSnapshots(transaction);
            int count = Math.min((int) maxAmount, getMerged().getMaxCount() - getMerged().getCount());
            var incoming = new TransportedItemStack(resource.toStack(count));
            incoming.insertedFrom = side.getOpposite();
            afterInsert(incoming);
            return count;
        }

        @Override
        public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
            return extraction.extract(resource, maxAmount, transaction);
        }

        @Override
        public Iterator<StorageView<ItemVariant>> iterator() {
            return extraction.iterator();
        }
    }

    public class ExtractionStorage extends SingleStackStorage implements ExtractionOnlyStorage<ItemVariant>, ReplaceableStorageView<ItemVariant> {
        @Override
        protected ItemStack getStack() {
            return main.stack;
        }

        @Override
        protected void setStack(ItemStack stack) {
            main.stack = stack;
        }

        @Override
        protected void onFinalCommit() {
            super.onFinalCommit();
            tileEntity.sendData();
        }

        @Override
        public boolean replace(ItemVariant resource, long amount, TransactionContext transa) {
            if (!incomings.isEmpty()) return false;
            updateSnapshots(transa);
            setStack(resource.toStack((int) amount));
            return true;
        }
    }
}
