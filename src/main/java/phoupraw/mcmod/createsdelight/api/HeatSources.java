package phoupraw.mcmod.createsdelight.api;

import com.nhoryzon.mc.farmersdelight.registry.TagsRegistry;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.fluids.tank.BoilerHeaters;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import phoupraw.mcmod.createsdelight.CreateSDelight;
public final class HeatSources {
    public static final BlockApiLookup<Double, Direction> SIDED = BlockApiLookup.get(new Identifier(CreateSDelight.MOD_ID, "heat_source"), Double.class, Direction.class);
    static {
        SIDED.registerFallback((world, pos, state, blockEntity, side) -> {
            if (side != Direction.UP) return null;
            float boiler = BoilerHeaters.getActiveHeat(world, pos, state);
            return boiler >= 0 ? boiler + 1.0 : null;
        });
        SIDED.registerFallback((world, pos, state, blockEntity, side) -> side == Direction.UP && (state.isIn(AllTags.AllBlockTags.PASSIVE_BOILER_HEATERS.tag) || state.isIn(TagsRegistry.HEAT_SOURCES)) ? 1.0 : null);
    }
    private HeatSources() {}
}
