package phoupraw.mcmod.createsdelight.misc;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.util.math.Direction;
import phoupraw.mcmod.createsdelight.registry.CSDIdentifiers;

public class HeatableStorages {
    public static final BlockApiLookup<Storage<ItemVariant>, Direction> ITEMS = BlockApiLookup.get(CSDIdentifiers.of("heatable_storages/items"), Storage.asClass(), Direction.class);
    public static final BlockApiLookup<Storage<FluidVariant>, Direction> FLUIDS = BlockApiLookup.get(CSDIdentifiers.of("heatable_storages/fluids"), Storage.asClass(), Direction.class);
}
