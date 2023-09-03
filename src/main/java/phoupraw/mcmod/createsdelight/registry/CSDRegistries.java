package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;

import java.util.Objects;

public class CSDRegistries {
    public static final SimpleRegistry<CakeIngredient> CAKE_INGREDIENT = FabricRegistryBuilder
      .<CakeIngredient>createSimple(RegistryKey.ofRegistry(CSDIdentifiers.CAKE_INGREDIENT))
      .attribute(RegistryAttribute.SYNCED)
      .buildAndRegister();
    public static final SimpleRegistry<VoxelCake> PREDEFINED_CAKE = FabricRegistryBuilder
      .<VoxelCake>createSimple(RegistryKey.ofRegistry(CSDIdentifiers.PREDEFINED_CAKE))
      .attribute(RegistryAttribute.SYNCED)
      .buildAndRegister();
    public static <T> @NotNull Identifier getId(Registry<T> registry, T value) {
        return Objects.requireNonNull(registry.getId(value), value.toString());
    }
    public static <T> @NotNull T get(Registry<T> registry, Identifier id) {
        return Objects.requireNonNull(registry.get(id), id.toString());
    }
}
