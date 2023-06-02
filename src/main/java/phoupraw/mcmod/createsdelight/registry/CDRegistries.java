package phoupraw.mcmod.createsdelight.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.util.registry.SimpleRegistry;
import phoupraw.mcmod.createsdelight.cake.CakeIngredient;
public class CDRegistries {
    public static final SimpleRegistry<CakeIngredient> CAKE_INGREDIENT = FabricRegistryBuilder
      .createSimple(CakeIngredient.class, CDIdentifiers.CAKE_INGREDIENT)
      .attribute(RegistryAttribute.SYNCED)
      .buildAndRegister();

}
