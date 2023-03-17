package phoupraw.mcmod.createsdelight.exp;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
public interface PowderVariant extends TransferVariant<Map<ItemVariant, Integer>> {
    @Override
    @Contract(pure = true)
    default boolean isBlank() {
        return getObject().isEmpty() || getObject().entrySet().stream().allMatch(entry -> entry.getKey().isBlank() || entry.getValue() == 0);
    }
    @Override
    @Nullable
    @Contract(value = "->null", pure = true)
    default NbtCompound getNbt() {
        return null;
    }
}
