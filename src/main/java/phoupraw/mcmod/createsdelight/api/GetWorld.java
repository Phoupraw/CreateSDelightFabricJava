package phoupraw.mcmod.createsdelight.api;

import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
public interface GetWorld {
    @Contract(value = "null -> null", pure = true)
    static @Nullable World getOrNull(Object subject) {
        return subject instanceof GetWorld getWorld ? getWorld.getWorld() : null;
    }
    @Nullable World getWorld();
}
