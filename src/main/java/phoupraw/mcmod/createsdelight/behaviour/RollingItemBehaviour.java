package phoupraw.mcmod.createsdelight.behaviour;

import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.DirectBeltInputBehaviour;
import com.simibubi.create.foundation.utility.BlockHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.ExtractionOnlyStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.CreateSDelight;
import phoupraw.mcmod.createsdelight.api.ReplaceableStorageView;

import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import static phoupraw.mcmod.createsdelight.block.entity.renderer.SmartDrainRenderer.renderLikeDepot;
import static phoupraw.mcmod.createsdelight.block.entity.renderer.SmartDrainRenderer.renderLikeDrain;
public class RollingItemBehaviour extends TileEntityBehaviour implements DirectBeltInputBehaviour.InsertionCallback {
    public static final float STEP = 1 / 8f;
    public static final BehaviourType<RollingItemBehaviour> TYPE = new BehaviourType<>("item_drain_item");

    @Environment(EnvType.CLIENT)
    public static void render(TransportedItemStack transp, BlockPos pos, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        ItemStack surfaceItem = transp.stack;
        if (!surfaceItem.isEmpty()) {
            Direction insertedFrom = transp.insertedFrom;
            if (insertedFrom.getAxis().isHorizontal()) {
                renderLikeDrain(transp, pos, partialTicks, ms, buffer, light, overlay);
            } else {
                renderLikeDepot(transp, partialTicks, ms, buffer, light, overlay);
            }
        }
    }

    public @NotNull TransportedItemStack transp = TransportedItemStack.EMPTY;
    public final Map<Direction, SideStorage> insertions = new EnumMap<>(Direction.class);
    public ItemApiLookup<Integer, RollingItemBehaviour> inputLimit = ItemApiLookup.get(new Identifier(CreateSDelight.MOD_ID, "roll/input_limit"), Integer.class, RollingItemBehaviour.class);
    public Event<Supplier<@Nullable Integer>> outputLimit = EventFactory.createArrayBacked(Supplier.class, providers -> () -> {
        for (var provider : providers) {
            var limit = provider.get();
            if (limit != null) return limit;
        }
        return null;
    });
    public Event<BooleanSupplier> continueRoll = EventFactory.createArrayBacked(BooleanSupplier.class, providers -> () -> {
        for (var provider : providers) if (!provider.getAsBoolean()) return false;
        return true;
    });
    public final ExtractionStorage extraction = new ExtractionStorage();

    public RollingItemBehaviour(SmartTileEntity te) {
        super(te);
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    @Override
    public void write(NbtCompound nbt, boolean clientPacket) {
        super.write(nbt, clientPacket);
        if (!transp.stack.isEmpty())
            nbt.put("transp", transp.serializeNBT());
    }

    @Override
    public void read(NbtCompound nbt, boolean clientPacket) {
        super.read(nbt, clientPacket);
        transp = TransportedItemStack.read(nbt.getCompound("transp"));
    }

    @Override
    public void tick() {
        super.tick();
        TransportedItemStack transp = this.transp;
        ItemStack stack = transp.stack;
        if (stack.isEmpty()) return;
        BlockPos pos = getPos();
        World world = getWorld();
        Direction insertedFrom = transp.insertedFrom;
        if (insertedFrom.getAxis().isVertical()) {
            turn();
        } else {
            float beltPosition = transp.beltPosition;
            transp.prevBeltPosition = beltPosition;
            if (beltPosition < 0.5f) {
                transp.beltPosition += STEP;
            } else if (beltPosition < 0.5f + STEP) {
                if (continueRoll.invoker().getAsBoolean()) {
                    transp.beltPosition += STEP;
                    tileEntity.sendData();
                }
            } else if (beltPosition < 1) {
                transp.beltPosition += STEP;
            } else if (beltPosition >= 1) {
                output(insertedFrom, true, false);
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        ItemScatterer.spawn(getWorld(), getPos(), new SimpleInventory(transp.stack));
    }

    @Override
    public ItemStack apply(TransportedItemStack transp, Direction side, boolean simulate) {
        if (!this.transp.stack.isEmpty()) return transp.stack;
        int count = limitInput(transp.stack);
        if (!simulate) {
            this.transp = transp.copy();
            this.transp.stack.setCount(count);
            afterInsert(side);
        }
        var remainder = transp.stack.copy();
        remainder.decrement(count);
        return remainder;
    }

    @MustBeInvokedByOverriders
    public int limitInput(ItemStack stack) {
        var limit = inputLimit.find(stack, this);
        return limit != null ? limit : stack.getCount();
    }

    @MustBeInvokedByOverriders
    public void afterInsert(Direction insertedFrom) {
        TransportedItemStack transp = this.transp;
        transp.insertedFrom = insertedFrom;
        if (insertedFrom.getAxis().isHorizontal()) {
            transp.prevBeltPosition = transp.beltPosition = 0;
        } else {
            transp.prevBeltPosition = transp.beltPosition = 0.5f;
        }
        tileEntity.sendData();
    }

    public @NotNull SideStorage get(@NotNull Direction side) {
        var view = insertions.get(side);
        if (view == null) {
            view = new SideStorage(side);
            insertions.put(side, view);
        }
        return view;
    }

    public void turn() {
        TransportedItemStack transp = this.transp;
        World world = getWorld();
        for (Direction to : Direction.Type.HORIZONTAL.getShuffled(world.getRandom())) {
            if (output(to, false, true)) {
                transp.insertedFrom = to;
                break;
            }
        }
    }

    public boolean output(Direction insertedFrom, boolean throwable, boolean simulate) {
        TransportedItemStack transp = this.transp;
        ItemStack stack = transp.stack;
        BlockPos pos = getPos();
        World world = getWorld();
        BlockPos offset = pos.offset(insertedFrom);
        var db0 = tileEntity.getBehaviour(DirectBeltInputBehaviour.TYPE);
        if (db0 != null && db0.canSupportBeltFunnels()) {
            ItemStack remainder = db0.tryExportingToBeltFunnel(stack, insertedFrom, simulate);
            if (remainder != null) {
                if (!simulate) transp.stack = remainder;
                return !stack.equals(remainder);
            }
        }
        DirectBeltInputBehaviour db1 = TileEntityBehaviour.get(world, offset, DirectBeltInputBehaviour.TYPE);
        if (db1 != null) {
            ItemStack remainder = db1.handleInsertion(transp, insertedFrom, simulate);
            if (!simulate) transp.stack = remainder;
            return !stack.equals(remainder);
        }
        if (!throwable) return false;
        if (!BlockHelper.hasBlockSolidSide(world.getBlockState(offset), world, offset, insertedFrom.getOpposite())) {
            if (!simulate) {
                Vec3d outPos = VecHelper.getCenterOf(pos).add(Vec3d.of(insertedFrom.getVector()).multiply(.75));
                Vec3d outMotion = Vec3d.of(insertedFrom.getVector()).multiply(STEP).add(0, STEP, 0);
                outPos.add(outMotion.normalize());
                ItemEntity entity = new ItemEntity(world, outPos.x, outPos.y + 6 / 16f, outPos.z, stack);
                entity.setVelocity(outMotion);
                entity.setToDefaultPickupDelay();
                entity.velocityModified = true;
                world.spawnEntity(entity);
                transp.stack = ItemStack.EMPTY;
                tileEntity.sendData();
            }
            return true;
        }
        return false;
    }

    @Environment(EnvType.CLIENT)
    public void render(float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        render(transp, getPos(), partialTicks, ms, buffer, light, overlay);
    }

    public class SideStorage extends SingleStackStorage implements ReplaceableStorageView<ItemVariant> {
        private final Direction side;

        public SideStorage(Direction side) {this.side = side;}

        @Override
        public ItemStack getStack() {
            return transp.stack;
        }

        @Override
        public void setStack(ItemStack stack) {
            transp = new TransportedItemStack(stack);
        }

        @Override
        public void onFinalCommit() {
            super.onFinalCommit();
            afterInsert(getSide().getOpposite());
        }

        @Override
        public long insert(ItemVariant insertedVariant, long maxAmount, TransactionContext transaction) {
            return super.insert(insertedVariant, limitInput(insertedVariant.toStack((int) maxAmount)), transaction);
        }

        @Override
        public boolean replace(ItemVariant resource, long amount, TransactionContext transa) {
            return extraction.replace(resource, amount, transa);
        }

        public Direction getSide() {
            return side;
        }

        @Override
        public Iterator<StorageView<ItemVariant>> iterator() {
            return extraction.iterator();
        }

        @Override
        public StorageView<ItemVariant> getUnderlyingView() {
            return extraction;
        }
    }

    public class ExtractionStorage extends SingleStackStorage implements ExtractionOnlyStorage<ItemVariant>, ReplaceableStorageView<ItemVariant> {

        @Override
        protected ItemStack getStack() {
            return transp.stack;
        }

        @Override
        protected void setStack(ItemStack stack) {
            transp.stack = stack;
        }

        @Override
        public boolean replace(ItemVariant resource, long amount, TransactionContext transa) {
            updateSnapshots(transa);
            setStack(resource.toStack((int) amount));
            return true;
        }

        @Override
        protected void onFinalCommit() {
            super.onFinalCommit();
            tileEntity.sendData();
        }
    }
}
