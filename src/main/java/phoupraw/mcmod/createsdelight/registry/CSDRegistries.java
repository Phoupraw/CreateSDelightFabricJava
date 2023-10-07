package phoupraw.mcmod.createsdelight.registry;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CSDRegistries {
    static {
        //Registry.register(CAKE_INGREDIENT, CSDIdentifiers.EMPTY, CakeIngredient.EMPTY);
    }
    public static <T> @NotNull Identifier getId(Registry<T> registry, @NotNull T value) {
        return Objects.requireNonNull(registry.getId(value), Objects.requireNonNull(value, registry.toString()).toString());
    }
    public static <T> @NotNull T get(Registry<T> registry, @NotNull Identifier id) {
        return Objects.requireNonNull(registry.get(id), id.toString());
    }
}
