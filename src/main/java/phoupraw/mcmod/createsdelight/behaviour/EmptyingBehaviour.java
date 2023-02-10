package phoupraw.mcmod.createsdelight.behaviour;

import com.simibubi.create.content.contraptions.processing.EmptyingByBasin;
import com.simibubi.create.foundation.fluid.FluidRenderer;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.CreateSDelight;
public class EmptyingBehaviour extends TileEntityBehaviour {
    public static final int DURATION = 15;
    public static final BehaviourType<EmptyingBehaviour> TYPE = new BehaviourType<>("emptying");
    public int ticks;
    public FluidStack fluid = FluidStack.EMPTY;
    private RollingItemBehaviour rolling;
    private @Nullable Storage<FluidVariant> target;

    public EmptyingBehaviour(SmartTileEntity te) {
        super(te);
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    @Override
    public void initialize() {
        super.initialize();
        var rb0 = getRolling();
        if (rb0 != null) {
            rb0.inputLimit.registerFallback((stack, rb) -> EmptyingByBasin.canItemBeEmptied(getWorld(), stack) ? 1 : null);
            rb0.continueRoll.register(this::isNotEmptying);
        }
    }

    @Override
    public void write(NbtCompound nbt, boolean clientPacket) {
        super.write(nbt, clientPacket);
        nbt.putInt("ticks", ticks);
        if (clientPacket) {
            nbt.put("variant", fluid.writeToNBT(new NbtCompound()));
        }
    }

    @Override
    public void read(NbtCompound nbt, boolean clientPacket) {
        super.read(nbt, clientPacket);
        ticks = nbt.getInt("ticks");
        if (clientPacket) {
            fluid = FluidStack.loadFluidStackFromNBT(nbt.getCompound("variant"));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!EmptyingByBasin.canItemBeEmptied(getWorld(), getStack())) {
            ticks = -1;
            return;
        }
        var fluidStorage = getTarget();
        if (fluidStorage == null) {
            ticks = -1;
            return;
        }
        var pair = EmptyingByBasin.emptyItem(getWorld(), getStack(), true);
        FluidStack fluidStack = pair.getFirst();
        if (ticks < 0) {
            fluid = fluidStack;
            ticks = DURATION;
            tileEntity.sendData();
            return;
        }
        if (ticks > 0) ticks--;
        long amount = fluidStack.getAmount();
        if (amount != fluidStorage.simulateInsert(fluidStack.getType(), amount, null)) {
            ticks = DURATION;
        }
        if (ticks == 0) {
            try (var transa = Transaction.openOuter()) {
                fluidStorage.insert(fluidStack.getType(), amount, transa);
                transa.commit();
                setStack(pair.getSecond());
                ticks = -1;
            }
        }
    }

    public @Nullable Storage<FluidVariant> getTarget() {
        if (target == null) {
            setTarget(FluidStorage.SIDED.find(getWorld(), getPos(), tileEntity.getCachedState(), tileEntity, Direction.UP));
            if (target == null) {
                setTarget(FluidStorage.SIDED.find(getWorld(), getPos(), tileEntity.getCachedState(), tileEntity, null));
            }
        }
        return target;
    }

    public void setTarget(@Nullable Storage<FluidVariant> target) {
        this.target = target;
    }

    public ItemStack getStack() {
        var rb = getRolling();
        if (rb == null) return ItemStack.EMPTY;
        return rb.transp.stack;
    }

    public void setStack(ItemStack stack) {
        var rb = getRolling();
        if (rb == null) {
            CreateSDelight.LOGGER.warn(stack + "; " + this);
            return;
        }
        rb.transp.stack = stack;
    }

    public RollingItemBehaviour getRolling() {
        if (rolling == null) {
            rolling = tileEntity.getBehaviour(RollingItemBehaviour.TYPE);
        }
        return rolling;
    }

    public boolean isNotEmptying() {
        return ticks < 0;
    }

    @Environment(EnvType.CLIENT)
    public void render(float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        if (isNotEmptying()) return;
        float radius;
        if (ticks < DURATION / 3) radius = ticks;
        else if (ticks < DURATION * 2 / 3) radius = DURATION / 3f;
        else radius = (DURATION - ticks);
        radius /= 128;
        FluidRenderer.renderFluidBox(fluid, 0.5f - radius, 2 / 16f, 0.5f - radius, 0.5f + radius, 13 / 16f, 0.5f + radius, buffer, ms, light, false);
    }
}
