package phoupraw.mcmod.createsdelight.behaviour;

import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.BehaviourType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import phoupraw.mcmod.createsdelight.storage.BlockingTransportedStorage;
import phoupraw.mcmod.createsdelight.CreateSDelight;
public class BlockingTransportedBehaviour extends TileEntityBehaviour {
    public static final BehaviourType<BlockingTransportedBehaviour> TYPE = new BehaviourType<>(new Identifier(CreateSDelight.MOD_ID, "blocking_transported").toString());

    private final BlockingTransportedStorage transported = new BlockingTransportedStorage() {
        @Override
        protected void onFinalCommit() {
            super.onFinalCommit();
            BlockingTransportedBehaviour.this.onFinalCommit();
        }
    };

    public BlockingTransportedBehaviour(SmartTileEntity te) {
        super(te);
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    @Override
    public void tick() {
        super.tick();
        BlockingTransportedStorage.tickMovement(getStorage().getTransported());
    }

    @Override
    public void write(NbtCompound nbt, boolean clientPacket) {
        super.write(nbt, clientPacket);
        nbt.put("item",getStorage().getTransported().serializeNBT());
    }

    @Override
    public void read(NbtCompound nbt, boolean clientPacket) {
        super.read(nbt, clientPacket);
        getStorage().setTransported(TransportedItemStack.read(nbt.getCompound("item")));
    }

    public BlockingTransportedStorage getStorage() {
        return transported;
    }
    @ApiStatus.OverrideOnly
    @MustBeInvokedByOverriders
    public void onFinalCommit() {
        tileEntity.sendData();
    }
}
