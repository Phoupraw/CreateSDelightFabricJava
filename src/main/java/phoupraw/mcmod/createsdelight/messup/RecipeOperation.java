package phoupraw.mcmod.createsdelight.messup;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.SimpleRegistry;
import phoupraw.mcmod.createsdelight.CreateSDelight;
@FunctionalInterface
public interface RecipeOperation {
    SimpleRegistry<RecipeOperation> REGISTRY = FabricRegistryBuilder.createSimple(RecipeOperation.class, new Identifier(CreateSDelight.MOD_ID, "recipe_operation")).buildAndRegister();
    boolean operate(Storage<ItemVariant> itemS, Storage<FluidVariant> fluidS, TransactionContext transa);
}
