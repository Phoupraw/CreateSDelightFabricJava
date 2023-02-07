package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.content.logistics.block.belts.tunnel.BeltTunnelTileEntity;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.behaviour.RollingItemBehaviour;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;

import java.util.*;
public class CopperTunnelBlockEntity extends SmartTileEntity implements SidedStorageBlockEntity {
    public static LerpedFloat createChasingFlap() {
        return LerpedFloat.linear().startWithValue(.25f).chase(0, .05f, LerpedFloat.Chaser.EXP);
    }

    /**
     * @see BeltTunnelTileEntity#flaps
     */
    @Environment(EnvType.CLIENT)
    public Map<Direction, LerpedFloat> flappings = new EnumMap<>(Direction.class);
    public Map<Direction, Boolean> toFlaps = new EnumMap<>(Direction.class);
    public RollingItemBehaviour rolling;

    public CopperTunnelBlockEntity(BlockPos pos, BlockState state) {this(MyBlockEntityTypes.COPPER_TUNNEL, pos, state);}

    public CopperTunnelBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
    }

    @Override
    public void tick() {
        super.tick();
        if (getWorld().isClient()) {
            for (LerpedFloat flapping : flappings.values()) {
                flapping.tickChaser();
            }
        }
    }

    @Override
    protected void write(NbtCompound tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        if (getWorld().isClient()) {
            for (Iterator<LerpedFloat> ite = flappings.values().iterator(); ite.hasNext(); ) {
                LerpedFloat value = ite.next();
                if (value.getChaseTarget() - value.getValue() < 0.1) {
                    ite.remove();
                }
            }
            if (!flappings.isEmpty()) {
                List<Integer> list = new ArrayList<>();
                for (Direction direction : flappings.keySet()) {
                    list.add(direction.ordinal());
                }
                tag.put("flappings", new NbtIntArray(list));
            }
        } else if (clientPacket) {
            if (!toFlaps.isEmpty()) {
                List<Integer> list = new ArrayList<>();
                for (var entry : toFlaps.entrySet()) {
                    list.add(entry.getKey().ordinal() * 2 + (entry.getValue() ? 1 : 0));
                }
                tag.put("toFlaps", new NbtIntArray(list));
            }
        }
    }

    @Override
    protected void read(NbtCompound tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        if (getWorld().isClient()) {
            flappings.clear();
            if (tag.contains("flappings", NbtElement.INT_ARRAY_TYPE)) {
                for (int ordinal : tag.getIntArray("flappings")) {
                    flappings.put(Direction.values()[ordinal], createChasingFlap());
                }
            }
            if (clientPacket) {
                if (tag.contains("toFlaps", NbtElement.INT_ARRAY_TYPE)) {
                    for (int info : tag.getIntArray("toFlaps")) {
                        flap(Direction.values()[info / 2], info % 2 == 1);
                    }
                }
            }
        }
    }

    public void flap(Direction side, boolean inward) {
        if (getWorld().isClient()) {
            if (flappings.containsKey(side))
                flappings.get(side).setValue(inward ^ side.getAxis() == Direction.Axis.Z ? -1 : 1);
            return;
        }
        toFlaps.put(side, inward);
        sendData();
    }

    @Override
    @NotNull
    public World getWorld() {
        return Objects.requireNonNull(super.getWorld());
    }

    @Override
    public @Nullable Storage<ItemVariant> getItemStorage(@Nullable Direction side) {
        if (side == Direction.DOWN) return null;
        if (rolling == null) {
            rolling = TileEntityBehaviour.get(getWorld(), getPos().down(), RollingItemBehaviour.TYPE);
        }
        if (side == null || side == Direction.UP) return rolling.insertions.get(Direction.UP);
        return rolling.insertions.get(side);
    }
}
