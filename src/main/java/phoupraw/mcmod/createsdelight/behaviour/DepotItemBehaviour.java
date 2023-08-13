package phoupraw.mcmod.createsdelight.behaviour;

import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.ExtractionOnlyStorage;
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
import phoupraw.mcmod.createsdelight.block.entity.renderer.SmartDrainRenderer;
import phoupraw.mcmod.createsdelight.storage.BlockingTransportedStorage;
import phoupraw.mcmod.createsdelight.storage.ReplaceableStorageView;

import java.util.*;
public class DepotItemBehaviour extends BlockEntityBehaviour implements DirectBeltInputBehaviour.InsertionCallback {
    public static final BehaviourType<DepotItemBehaviour> TYPE = new BehaviourType<>("less_depot");
    private TransportedItemStack main = TransportedItemStack.EMPTY;
    private List<TransportedItemStack> incomings = new ArrayList<>();
    private boolean allowMerge = true;
    private Map<Direction, Storage<ItemVariant>> insertions = new EnumMap<>(Direction.class);
    private Storage<ItemVariant> extraction = newExtractionStorage();

    public DepotItemBehaviour(SmartBlockEntity te) {
        super(te);
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    @Override
    public void write(NbtCompound nbt, boolean clientPacket) {
        super.write(nbt, clientPacket);
        if (!getMain().stack.isEmpty()) {
            nbt.put("main", getMain().serializeNBT());
        }
        if (!getIncomings().isEmpty()) {
            var list = new NbtList();
            for (TransportedItemStack incoming : getIncomings()) {
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
        setMain(TransportedItemStack.read(nbt.getCompound("main")));
        getIncomings().clear();
        var list = nbt.getList("incomings", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < list.size(); i++) {
            getIncomings().add(TransportedItemStack.read(list.getCompound(i)));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!getMain().stack.isEmpty()) {
            BlockingTransportedStorage.tickMovement(getMain());
        }
        for (Iterator<TransportedItemStack> iterator = getIncomings().iterator(); iterator.hasNext(); ) {
            TransportedItemStack incoming = iterator.next();
            if (incoming.stack.isEmpty()) {
                iterator.remove();
                continue;
            }
            if (BlockingTransportedStorage.tickMovement(incoming)) {
                if (getMain().stack.isEmpty()) {
                    setMain(incoming);
                } else {
                    getMain().stack.increment(incoming.stack.getCount());
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
        if (!isAllowMerge() && !getMain().stack.isEmpty()) return transp.stack;
        int count = Math.min(transp.stack.getCount(), getMain().stack.getMaxCount() - getMain().stack.getCount());
        if (!simulate) {
            var incoming = transp.copy();
            incoming.stack.setCount(count);
            incoming.insertedFrom = side;
            afterInsert(incoming);
            blockEntity.sendData();
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
        getIncomings().add(incoming);
    }

    public ItemStack getMerged() {
        var merged = getMain().stack.copy();
        for (TransportedItemStack incoming : getIncomings()) {
            if (merged.isEmpty()) {
                merged = incoming.stack;
            } else {
                merged.increment(incoming.stack.getCount());
            }
        }
        return merged;
    }

    public Storage<ItemVariant> get(Direction side) {
        var s = getInsertions().get(side);
        if (s == null) {
            s = newInsertionStorage(side);
            getInsertions().put(side, s);
        }
        return s;
    }

    public Storage<ItemVariant> newInsertionStorage(Direction side) {
        return new InsertionStorage(side);
    }

    public Storage<ItemVariant> newExtractionStorage() {
        return new ExtractionStorage();
    }

    @Environment(EnvType.CLIENT)
    public void render(float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        ms.push();
        ms.translate(0, -11 / 16.0, 0);
        if (!getMain().stack.isEmpty()) {
            SmartDrainRenderer.renderLikeDepot(getMain(), partialTicks, ms, buffer, light, overlay);
        }
        for (TransportedItemStack incoming : getIncomings()) {
            if (!incoming.stack.isEmpty()) {
                SmartDrainRenderer.renderLikeDepot(incoming, partialTicks, ms, buffer, light, overlay);
            }
        }
        ms.pop();
    }

    public TransportedItemStack getMain() {
        return main;
    }

    public void setMain(TransportedItemStack main) {
        this.main = main;
    }

    public List<TransportedItemStack> getIncomings() {
        return incomings;
    }

    public void setIncomings(List<TransportedItemStack> incomings) {
        this.incomings = incomings;
    }

    public boolean isAllowMerge() {
        return allowMerge;
    }

    public void setAllowMerge(boolean allowMerge) {
        this.allowMerge = allowMerge;
    }

    public Map<Direction, Storage<ItemVariant>> getInsertions() {
        return insertions;
    }

    public void setInsertions(Map<Direction, Storage<ItemVariant>> insertions) {
        this.insertions = insertions;
    }

    public Storage<ItemVariant> getExtraction() {
        return extraction;
    }

    public void setExtraction(Storage<ItemVariant> extraction) {
        this.extraction = extraction;
    }

    public class InsertionStorage extends SnapshotParticipant<@NotNull Integer> implements Storage<ItemVariant> {
        private final Direction side;

        public InsertionStorage(Direction side) {this.side = side;}

        @Override
        protected @NotNull Integer createSnapshot() {
            return getIncomings().size();
        }

        @Override
        protected void readSnapshot(@NotNull Integer snapshot) {
            while (getIncomings().size() > snapshot) {
                getIncomings().remove(getIncomings().size() - 1);
            }
        }

        @Override
        protected void onFinalCommit() {
            super.onFinalCommit();
            blockEntity.sendData();
        }

        @Override
        public long insert(ItemVariant resource, long maxAmount, TransactionContext transaction) {
            if (!getMerged().isEmpty() && isAllowMerge() && !resource.equals(ItemVariant.of(getMerged()))) return 0;
            updateSnapshots(transaction);
            int count = Math.min((int) maxAmount, getMerged().getMaxCount() - getMerged().getCount());
            var incoming = new TransportedItemStack(resource.toStack(count));
            incoming.insertedFrom = getSide().getOpposite();
            afterInsert(incoming);
            return count;
        }

        @Override
        public long extract(ItemVariant resource, long maxAmount, TransactionContext transaction) {
            return getExtraction().extract(resource, maxAmount, transaction);
        }

        @Override
        public Iterator<StorageView<ItemVariant>> iterator() {
            return getExtraction().iterator();
        }

        public Direction getSide() {
            return side;
        }
    }

    public class ExtractionStorage extends SingleStackStorage implements ExtractionOnlyStorage<ItemVariant>, ReplaceableStorageView<ItemVariant> {
        @Override
        protected ItemStack getStack() {
            return getMain().stack;
        }

        @Override
        protected void setStack(ItemStack stack) {
            getMain().stack = stack;
        }

        @Override
        protected void onFinalCommit() {
            super.onFinalCommit();
            blockEntity.sendData();
        }

        @Override
        public boolean replace(ItemVariant resource, long amount, TransactionContext transa) {
            if (!getIncomings().isEmpty()) return false;
            updateSnapshots(transa);
            setStack(resource.toStack((int) amount));
            return true;
        }
    }
}
