package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
import phoupraw.mcmod.createsdelight.cake.VoxelCake;

public class CDRegistries {

    public static final SimpleRegistry<CakeIngredient> CAKE_INGREDIENT = FabricRegistryBuilder
      .<CakeIngredient>createSimple(RegistryKey.ofRegistry(CSDIdentifiers.CAKE_INGREDIENT))
      .attribute(RegistryAttribute.SYNCED)
      .buildAndRegister();
    public static final SimpleRegistry<VoxelCake> PREDEFINED_CAKE = FabricRegistryBuilder
      .<VoxelCake>createSimple(RegistryKey.ofRegistry(CSDIdentifiers.PREDEFINED_CAKE))
      .attribute(RegistryAttribute.SYNCED)
      .buildAndRegister();

}
