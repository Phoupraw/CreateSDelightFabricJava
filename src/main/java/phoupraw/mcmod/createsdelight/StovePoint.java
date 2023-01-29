package phoupraw.mcmod.createsdelight;

import com.nhoryzon.mc.farmersdelight.entity.block.StoveBlockEntity;
import com.nhoryzon.mc.farmersdelight.registry.BlockEntityTypesRegistry;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
public class StovePoint extends ArmInteractionPoint {
    private final Storage<ItemVariant> handler = FarmersDelightWrappers.storageOf((StoveBlockEntity) Objects.requireNonNull(level.getBlockEntity(pos)));

    public StovePoint(ArmInteractionPointType type, World level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }

    @Nullable
    @Override
    protected Storage<ItemVariant> getHandler() {
        return handler;
    }
}
