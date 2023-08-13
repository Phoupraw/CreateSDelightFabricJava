package phoupraw.mcmod.createsdelight.block.entity;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SidedStorageBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import phoupraw.mcmod.createsdelight.behaviour.RollingItemBehaviour;
import phoupraw.mcmod.createsdelight.registry.MyBlockEntityTypes;

import java.util.*;
public class CopperTunnelBlockEntity extends SmartBlockEntity implements SidedStorageBlockEntity {
    public static LerpedFloat createChasingFlap() {
        return LerpedFloat.linear().startWithValue(.25f).chase(0, .05f, LerpedFloat.Chaser.EXP);
    }

    /**
     * @see com.simibubi.create.content.logistics.tunnel.BeltTunnelBlockEntity#flaps
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
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
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
        if (!getWorld().isClient()) {
            if (clientPacket) {
                if (!toFlaps.isEmpty()) {
                    List<Integer> list = new ArrayList<>();
                    for (var entry : toFlaps.entrySet()) {
                        list.add(entry.getKey().ordinal() * 2 + (entry.getValue() ? 1 : 0));
                    }
                    tag.put("toFlaps", new NbtIntArray(list));
                    toFlaps.clear();
                }
            }
        }
    }

    @Override
    protected void read(NbtCompound tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        if (super.getWorld() != null && getWorld().isClient()) {
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
        if (!getWorld().isClient()) {
            toFlaps.put(side, inward);
            sendData();
            return;
        }
        if (flappings.containsKey(side)) {
            flappings.get(side).setValue(inward ^ side.getAxis() == Direction.Axis.Z ? -1 : 1);
        } else {
            var lf = createChasingFlap();
            if (inward) {
                lf.updateChaseTarget(-lf.getChaseTarget());
            }
            flappings.put(side, lf);
        }
//        System.out.println(flappings);
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
            rolling = BlockEntityBehaviour.get(getWorld(), getPos().down(), RollingItemBehaviour.TYPE);
        }
        if (side == null || side == Direction.UP) return rolling.insertions.get(Direction.UP);
        return rolling.insertions.get(side);
    }
}
