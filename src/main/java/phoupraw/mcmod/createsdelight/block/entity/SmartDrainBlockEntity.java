package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.content.contraptions.fluids.actors.ItemDrainTileEntity;
import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.contraptions.processing.EmptyingByBasin;
import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.DirectBeltInputBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.fluid.SmartFluidTankBehaviour;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleStackStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;

import java.util.*;
import java.util.function.Predicate;
public class SmartDrainBlockEntity extends SmartTileEntity implements DirectBeltInputBehaviour.InsertionCallback, SidedStorageBlockEntity, IHaveGoggleInformation {
    public static final Event<Predicate<SmartDrainBlockEntity>> SURFACE_TICKER = EventFactory.createArrayBacked(Predicate.class, tickers -> drain -> {
        for (Predicate<SmartDrainBlockEntity> ticker : tickers) if (!ticker.test(drain)) return false;
        return true;
    });
    //    public static final Map<String,Function<SmartDrainBlockEntity,>>
    static {
        SURFACE_TICKER.register(drain -> {
            TransportedItemStack surface = drain.surface;
            ItemStack stack = surface.stack;
            if (!EmptyingByBasin.canItemBeEmptied(drain.getWorld(), stack)) return true;
            drain.surfaceRenderer = "drain";
            var pair = EmptyingByBasin.emptyItem(drain.getWorld(), stack, true);
            FluidStack fluidStack = pair.getFirst();
            long amount = fluidStack.getAmount();
            SmartFluidTank tank = drain.tank.getPrimaryHandler();
            FluidVariant variant = fluidStack.getType();
            try (var transaction = Transaction.openOuter()) {
                if (tank.insert(variant, amount, transaction) != amount) return false;
            }
            if (drain.drainTicks < ItemDrainTileEntity.FILLING_TIME) {
                drain.drainTicks++;
            } else {
                drain.drainTicks = 0;
                try (var transaction = Transaction.openOuter()) {
                    tank.insert(variant, amount, transaction);
                    surface.stack = pair.getSecond();
                    transaction.commit();
                }
            }
            return false;
        });
    }
    public final Map<@NotNull Direction, SurfaceStorage> surfaceS = new EnumMap<>(Direction.class);
    public TransportedItemStack surface = TransportedItemStack.EMPTY;
    public boolean continueRoll = false;
    public int drainTicks;
    public SmartFluidTankBehaviour tank;
    public String surfaceRenderer = "";
    public SurfaceStrategy surfaceStrategy;
//    @Environment(EnvType.CLIENT)
//    public SmartDrainRenderer.Renderer surfaceRenderer;

    public SmartDrainBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.SMART_DRAIN, pos, state);}

    public SmartDrainBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
        behaviours.add(new DirectBeltInputBehaviour(this).setInsertionHandler(this).allowingBeltFunnels());
        behaviours.add(tank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.TYPE, this, 1, FluidConstants.BUCKET * 2, false));
    }

    @Override
    public void tick() {
        super.tick();
        TransportedItemStack surface = this.surface;
        ItemStack surfaceItem = surface.stack;
        float beltPosition = surface.beltPosition;
        float speed = getSurfaceSpeed();
        if (!surfaceItem.isEmpty()) {
            surface.prevBeltPosition = beltPosition;
            Direction insertedFrom = surface.insertedFrom;
            if (insertedFrom.getAxis().isVertical()) {
                setRollingToAround();
            } else {
                if (beltPosition < 0.5f || beltPosition >= 0.5f + speed && beltPosition < 1) {
                    beltPosition += speed;
                } else if (beltPosition >= 0.5f && beltPosition < 0.5f + speed) {
                    if (continueRoll) {
                        beltPosition += speed;
                        continueRoll = false;
                    } else {
                        if (getWorld().isClient()) {
                            if (drainTicks > 0) {
                                drainTicks--;
                            }
                        } else {
                            if (drainTicks <= 5 && drainTicks > 0) {
                                drainTicks--;
                            } else if (EmptyingByBasin.canItemBeEmptied(getWorld(), surfaceItem)) {
                                var pair = EmptyingByBasin.emptyItem(getWorld(), surfaceItem, true);
                                try (Transaction t = TransferUtil.getTransaction()) {
                                    if (pair.getFirst().getAmount() == tank.getPrimaryHandler().insert(pair.getFirst().getType(), pair.getFirst().getAmount(), t)) {
                                        if (drainTicks == 0) {
                                            drainTicks = ItemDrainTileEntity.FILLING_TIME;
                                            sendData();
                                        }
                                        drainTicks--;
                                    } else {
                                        drainTicks = 0;
                                    }
                                }
                                    if (drainTicks == 5) {try (Transaction t = TransferUtil.getTransaction()) {
                                        tank.getPrimaryHandler().insert(pair.getFirst().getType(), pair.getFirst().getAmount(),t);
                                        t.commit();
                                        surface.stack = pair.getSecond();
                                    }
                                }
                            } else {
                                continueRoll = true;
                                sendData();
                            }
                        }
                    }
                } else if (beltPosition >= 1) {
                    DirectBeltInputBehaviour b = TileEntityBehaviour.get(getWorld(), pos.offset(insertedFrom), DirectBeltInputBehaviour.TYPE);
                    if (b != null) {
                        ItemStack now = surface.stack = b.handleInsertion(surface, insertedFrom, false);
                        if (!surfaceItem.equals(now)) notifyUpdate();
                    }
                }
                surface.beltPosition = beltPosition;
            }
        }
        this.surface = surface;
    }

    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.put("surface", surface.serializeNBT());
        tag.putBoolean("continueRoll", continueRoll);
        tag.putInt("surfaceTicks", drainTicks);
        tag.putString("surfaceRenderer", surfaceRenderer);
    }

    @Override
    protected void read(NbtCompound tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        surface = TransportedItemStack.read(tag.getCompound("surface"));
        continueRoll = tag.getBoolean("continueRoll");
        drainTicks = tag.getInt("surfaceTicks");
        surfaceRenderer = tag.getString("surfaceRenderer");
    }

    @Override
    public void destroy() {
        super.destroy();
        ItemScatterer.spawn(getWorld(), getPos(), DefaultedList.ofSize(1, surface.stack));
    }

    @NotNull
    @Override
    public World getWorld() {
        return Objects.requireNonNull(super.getWorld());
    }

    @Override
    public ItemStack apply(TransportedItemStack stack, @NotNull Direction side, boolean simulate) {
        if (side == Direction.DOWN) return stack.stack;
        return getSurfaceStorage(side).apply(stack, side, simulate);
    }

    @Override
    public @Nullable Storage<ItemVariant> getItemStorage(Direction side) {
        if (side == Direction.UP) return getSurfaceStorage(Direction.UP);
        return null;//TODO
    }

    @Override
    public @Nullable Storage<FluidVariant> getFluidStorage(Direction side) {
        if (side == Direction.UP) return null;
        return tank.getCapability();
    }

    @Override
    public boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        return containedFluidTooltip(tooltip, isPlayerSneaking, tank.getCapability());
    }

    public @NotNull SurfaceStorage getSurfaceStorage(@NotNull Direction side) {
        if (side == Direction.DOWN) throw new IllegalArgumentException(side.asString());
        var s = surfaceS.get(side);
        if (s == null) {
            if (side == Direction.UP) s = new SurfaceUpStorage();
            else s = new SurfaceHorizontalStorage(side);
            surfaceS.put(side, s);
        }
        return s;
    }

    public float getSurfaceSpeed() {
        return 1 / 8f;
    }

    public void setRollingToAround() {
        World world = getWorld();
        BlockPos pos = getPos();
        TransportedItemStack surface = this.surface;
        for (Direction to : Direction.Type.HORIZONTAL.getShuffled(world.getRandom())) {
            DirectBeltInputBehaviour b = TileEntityBehaviour.get(world, pos.offset(to), DirectBeltInputBehaviour.TYPE);
            if (b == null) continue;
            var remainder = b.handleInsertion(surface, to, true);
            if (!remainder.equals(surface.stack)) surface.insertedFrom = to;
        }
    }

    public int limitInput(ItemStack inputting) {
        if (inputting.isEmpty()) return 0;
        if (EmptyingByBasin.canItemBeEmptied(getWorld(), inputting)) return 1;
        return inputting.getCount();
    }

    public abstract class SurfaceStorage extends SingleStackStorage implements DirectBeltInputBehaviour.InsertionCallback {
        @MustBeInvokedByOverriders
        @Override
        public ItemStack getStack() {
            return surface.stack;
        }

        @MustBeInvokedByOverriders
        @Override
        public void setStack(ItemStack stack) {
            surface = new TransportedItemStack(stack);
        }

        @Override
        protected boolean canInsert(ItemVariant itemVariant) {
            return getStack().isEmpty();
        }

        @MustBeInvokedByOverriders
        @Override
        protected void onFinalCommit() {
            super.onFinalCommit();
            beforeNotify();
            notifyUpdate();
        }

        @Override
        public long insert(ItemVariant insertedVariant, long maxAmount, TransactionContext transaction) {
            return super.insert(insertedVariant, limitInput(insertedVariant.toStack((int) maxAmount)), transaction);
        }

        @Override
        public ItemStack apply(TransportedItemStack transp, Direction side, boolean simulate) {
            ItemStack stack = transp.stack;
            if (!getStack().isEmpty()) return stack;
            int count = limitInput(stack);
            if (!simulate) {
                var inserted = transp.copy();
                inserted.stack.setCount(count);
                inserted.insertedFrom = side;
                surface = inserted;
                beforeNotify();
                notifyUpdate();
            }
            var remainder = stack.copy();
            remainder.decrement(count);
            return remainder;
        }

        public abstract void beforeNotify();
    }

    public class SurfaceHorizontalStorage extends SurfaceStorage {
        public final Direction side;

        public SurfaceHorizontalStorage(Direction side) {this.side = side;}

        @Override
        public void beforeNotify() {
            TransportedItemStack surface = SmartDrainBlockEntity.this.surface;
            surface.prevBeltPosition = surface.beltPosition = 0;
        }
    }

    public class SurfaceUpStorage extends SurfaceStorage {
        @Override
        public void beforeNotify() {
            TransportedItemStack surface = SmartDrainBlockEntity.this.surface;
            surface.prevBeltPosition = surface.beltPosition = 0.5f;
            setRollingToAround();
        }
    }

    public interface SurfaceStrategy {
        int limitInput(SmartDrainBlockEntity drain, ItemStack inputStack);
        void tick(SmartDrainBlockEntity drain);
        boolean queryRoll(SmartDrainBlockEntity drain);
        @Environment(EnvType.CLIENT)
        void render(SmartDrainBlockEntity drain, float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay);
    }

}
