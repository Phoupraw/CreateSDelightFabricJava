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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
public class EmptyingBehaviour extends TileEntityBehaviour {
    public static final BehaviourType<EmptyingBehaviour> TYPE = new BehaviourType<>("emptying");
    public int ticks;
    public FluidStack fluid = FluidStack.EMPTY;
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
        var rb0 = tileEntity.getBehaviour(RollingItemBehaviour.TYPE);
        if (rb0 != null) {
            rb0.inputLimit.registerFallback((stack, rb) -> EmptyingByBasin.canItemBeEmptied(getWorld(), stack) ? 1 : null);
            rb0.continueRoll.register(this::tickEmpty);
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
        if (ticks > 0) {
            ticks--;
        }
    }

    public boolean tickEmpty(RollingItemBehaviour rb) {//FIXME
        if (!EmptyingByBasin.canItemBeEmptied(getWorld(), rb.transp.stack)) return true;
        var fluidStorage = getTarget();
        if (fluidStorage == null) return true;
        if (ticks == 0) {
            ticks = 15;
            tileEntity.sendData();
            return false;
        }
        var pair = EmptyingByBasin.emptyItem(getWorld(), rb.transp.stack, true);
        FluidStack fluidStack = pair.getFirst();
        long amount = fluidStack.getAmount();
        try (var transa = Transaction.openOuter()) {
            if (amount != fluidStorage.insert(fluidStack.getType(), amount, transa)) {
                ticks = 15;
                return false;
            }
        }
        ticks--;
        if (ticks == 0) {
            try (var transa = Transaction.openOuter()) {
                fluidStorage.insert(fluidStack.getType(), amount, transa);
                transa.commit();
                rb.transp.stack = pair.getSecond();
                tileEntity.sendData();
                return true;
            }
        }
        return false;
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

    @Environment(EnvType.CLIENT)
    public void render(float partialTicks, MatrixStack ms, VertexConsumerProvider buffer, int light, int overlay) {
        float radius = (float) (Math.pow(((2 * ticks) - 1), 2) - 1);
        FluidRenderer.renderFluidBox(fluid, 0.5f - radius, 2 / 16f, 0.5f - radius, 0.5f + radius, 13 / 16f, 0.5f + radius, buffer, ms, light, false);
    }
}
