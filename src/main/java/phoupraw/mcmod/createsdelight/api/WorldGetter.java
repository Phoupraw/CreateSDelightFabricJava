package phoupraw.mcmod.createsdelight.api;

import net.minecraft.world.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
public interface WorldGetter {
    @Contract(value = "null -> null", pure = true)
    static @Nullable World getOrNull(Object subject) {
        return subject instanceof WorldGetter worldGetter ? worldGetter.getWorld() : null;
    }
    @Nullable World getWorld();
}
