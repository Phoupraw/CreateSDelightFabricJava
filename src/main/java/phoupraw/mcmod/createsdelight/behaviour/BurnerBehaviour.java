package phoupraw.mcmod.createsdelight.behaviour;

import com.simibubi.create.content.contraptions.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.registry.CDFluids;

import java.util.List;
public class BurnerBehaviour extends TileEntityBehaviour implements IHaveGoggleInformation {
    public static final BehaviourType<BurnerBehaviour> TYPE = new BehaviourType<>("burner");
    public @Nullable Storage<ItemVariant> itemS;
    public @Nullable Storage<FluidVariant> fluidS;
    private int fuelTicks = 0;

    public BurnerBehaviour(SmartTileEntity te) {
        super(te);
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    @Override
    public void initialize() {
        super.initialize();
        if (getWorld().isClient()) return;
        if (itemS == null) {
            itemS = ItemStorage.SIDED.find(getWorld(), getPos(), tileEntity.getCachedState(), tileEntity, null);
        }
        if (fluidS == null) {
            fluidS = FluidStorage.SIDED.find(getWorld(), getPos(), tileEntity.getCachedState(), tileEntity, null);
        }
    }

    @Override
    public void write(NbtCompound nbt, boolean clientPacket) {
        super.write(nbt, clientPacket);
        nbt.putInt("fuelTicks", getFuelTicks());
    }

    @Override
    public void read(NbtCompound nbt, boolean clientPacket) {
        super.read(nbt, clientPacket);
        this.fuelTicks = nbt.getInt("fuelTicks");
    }

    @Override
    public void tick() {
        super.tick();
        if (getFuelTicks() <= 0) {return;}
        setFuelTicks(getFuelTicks() - 1);
        if (getWorld().isClient()) return;
        if (getFuelTicks() != 0) {return;}
        if (tryIgnite() == 0) {
            setFuelTicks(-1);
        }
    }

    public int tryIgnite() {
        if (itemS != null) {
            for (var view : itemS) {
                var resource = view.getResource();
                Integer fuelTime = FuelRegistry.INSTANCE.get(resource.getItem());
                if (fuelTime == null) continue;
                try (var transa = Transaction.openOuter()) {
                    long extract = view.extract(resource, 1, transa);
                    if (extract == 1) {
                        boolean b = true;
                        if (resource.getItem().getRecipeRemainder() != null) {
                            long inserted = itemS.insert(ItemVariant.of(resource.getItem().getRecipeRemainder()), 1, transa);
                            if (inserted != 1) b = false;
                        }
                        if (b) {
                            transa.commit();
                            setFuelTicks(getFuelTicks() + fuelTime);
                            return fuelTime;
                        }
                    }
                }
            }
        }
        if (fluidS != null) {
            for (var view : fluidS) {
                var resource = view.getResource();
                if (!resource.isOf(CDFluids.SUNFLOWER_OIL)) continue;
                try (var transa = Transaction.openOuter()) {
                    long amount = view.extract(resource, FluidConstants.NUGGET, transa);
                    if (amount > 0) {
                        transa.commit();
                        int fuelTime = (int) (20 * amount / FluidConstants.NUGGET);
                        setFuelTicks(getFuelTicks() + fuelTime);
                        return fuelTime;
                    }
                }
            }
        }
        return 0;
    }

    public int getFuelTicks() {
        return fuelTicks;
    }

    public void setFuelTicks(int fuelTicks) {
        int p = getFuelTicks();
        this.fuelTicks = fuelTicks;
        if (p < 0 && fuelTicks >= 0) {
            onIgnite();
        } else if (p >= 0 && fuelTicks < 0) {
            onExtinguish();
        }
    }

    @ApiStatus.OverrideOnly
    @MustBeInvokedByOverriders
    public void onIgnite() {
        tileEntity.sendData();
    }

    @ApiStatus.OverrideOnly
    @MustBeInvokedByOverriders
    public void onExtinguish() {
        tileEntity.sendData();
    }

    @Override
    public boolean addToGoggleTooltip(List<Text> tooltip, boolean isPlayerSneaking) {
        int fuelTicks = getFuelTicks();
        if (fuelTicks < 0) fuelTicks = 0;
        Formatting formatting;
        if (fuelTicks < 5 * 20) {
            formatting = Formatting.GRAY;
        } else if (fuelTicks < 60 * 20) {
            formatting = Formatting.GOLD;
        } else if (fuelTicks < 10 * 60 * 20) {
            formatting = Formatting.RED;
        } else {
            formatting = Formatting.DARK_RED;
        }
        String time = "%d:%02d:%02d".formatted(fuelTicks / 20 / 60, fuelTicks / 20 % 60, fuelTicks % 20);
        tooltip.add(Text.translatable("burn_time", Text.literal(time).formatted(formatting)));
        return true;
    }
}
