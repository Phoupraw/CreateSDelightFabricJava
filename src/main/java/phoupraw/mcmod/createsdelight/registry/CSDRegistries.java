package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.DefaultedRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;

import java.util.Objects;

public class CSDRegistries {
    public static final DefaultedRegistry<CakeIngredient> CAKE_INGREDIENT = FabricRegistryBuilder
      .<CakeIngredient>createDefaulted(RegistryKey.ofRegistry(CSDIdentifiers.CAKE_INGREDIENT), CSDIdentifiers.EMPTY)
      .attribute(RegistryAttribute.SYNCED)
      .buildAndRegister();
    static {
        Registry.register(CAKE_INGREDIENT, CSDIdentifiers.EMPTY, CakeIngredient.EMPTY);
    }
    public static <T> @NotNull Identifier getId(Registry<T> registry, @NotNull T value) {
        return Objects.requireNonNull(registry.getId(value), Objects.requireNonNull(value, registry.toString()).toString());
    }
    public static <T> @NotNull T get(Registry<T> registry, @NotNull Identifier id) {
        return Objects.requireNonNull(registry.get(id), id.toString());
    }
}
